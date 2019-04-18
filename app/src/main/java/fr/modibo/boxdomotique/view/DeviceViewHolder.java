package fr.modibo.boxdomotique.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.modibo.boxdomotique.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private View material_card_root;
    private TextView material_card_tvTitle;
    private TextView material_card_tvInfo;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        material_card_root = itemView.findViewById(R.id.material_card_root);
        material_card_tvTitle = itemView.findViewById(R.id.material_card_tvTitle);
        material_card_tvInfo = itemView.findViewById(R.id.material_card_tvInfo);
    }

    public View getMaterial_card_root() {
        return material_card_root;
    }

    public void setMaterial_card_root(View material_card_root) {
        this.material_card_root = material_card_root;
    }

    public TextView getMaterial_card_tvTitle() {
        return material_card_tvTitle;
    }

    public void setMaterial_card_tvTitle(String material_card_tvTitle) {
        //this.material_card_tvTitle = material_card_tvTitle;
        this.material_card_tvTitle.setText(material_card_tvTitle);
    }

    public TextView getMaterial_card_tvInfo() {
        return material_card_tvInfo;
    }

    public void setMaterial_card_tvInfo(String material_card_tvInfo) {
        //this.material_card_tvInfo = material_card_tvInfo;
        this.material_card_tvInfo.setText(material_card_tvInfo);
    }
}
