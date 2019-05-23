package fr.modibo.boxdomotique.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Devices;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.Model.Thread.JsonThread;
import fr.modibo.boxdomotique.Model.UrlServer;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.InfoDeviceDialog;
import fr.modibo.boxdomotique.View.ViewHolder.ScenarioViewHolder;

public class ScenarioAdapter extends RecyclerView.Adapter<ScenarioViewHolder> implements InfoDeviceDialog.okListerner {

    private ArrayList<Devices> data;
    private ArrayList<Scenario> list;
    private FragmentManager fragmentManager;
    private InfoDeviceDialog infoDeviceDialog;
    private Context context;


    public ScenarioAdapter(ArrayList<Devices> data, FragmentManager fragmentManager, ArrayList<Scenario> list, Context context) {
        this.data = data;
        this.fragmentManager = fragmentManager;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ScenarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card_scenario, parent, false);
        return new ScenarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScenarioViewHolder holder, int position) {

        final Scenario scenario = list.get(position);

        holder.getMcScenario_tvTitle().setText("Scenario");
        String heure = context.getResources().getString(R.string.strTime) + scenario.getHeure() + "h" + scenario.getMinute();
        holder.getMcScenario_tvHour().setText(heure);
        holder.getMcScenario_bt().setText(R.string.btList);

        if (scenario.getEtat() == 1)
            holder.getMcScenario_rbON().toggle();
        else
            holder.getMcScenario_rbOFF().toggle();


        holder.getMcScenario_bt().setOnClickListener(v -> info(scenario));

        holder.getMcScenario_root().setOnClickListener(v -> info(scenario));

        holder.getMcScenario_rg().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.mcScenario_rbON:
                    list.get(position).setEtat(1);
                    break;
                case R.id.mcScenario_rbOFF:
                    list.get(position).setEtat(0);
                    break;
            }

            new JsonThread(list, UrlServer.SEND_JSON_URL_SCENARIO).execute();
        });


    }

    private void info(final Scenario scenario) {

        ArrayList<String> deviceName = new ArrayList<>();
        for (Integer name : scenario.getId_devices()) {
            for (Devices get : data) {
                if (get.getId().equals(name)) {
                    deviceName.add(get.getNom());
                }
            }
        }
        infoDeviceDialog = new InfoDeviceDialog(deviceName);
        infoDeviceDialog.attachListerner(this);
        infoDeviceDialog.show(fragmentManager, "Info Device Dialog");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void ok() {
        infoDeviceDialog.dismiss();
    }
}

