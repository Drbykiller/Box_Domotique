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

import fr.modibo.boxdomotique.Controller.MainActivity;
import fr.modibo.boxdomotique.Model.Devices;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.Model.ScenarioThread;
import fr.modibo.boxdomotique.Model.Thread.DeviceThread;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter;
import fr.modibo.boxdomotique.View.ChoiceDialog;

public class ScenarioFragment extends Fragment implements DeviceThread.executeDeviceThread, ChoiceDialog.choiceDialogListerner, ScenarioThread.executeScenarioThread {

    private String[] list;
    private FloatingActionButton fab;
    private ArrayList<Devices> data;
    private RecyclerView rv_scenario;
    private ArrayList<Scenario> scenarios;
    private ScenarioAdapter adapter;

    public ScenarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenario, container, false);

        rv_scenario = view.findViewById(R.id.rv_scenario);
        rv_scenario.setLayoutManager(new LinearLayoutManager(getContext()));

        fab = MainActivity.getFab();
        fab.show();

        scenarios = new ArrayList<>();
        data = new ArrayList<>();

//        ArrayList<Integer> deviceInScenario = new ArrayList<>();
//        deviceInScenario.add(2);
//        deviceInScenario.add(3);
//        deviceInScenario.add(4);
//
//        scenarios.add(new Scenario(1, deviceInScenario, 0, 12, 45));

        adapter = new ScenarioAdapter(data, getFragmentManager(), scenarios, getContext());

        rv_scenario.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceDialog dialog = new ChoiceDialog(list, ScenarioFragment.this, getContext());
                dialog.show(getFragmentManager(), "Choice");
            }
        });

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        fab.hide();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DeviceThread(this, getFragmentManager()).execute();
        new ScenarioThread(this, getFragmentManager()).execute();
    }

    @Override
    public void resultDeviceThread(ArrayList<Devices> resultDevice) {
        data.addAll(resultDevice);
        adapter.notifyDataSetChanged();
        list = new String[resultDevice.size()];

        for (int i = 0; i < resultDevice.size(); i++) {
            list[i] = resultDevice.get(i).getNom();
        }
    }

    @Override
    public void errorDeviceThread(String error) {

    }

    @Override
    public void choiceUser(boolean[] check, int hour, int minute) {

        ArrayList<Integer> deviceInScenario1 = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            if (check[i]) {
                deviceInScenario1.add(data.get(i).getId());
            }
        }

        int size = scenarios.size() - 1;
        int id = scenarios.get(size).getId() + 1;

        scenarios.add(new Scenario(id, deviceInScenario1, 0, hour, minute));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void resultScenarioThread(ArrayList<Scenario> resultScenario) {
        scenarios.addAll(resultScenario);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void errorScenarioThread(String error) {

    }
}
