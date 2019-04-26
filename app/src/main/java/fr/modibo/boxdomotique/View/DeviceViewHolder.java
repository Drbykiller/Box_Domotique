package fr.modibo.boxdomotique.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import fr.modibo.boxdomotique.R;

/**
 * La Classe <b>DeviceViewHolder</b> permet d'initialiser les composants graphiques.
 * mcs = material_card_sensor
 */
public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private TextView mcs_tvTitle;
    private TextView mcs_tvInfo;
    private ImageView mcs_iv;
    private SwitchCompat mcs_switch;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        mcs_tvTitle = itemView.findViewById(R.id.mcs_tvTitle);
        mcs_tvInfo = itemView.findViewById(R.id.mcs_tvInfo);
        mcs_iv = itemView.findViewById(R.id.mcs_iv);
        mcs_switch = itemView.findViewById(R.id.mcs_switch);
    }

    public TextView getMcs_tvTitle() {
        return mcs_tvTitle;
    }

    public void setMcs_tvTitle(String mcs_tvTitle) {
        this.mcs_tvTitle.setText(mcs_tvTitle);
    }

    public TextView getMcs_tvInfo() {
        return mcs_tvInfo;
    }

    public void setMcs_tvInfo(String mcs_tvInfo) {
        this.mcs_tvInfo.setText(mcs_tvInfo);
    }

    public ImageView getMcs_iv() {
        return mcs_iv;
    }

    public void setMcs_iv(ImageView mcs_iv) {
        this.mcs_iv = mcs_iv;
    }

    public SwitchCompat getMcs_switch() {
        return mcs_switch;
    }

    public void setMcs_switch(Boolean isSwitch) {
        mcs_switch.setChecked(isSwitch);
    }
}
