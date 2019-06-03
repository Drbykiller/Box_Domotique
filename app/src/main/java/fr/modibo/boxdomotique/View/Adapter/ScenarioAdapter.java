package fr.modibo.boxdomotique.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.Model.Thread.JsonThread;
import fr.modibo.boxdomotique.Model.UrlServer;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.DeleteScenarioDialog;
import fr.modibo.boxdomotique.View.InfoDeviceDialog;
import fr.modibo.boxdomotique.View.ViewHolder.ScenarioViewHolder;

/**
 * La classe <b>ScenarioAdapter</b> permet de recycler les View.
 */
public class ScenarioAdapter extends RecyclerView.Adapter<ScenarioViewHolder> implements InfoDeviceDialog.infoDeviceDialogListerner, DeleteScenarioDialog.deleteScenarioDialogListerner {

    private Context context;
    private FragmentManager fragmentManager;
    private InfoDeviceDialog infoDeviceDialog;

    private ArrayList<Device> listDevice;
    private ArrayList<Scenario> listScenario;

    private scenarioAdapterListerner listerner;


    /**
     * Constructeur de la classe <b>ScenarioAdapter</b> qui prend en paramètre 4 arguments.
     *
     * @param listerner       La classe qui implémente l'interface {@link scenarioAdapterListerner} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param listDevice      Liste des capteurs/actionneurs.
     * @param listScenario    Liste des scénarios.
     * @param fragmentManager L'argument fragmentManager permet a l'objet 'infoDeviceDialog' de type : {@link fr.modibo.boxdomotique.View.InfoDeviceDialog}
     *                        d'afficher correctement le pop-up de chargement.
     * @param context         Contexte de l'application.
     */
    public ScenarioAdapter(scenarioAdapterListerner listerner, ArrayList<Device> listDevice, ArrayList<Scenario> listScenario, FragmentManager fragmentManager, Context context) {
        this.listerner = listerner;
        this.listDevice = listDevice;
        this.listScenario = listScenario;
        this.fragmentManager = fragmentManager;
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
        final Scenario scenario = listScenario.get(position);

        String heure = context.getResources().getString(R.string.strTime) + scenario.getHeure() + "h" + scenario.getMinute();

        holder.getMcScenario_tvTitle().setText("Scenario " + scenario.getId());
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
                    listScenario.get(position).setEtat(1);
                    break;
                case R.id.mcScenario_rbOFF:
                    listScenario.get(position).setEtat(0);
                    break;
            }

            new JsonThread(listScenario, UrlServer.SEND_JSON_URL_SCENARIO).execute();
        });

        holder.getMcScenario_btDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteScenario(scenario);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listScenario.size();
    }

    private void info(final Scenario scenario) {
        ArrayList<String> deviceName = new ArrayList<>();

        /*
        for (Integer name : scenario.getId_devices()) {
            for (Device get : listDevice) {
                if (get.getId().equals(name)) {
                    deviceName.add(get.getNom());
                }
            }
        }*/

        for (int i = 0; i < listDevice.size(); i++) {
            if (scenario.getId_devices().equals(listDevice.get(i).getId())) {
                deviceName.add(listDevice.get(i).getNom());
            }
        }

        infoDeviceDialog = new InfoDeviceDialog(this, deviceName);
        infoDeviceDialog.show(fragmentManager, "InfoDeviceDialog");
    }

    private void deleteScenario(final Scenario scenario) {
        new DeleteScenarioDialog(this, scenario.getId(), context).show(fragmentManager, "DeleteScenarioDialog");
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /
    @Override
    public void ok() {
        infoDeviceDialog.dismiss();
    }

    @Override
    public void updateScenario() {
        listerner.updateScenario();
    }

    public interface scenarioAdapterListerner {
        void updateScenario();
    }
}

