package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import fr.modibo.boxdomotique.R;

/**
 * Classe <b>ChoiceDialog</b> qui affiche le pop-up qui permet a
 * l'utilisateur de choisir les differents capteurs/actionneurs pour un scénario.
 */
public class ChoiceDialog extends DialogFragment implements StateDialog.stateDialogListerner {

    private Context context;
    private StateDialog stateDialog;

    private String[] list;
    private boolean[] listcheck;

    private choiceDialogListerner listerner;


    /**
     * Constructeur de la classe <b>ChoiceDialog</b> qui prend en paramètre 3 arguments.
     *
     * @param listerner La classe qui implémente l'interface {@link choiceDialogListerner} passe en paramètre
     *                  pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param list      Liste des noms des capteurs/actionneurs.
     * @param context   Contexte de l'application.
     */
    public ChoiceDialog(choiceDialogListerner listerner, String[] list, Context context) {
        this.listerner = listerner;
        this.list = list;
        this.context = context;
        this.listcheck = new boolean[list.length];
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(context.getResources().getString(R.string.strChoice));

        builder.setMultiChoiceItems(list, listcheck, (dialog, which, isChecked) -> listcheck[which] = isChecked);

        builder.setPositiveButton(context.getResources().getString(R.string.strOK), (dialog, which) -> {
            byte errorNoDeviceSelected = 0;

            // Vérification

            for (boolean b : listcheck) {
                if (b) {
                    errorNoDeviceSelected = 0;
                    break;
                } else
                    errorNoDeviceSelected = 1;
            }

            byte errorOneDeviceMustBeSelected = 0;

            for (boolean b : listcheck) {
                if (b) {
                    errorOneDeviceMustBeSelected++;
                }
            }

            // FIN Vérification

            if (errorNoDeviceSelected == 0) {

                if (errorOneDeviceMustBeSelected >= 2) {
                    listerner.errorMultipleDeviceSelected();
                } else {
                    stateDialog = new StateDialog(this);
                    stateDialog.show(getFragmentManager(), "StateDialog");
                }

            } else {
                listerner.errorChoiceDevice();
            }

        });

        builder.setNeutralButton(context.getResources().getString(R.string.strCancel), (dialog, which) -> {
            dialog.cancel();
        });

        return builder.create();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        try {
            listerner = (choiceDialogListerner) childFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(childFragment.toString() + " must implement (ChoiceDialog.choiceDialogListerner) ");
        }
    }

    /**
     * Méthode implementé de la classe <b>StateDialog</b>
     *
     * @param state     Si le scénario doit etre activé par default ou non.
     * @param hourOfDay L'heure passe en paramètre ce qui permet de le récuperer.
     * @param minute    Les minutes passe en paramètre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.View.StateDialog
     */
    @Override
    public void choiceState(int state, int hourOfDay, int minute) {
        stateDialog.dismiss();
        listerner.choiceUser(listcheck, state, hourOfDay, minute);
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    public interface choiceDialogListerner {
        /**
         * Méthode qui va etre implementé dans la classe <b>ScenarioFragment</b>
         * <p>
         * !!! ATTENTION !!!
         * <p>
         * {@link fr.modibo.boxdomotique.View.StateDialog} -> {@link fr.modibo.boxdomotique.View.ChoiceDialog} ->
         * {@link fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment}
         *
         * @param check     Liste des capteurs/actionneurs choisi par l'utilisateur.
         * @param state     Si le scénario doit etre activé par default ou non.
         * @param hourOfDay L'heure passe en paramètre ce qui permet de le récuperer.
         * @param minute    Les minutes passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment
         */
        void choiceUser(boolean[] check, int state, int hourOfDay, int minute);

        /**
         * Méthode qui va etre implementé dans la classe <b>ScenarioFragment</b>
         * qui va signaler à l'utilisateur qui n'a pas selectionné d'appareil.
         * <p>
         * !!! ATTTENTION !!!
         * <p>
         * {@link fr.modibo.boxdomotique.View.ChoiceDialog} -> {@link fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment}
         * ->{@link fr.modibo.boxdomotique.Controller.MainActivity}
         *
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment
         */
        void errorChoiceDevice();

        /**
         * Méthode qui va etre implementé dans la classe <b>ScenarioFragment</b>
         * qui va signaler a l'utilisateur qu'il doit selectionné qu'un seul
         * appareil.
         * <p>
         * !!! ATTTENTION !!!
         * <p>
         * {@link fr.modibo.boxdomotique.View.ChoiceDialog} -> {@link fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment}
         * ->{@link fr.modibo.boxdomotique.Controller.MainActivity}
         *
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment
         */
        void errorMultipleDeviceSelected();
    }
}
