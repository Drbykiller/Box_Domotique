package fr.modibo.boxdomotique.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.modibo.boxdomotique.R;


/**
 * mcs = material_card_sensor
 */
public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private View mcs_root;
    private TextView mcs_tvTitle;
    private TextView mcs_tvInfo;
    private ImageView mcs_iv;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        mcs_root = itemView.findViewById(R.id.mcs_root);
        mcs_tvTitle = itemView.findViewById(R.id.mcs_tvTitle);
        mcs_tvInfo = itemView.findViewById(R.id.mcs_tvInfo);
        mcs_iv = itemView.findViewById(R.id.mcs_iv);
    }

    public View getMcs_root() {
        return mcs_root;
    }

    public void setMcs_root(View mcs_root) {
        this.mcs_root = mcs_root;
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
}
