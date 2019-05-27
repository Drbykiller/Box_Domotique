package fr.modibo.boxdomotique.Controller.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Main;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.MainAdapter;

/**
 * Classe <b>MainFragment</b> qui gère l'affichage de l'écran d'accueil.
 */
public class MainFragment extends Fragment implements MainAdapter.fragmentListerner {

    private fragmentFromMainAdapterListerner listerner;


    public MainFragment() {
    }

    /**
     * Constructeur de la classe <b>MainFragment</b> surchargé.
     *
     * @param listerner La classe qui implémente l'interface {@link fragmentFromMainAdapterListerner} passe en paramètre
     *                  pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     */
    public MainFragment(fragmentFromMainAdapterListerner listerner) {
        this.listerner = listerner;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView rv_main = view.findViewById(R.id.rv_main);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && getResources().getBoolean(R.bool.tablet))
            rv_main.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        else
            rv_main.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Main> data = new ArrayList<>();
        data.add(new Main(getString(R.string.nav_sensor), getString(R.string.strSensor), R.drawable.sensor));
        data.add(new Main(getString(R.string.nav_scenario), getString(R.string.strScenario), R.drawable.scenario));
        data.add(new Main(getString(R.string.nav_about), getString(R.string.strAbout), R.drawable.about));

        MainAdapter adapter = new MainAdapter(data, this);

        rv_main.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listerner = (fragmentFromMainAdapterListerner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement (MainFragment.fragmentFromMainAdapterListerner) ");
        }
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
     *////////////////////////// /

    /**
     * Méthode implémenté de la classe <b>MainAdapter</b>
     * qui récupère le nom du Fragment qui va etre chargé.
     *
     * @param title Permet de savoir quel fragment va etre chargé.
     * @see fr.modibo.boxdomotique.View.Adapter.MainAdapter
     */
    @Override
    public void loadFragment(String title) {
        listerner.loadFragment(title);
    }

    public interface fragmentFromMainAdapterListerner {
        /**
         * Méthode qui va etre implementé dans la classe <b>MainActivity</b>
         * et qui permet de récupérer le nom du Fragment
         * depuis la méthode <b>loadFragment</b>
         *
         * @param title Permet de savoir quel fragment va etre chargé.
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         * @see fr.modibo.boxdomotique.Controller.Fragment.MainFragment#loadFragment(String)
         */
        void loadFragment(String title);
    }
}
