package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import fr.modibo.boxdomotique.R;

/**
 * Classe <b>StateDialog</b> qui affiche un pop-up qui va demander a l'utilisateur
 * si son scénario doit etre activer par default ou non.
 */
public class StateDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private int state = 0;
    private stateDialogListerner listerner;


    /**
     * Constructeur de la classe <b>StateDialog</b> surchargé.
     *
     * @param listerner La classe qui implémente l'interface {@link stateDialogListerner} passe en paramètre
     *                  pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     */
    public StateDialog(stateDialogListerner listerner) {
        this.listerner = listerner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.state_dialog, null, false);

        TextView sd_tv = view.findViewById(R.id.sd_tv);
        RadioGroup sd_rg = view.findViewById(R.id.sd_rg);
        Button sd_bt = view.findViewById(R.id.sd_bt);

        sd_tv.setText(getResources().getText(R.string.strState));

        sd_rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.sd_rbON:
                    state = 1;
                    break;
                case R.id.sd_rbOFF:
                    state = 0;
                    break;
            }
        });

        sd_bt.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        try {
            listerner = (stateDialogListerner) childFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(childFragment.toString() + " must implement (StateDialog.stateDialogListerner) ");
        }
    }

    @Override
    public void onClick(View v) {
        new TimeDialog(this).show(getFragmentManager(), "TimeDialog");
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    /**
     * Méthode implémenté de la classe <b>TimeDialog</b> qui
     * permet de récuperer l'heure et les minutes.
     *
     * @param view      Ce paramètre n'est pas utilisé.
     * @param hourOfDay Récupere l'heure choisi par l'utilisateur.
     * @param minute    Récupere les minutes choisi par l'utilisateur.
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listerner.choiceState(state, hourOfDay, minute);
    }

    public interface stateDialogListerner {
        /**
         * Méthode qui va etre implémenté dans la classe <b>ChoiceDialog</b>.
         *
         * <p>
         * !!! ATTENTION !!!
         * <p>
         * {@link fr.modibo.boxdomotique.View.StateDialog} -> {@link fr.modibo.boxdomotique.View.ChoiceDialog} ->
         * {@link fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment}
         *
         * @param state     Si le scénario doit etre activé par default ou non.
         * @param hourOfDay L'heure passe en paramètre ce qui permet de le récuperer.
         * @param minute    Les minutes passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.View.ChoiceDialog
         */
        void choiceState(int state, int hourOfDay, int minute);
    }
}
