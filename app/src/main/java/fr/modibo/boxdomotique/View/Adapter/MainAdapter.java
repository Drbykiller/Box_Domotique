package fr.modibo.boxdomotique.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Main;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.ViewHolder.MainViewHolder;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private ArrayList<Main> data;
    private newFragment fragment;

    public MainAdapter(ArrayList<Main> data, newFragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card_main, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final Main main = data.get(position);

        holder.setMcm_title(main.getTitle());
        holder.setMcm_info(main.getInfo());
        holder.getMcm_image().setImageResource(main.getImage());

        holder.getMcm_root().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.loadFragment(main.getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface newFragment {
        void loadFragment(String title);
    }
}
