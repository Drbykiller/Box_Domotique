package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.modibo.boxdomotique.R;

/**
 * Classe <b>ChoiceDialog</b> qui affiche le pop-up qui permet a
 * l'utilisateur de choisir les differents capteurs/actionneurs pour un scénario.
 */
public class ChoiceDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String[] list;
    private boolean[] listcheck;
    private choiceDialogListerner listerner;
    private Context context;

    /**
     * Constructeur de la classe <b>ChoiceDialog</b> qui prend en paramètre 3 arguments.
     *
     * @param list      Liste des noms des capteurs/actionneurs.
     * @param listerner La classe qui implémente l'interface {@link choiceDialogListerner} passe en paramètre
     *                  pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param context   Contexte de l'application
     */
    public ChoiceDialog(String[] list, choiceDialogListerner listerner, Context context) {
        this.list = list;
        this.listerner = listerner;
        this.context = context;
        this.listcheck = new boolean[list.length];
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(context.getResources().getString(R.string.strChoice));

        builder.setMultiChoiceItems(list, listcheck, (dialog, which, isChecked) -> listcheck[which] = isChecked);

        builder.setPositiveButton(context.getResources().getString(R.string.strOK), (dialog, which) -> {
            TimeDialog timeDialog = new TimeDialog(ChoiceDialog.this);
            timeDialog.show(getFragmentManager(), "time");
        });

        builder.setNeutralButton(context.getResources().getString(R.string.strCancel), (dialog, which) -> {
        });

        return builder.create();
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listerner.choiceUser(listcheck, hourOfDay, minute);
    }

    public interface choiceDialogListerner {
        /**
         * Méthode qui va etre implementé dans la classe <b>ScenarioFragment</b>
         *
         * @param check  Liste des capteurs/actionneurs choisi par l'utilisateur.
         * @param hour   L'heure passe en paramètre ce qui permet de le récuperer.
         * @param minute Les minutes passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment
         */
        void choiceUser(boolean[] check, int hour, int minute);
    }
}
