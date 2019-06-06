package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import fr.modibo.boxdomotique.Model.Thread.DeleteScenarioThread;
import fr.modibo.boxdomotique.R;

/**
 * Classe <b>DeleteScenarioDialog</b> qui affiche un message de confirmation de
 * suppression de scénario.
 */
public class DeleteScenarioDialog extends DialogFragment implements DeleteScenarioThread.deleteScenarioListerner {

    private int id_scenario;
    private Context context;
    private deleteScenarioDialogListerner listerner;


    /**
     * Constructeur de la classe <b>DeleteScenarioDialog</b> qui prend en paramètre 3 arguments.
     *
     * @param listerner   La classe qui implémente l'interface {@link deleteScenarioDialogListerner} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param context     Contexte de l'application
     * @param id_scenario L'identifiant du scénario.
     */
    public DeleteScenarioDialog(deleteScenarioDialogListerner listerner, Context context, int id_scenario) {
        this.listerner = listerner;
        this.context = context;
        this.id_scenario = id_scenario;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(context.getResources().getString(R.string.strTitreDeleteScenario));
        builder.setMessage(context.getResources().getString(R.string.strDeleteScenario));

        builder.setPositiveButton("OUI", (dialog, which) -> new DeleteScenarioThread(DeleteScenarioDialog.this, id_scenario).execute());

        builder.setNeutralButton("NON", (dialog, which) -> {

        });

        return builder.create();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        try {
            listerner = (deleteScenarioDialogListerner) childFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(childFragment.toString() + " must implement (DeleteScenarioDialog.deleteScenarioDialogListerner) ");
        }
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    /**
     * Méthode implementé de la classe <b>DeleteScenarioThread</b>
     * et qui met a jour la liste des scénarios.
     *
     * @see fr.modibo.boxdomotique.Model.Thread.DeleteScenarioThread
     */
    @Override
    public void successDeleteScenario() {
        listerner.updateScenario();
    }

    /**
     * Méthode implementé de la classe <b>DeleteScenarioThread</b>
     *
     * @param error L'erreur passe en paramètre ce qui permet de le récuperer.
     */
    @Override
    public void errorDeleteScenario(String error) {
        listerner.errorDeleteScenario(error);
    }

    public interface deleteScenarioDialogListerner {
        /**
         * Méthode qui va etre implémenté dans la classe
         * <b>ScenarioAdapter</b> et qui met a jour la
         * liste des scénarios.
         * <p>
         * !!! ATTENTION !!!
         * <p>
         * {@link DeleteScenarioThread} -> {@link DeleteScenarioDialog} ->
         * {@link fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter} -> {@link fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment}
         *
         * @see fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter
         */
        void updateScenario();

        void errorDeleteScenario(String error);
    }
}
