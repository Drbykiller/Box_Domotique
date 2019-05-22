package fr.modibo.boxdomotique.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Home;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.ViewHolder.HomeViewHolder;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<Home> data;
    private newFragment fragment;

    public HomeAdapter(ArrayList<Home> data, newFragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final Home home = data.get(position);

        holder.setMch_title(home.getTitle());
        holder.setMch_info(home.getInfo());
        holder.getMch_image().setImageResource(home.getImage());

        holder.getMch_root().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.loadFragment(home.getTitle());
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
