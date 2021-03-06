package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import fr.modibo.boxdomotique.R;

/**
 * Classe <b>InfoDeviceDialog</b> qui affiche la liste des appareils en fonction du scénario.
 */
public class InfoDeviceDialog extends DialogFragment {

    private ArrayList<String> nameDevice;
    private infoDeviceDialogListerner listerner;


    /**
     * Constructeur de la classe <b>InfoDeviceDialog</b> qui prend en paramètre 2 arguments.
     *
     * @param listerner  La classe qui implémente l'interface {@link infoDeviceDialogListerner} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param nameDevice Les noms des capteurs/actionneurs passe en paramètre.
     */
    public InfoDeviceDialog(infoDeviceDialogListerner listerner, ArrayList<String> nameDevice) {
        this.listerner = listerner;
        this.nameDevice = nameDevice;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.info_device_dialog, null, false);

        ListView id_listView = v.findViewById(R.id.id_listView);
        MaterialButton id_button = v.findViewById(R.id.id_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, nameDevice);

        id_listView.setAdapter(adapter);

        builder.setView(v);

        id_button.setOnClickListener(v1 -> listerner.disappearDialog());

        return builder.create();
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /
    public interface infoDeviceDialogListerner {
        /**
         * Méthode qui va etre implémenté dans la classe <b>ScenarioAdapter</b>
         * qui permet de faire disparaitre le pop-up.
         *
         * @see fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter#disappearDialog()
         */
        void disappearDialog();
    }
}
