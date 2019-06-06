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
    private mainAdapterListerner listerner;


    /**
     * Constructeur de la classe <b>MainAdapter</b> qui prend en paramètre 2 arguments.
     *
     * @param listerner La classe qui implémente l'interface {@link mainAdapterListerner} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param data      Liste des onglets.
     */
    public MainAdapter(mainAdapterListerner listerner, ArrayList<Main> data) {
        this.listerner = listerner;
        this.data = data;
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

        holder.getMcm_root().setOnClickListener(v -> listerner.loadFragment(main.getTitle()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /
    public interface mainAdapterListerner {
        /**
         * Méthode qui va etre implémenté dans la classe <b>MainFragment</b>
         * qui permet d'obtenir le nom du Fragment.
         * <p>
         * !!! ATTENTION !!!
         * <p>
         * {@link fr.modibo.boxdomotique.View.Adapter.MainAdapter} -> {@link fr.modibo.boxdomotique.Controller.Fragment.MainFragment}
         * -> {@link fr.modibo.boxdomotique.Controller.MainActivity}
         *
         * @param title Le nom du Fragment passe en paramètre.
         * @see fr.modibo.boxdomotique.Controller.Fragment.MainFragment
         */
        void loadFragment(String title);
    }
}
