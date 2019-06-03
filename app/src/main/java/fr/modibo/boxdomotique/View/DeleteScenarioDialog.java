package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import fr.modibo.boxdomotique.Model.Thread.DeleteScenarioThread;
import fr.modibo.boxdomotique.R;

/**
 * Classe <b>DeleteScenarioDialog</b> qui affiche un message de confirmation que l'utilisateur
 * s'il veut bien supprimer le scénario.
 */
public class DeleteScenarioDialog extends DialogFragment implements DeleteScenarioThread.deleteScenarioListerner {

    private int id_scenario;
    private Context context;
    private deleteScenarioDialogListerner listerner;

    public DeleteScenarioDialog(deleteScenarioDialogListerner listerner, int id_scenario, Context context) {
        this.listerner = listerner;
        this.id_scenario = id_scenario;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(context.getResources().getString(R.string.strTitreDeleteScenario));
        builder.setMessage(context.getResources().getString(R.string.strDeleteScenario));

        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DeleteScenarioThread(DeleteScenarioDialog.this, id_scenario).execute();
            }
        });

        builder.setNeutralButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
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

    @Override
    public void successDeleteScenario() {
        listerner.updateScenario();
    }

    public interface deleteScenarioDialogListerner {
        void updateScenario();
    }
}
