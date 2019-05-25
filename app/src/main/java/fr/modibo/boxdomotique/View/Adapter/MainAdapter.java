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

/**
 * La classe <b>MainAdapter</b> permet de recycler les View.
 */
public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private ArrayList<Main> data;
    private fragmentListerner listerner;


    /**
     * Constructeur de la classe <b>MainAdapter</b> qui prend en paramètre 2 arguments.
     *
     * @param data      Liste des onglets.
     * @param listerner La classe qui implémente l'interface {@link fragmentListerner} passe en paramètre pour s'assurer que
     *                  cette classe implémente bien les méthodes de l'interface.
     */
    public MainAdapter(ArrayList<Main> data, fragmentListerner listerner) {
        this.data = data;
        this.listerner = listerner;
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
                listerner.loadFragment(main.getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /
    public interface fragmentListerner {
        /**
         * Méthode qui va etre implémenté dans la classe <b>MainFragment</b>
         * qui permet d'obtenir le nom du Fragment.
         *
         * @param title Le nom du Fragment passe en paramètre.
         */
        void loadFragment(String title);
    }
}
