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

public class InfoDeviceDialog extends DialogFragment {

    private ArrayList<String> devices;
    private okListerner okListerner;

    public InfoDeviceDialog(ArrayList<String> devices) {
        this.devices = devices;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.info_device, null, false);

        ListView listView = v.findViewById(R.id.id_listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, devices);

        listView.setAdapter(adapter);

        builder.setView(v);

        MaterialButton button = v.findViewById(R.id.id_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okListerner.ok();
            }
        });

        return builder.create();
    }

    public void attachListerner(okListerner listerner) {
        this.okListerner = listerner;
    }

    public interface okListerner {
        void ok();
    }
}
