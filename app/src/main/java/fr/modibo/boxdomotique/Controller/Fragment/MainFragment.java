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

import fr.modibo.boxdomotique.Model.Main;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.MainAdapter;

public class MainFragment extends Fragment implements MainAdapter.newFragment {

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

        RecyclerView rv_main = view.findViewById(R.id.rv_main);
        rv_main.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Main> data = new ArrayList<>();
        data.add(new Main("Gestion des Capteurs", "Gérer les différents appareil connecté.", R.drawable.sensor));
        data.add(new Main("Gestion des Scénarios", "Crée des Scénarios automatiser en fonction de heure, du lever/coucher du soleil etc ...", R.drawable.scenario));
        data.add(new Main("A Propos", "A Propos de nous !", R.drawable.about));

        MainAdapter adapter = new MainAdapter(data, this);

        rv_main.setAdapter(adapter);
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
