package fr.modibo.boxdomotique.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.modibo.boxdomotique.R;

public class ChoiceDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String[] list;
    private boolean[] listcheck;
    private choiceDialogListerner listerner;
    private Context context;

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


        builder.setMultiChoiceItems(list, listcheck, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                listcheck[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TimeDialog timeDialog = new TimeDialog(ChoiceDialog.this);
                timeDialog.show(getFragmentManager(), "time");
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listerner.choiceUser(listcheck, hourOfDay, minute);
    }

    public interface choiceDialogListerner {
        void choiceUser(boolean[] check, int hour, int minute);
    }
}
