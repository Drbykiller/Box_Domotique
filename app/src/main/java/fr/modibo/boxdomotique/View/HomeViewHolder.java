package fr.modibo.boxdomotique.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.modibo.boxdomotique.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    private View mch_root;
    private TextView mch_title;
    private TextView mch_info;
    protected ImageView mch_image;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        mch_root = itemView.findViewById(R.id.mch_root);
        mch_title = itemView.findViewById(R.id.mch_title);
        mch_info = itemView.findViewById(R.id.mch_info);
        mch_image = itemView.findViewById(R.id.mch_image);
    }

    public View getMch_root() {
        return mch_root;
    }

    public void setMch_root(View mch_root) {
        this.mch_root = mch_root;
    }

    public TextView getMch_title() {
        return mch_title;
    }

    public void setMch_title(String mch_title) {
        this.mch_title.setText(mch_title);
    }

    public TextView getMch_info() {
        return mch_info;
    }

    public void setMch_info(String mch_info) {
        this.mch_info.setText(mch_info);
    }

    public ImageView getMch_image() {
        return mch_image;
    }

    public void setMch_image(ImageView mch_image) {
        this.mch_image = mch_image;
    }
}
