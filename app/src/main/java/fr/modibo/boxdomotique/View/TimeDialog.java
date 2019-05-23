package fr.modibo.boxdomotique.View;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * Classe <b>TimeDialog</b> qui crée un objet de type <b>TimePickerDialog</b>
 */
public class TimeDialog extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener listener;

    /**
     * Constructeur de la classe <b>TimeDialog</b> surchargé.
     *
     * @param listener La classe qui implémente l'interface {@link TimePickerDialog.OnTimeSetListener} passe en paramètre
     *                 pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     */
    public TimeDialog(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), listener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
