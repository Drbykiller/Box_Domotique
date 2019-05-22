package fr.modibo.boxdomotique.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fr.modibo.boxdomotique.Model.Devices;
import fr.modibo.boxdomotique.Model.ExpandScenario;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.Model.Thread.DeviceThread;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter;
import fr.modibo.boxdomotique.View.ChoiceDialog;

public class ScenarioFragment extends Fragment implements DeviceThread.executeDeviceThread, ChoiceDialog.choiceDialogListerner {

    private String[] list;
    private FloatingActionButton fab;
    private ArrayList<Devices> data;
    private RecyclerView rv_scenario;
    private ArrayList<Scenario> lol;
    private int nomber = 0;

    private ScenarioAdapter adapter;
    private List<ExpandScenario> expandScenarios;

    public ScenarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenario, container, false);

        rv_scenario = view.findViewById(R.id.rv_scenario);
        rv_scenario.setLayoutManager(new LinearLayoutManager(getContext()));

        lol = new ArrayList<>();
        expandScenarios = new ArrayList<>();


        //Local
//        expandScenarios.add(new ExpandScenario("Lampe 1"));
//        expandScenarios.add(new ExpandScenario("Lampe 2"));
//        expandScenarios.add(new ExpandScenario("Lampe 3"));
//
//        ArrayList<Scenario> rio = new ArrayList<>();
//        rio.add(new Scenario("Scenario 1", "Heure d'execution : 12h00", expandScenarios));
//        addToAdapter.addAll(rio);


        adapter = new ScenarioAdapter(lol);
        rv_scenario.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab = getActivity().findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeviceThread(ScenarioFragment.this, getFragmentManager()).execute();
            }
        });


    }

    @Override
    public void resultDeviceThread(ArrayList<Devices> resultDevice) {
        data = new ArrayList<>();
        data.addAll(resultDevice);
        list = new String[resultDevice.size()];

        for (int i = 0; i < resultDevice.size(); i++) {
            list[i] = "Device n°" + resultDevice.get(i).getId() + " Nom :" + resultDevice.get(i).getNom();
        }

        ChoiceDialog dialog = new ChoiceDialog(list, this);
        dialog.show(getFragmentManager(), "Choice");
    }

    @Override
    public void errorDeviceThread(String error) {

    }

    @Override
    public void choiceUser(boolean[] check, int hour, int minute) {

        String time = String.valueOf(hour) + "h" + String.valueOf(minute);

        for (int i = 0; i < list.length; i++) {
            if (check[i]) {
                expandScenarios.add(new ExpandScenario(data.get(i).getNom()));
            }
        }

        int taille = lol.size();

        lol.add(new Scenario("Scenario", time, expandScenarios));


        adapter.addAll(lol);
        adapter.notifyDataSetChanged();

    }


}
