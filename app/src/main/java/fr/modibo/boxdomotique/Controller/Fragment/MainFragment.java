package fr.modibo.boxdomotique.Controller.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Home;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.HomeAdapter;

public class MainFragment extends Fragment implements HomeAdapter.newFragment {

    private newFragmentFromHomeAdapter fragmentFromHomeAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public MainFragment(newFragmentFromHomeAdapter fragmentFromHomeAdapter) {
        this.fragmentFromHomeAdapter = fragmentFromHomeAdapter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Home> data = new ArrayList<>();
        data.add(new Home("Gestion des Capteurs", "Gérer les différents appareil connecté.", R.drawable.sensor));
        data.add(new Home("Gestion des Scénarios", "Crée des Scénarios automatiser en fonction de heure, du lever/coucher du soleil etc ...", R.drawable.scenario));
        data.add(new Home("A Propos", "A Propos de nous !", R.drawable.about));

        HomeAdapter adapter = new HomeAdapter(data, this);

        rv_home.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentFromHomeAdapter = (newFragmentFromHomeAdapter) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement (newFragmentFromHomeAdapter) ");
        }
    }

    @Override
    public void loadFragment(String title) {
        fragmentFromHomeAdapter.loadFragmentFromHomeAdapter(title);
    }

    public interface newFragmentFromHomeAdapter {
        void loadFragmentFromHomeAdapter(String title);
    }
}
