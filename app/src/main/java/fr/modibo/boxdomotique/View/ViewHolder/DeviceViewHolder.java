package fr.modibo.boxdomotique.View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.modibo.boxdomotique.R;

/**
 * La Classe <b>DeviceViewHolder</b> permet d'initialiser les composants graphiques.
 * mcs = material_card_sensor
 */
public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private ImageView mcs_iv;
    private TextView mcs_tvTitle;
    private RadioGroup mcs_rg;
    private RadioButton mcs_rbOFF;
    private RadioButton mcs_rbON;
    private TextView mcs_tvInfo;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        mcs_iv = itemView.findViewById(R.id.mcs_iv);
        mcs_tvTitle = itemView.findViewById(R.id.mcs_tvTitle);
        mcs_rg = itemView.findViewById(R.id.mcs_rg);
        mcs_rbOFF = itemView.findViewById(R.id.mcs_rbOFF);
        mcs_rbON = itemView.findViewById(R.id.mcs_rbON);
        mcs_tvInfo = itemView.findViewById(R.id.mcs_tvInfo);
    }

    public ImageView getMcs_iv() {
        return mcs_iv;
    }

    public void setMcs_iv(ImageView mcs_iv) {
        this.mcs_iv = mcs_iv;
    }

    public TextView getMcs_tvTitle() {
        return mcs_tvTitle;
    }

    public void setMcs_tvTitle(String mcs_tvTitle) {
        this.mcs_tvTitle.setText(mcs_tvTitle);
    }

    public RadioGroup getMcs_rg() {
        return mcs_rg;
    }

    public void setMcs_rg(RadioGroup mcs_rg) {
        this.mcs_rg = mcs_rg;
    }

    public RadioButton getMcs_rbOFF() {
        return mcs_rbOFF;
    }

    public void setMcs_rbOFF(RadioButton mcs_rbOFF) {
        this.mcs_rbOFF = mcs_rbOFF;
    }

    public RadioButton getMcs_rbON() {
        return mcs_rbON;
    }

    public void setMcs_rbON(RadioButton mcs_rbON) {
        this.mcs_rbON = mcs_rbON;
    }

    public TextView getMcs_tvInfo() {
        return mcs_tvInfo;
    }

    public void setMcs_tvInfo(String mcs_tvInfo) {
        this.mcs_tvInfo.setText(mcs_tvInfo);
    }
}
