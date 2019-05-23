package fr.modibo.boxdomotique.View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.modibo.boxdomotique.R;

/**
 * La Classe <b>MainViewHolder</b> permet d'initialiser les composants graphiques.
 * mcm = material_card_main
 */
public class MainViewHolder extends RecyclerView.ViewHolder {

    private View mcm_root;
    private ImageView mcm_image;
    private TextView mcm_title;
    private TextView mcm_info;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        mcm_root = itemView.findViewById(R.id.mcm_root);
        mcm_image = itemView.findViewById(R.id.mcm_image);
        mcm_title = itemView.findViewById(R.id.mcm_title);
        mcm_info = itemView.findViewById(R.id.mcm_info);
    }

    public View getMcm_root() {
        return mcm_root;
    }

    public void setMcm_root(View mcm_root) {
        this.mcm_root = mcm_root;
    }

    public ImageView getMcm_image() {
        return mcm_image;
    }

    public void setMcm_image(ImageView mcm_image) {
        this.mcm_image = mcm_image;
    }

    public TextView getMcm_title() {
        return mcm_title;
    }

    public void setMcm_title(String mcm_title) {
        this.mcm_title.setText(mcm_title);
    }

    public TextView getMcm_info() {
        return mcm_info;
    }

    public void setMcm_info(String mcm_info) {
        this.mcm_info.setText(mcm_info);
    }
}
