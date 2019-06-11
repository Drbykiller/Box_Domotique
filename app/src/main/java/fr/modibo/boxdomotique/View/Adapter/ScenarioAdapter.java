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
     * Constructeur de la classe <b>ScenarioAdapter</b> qui prend en paramètre 5 arguments.
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

        String hour = context.getResources().getString(R.string.strTime) + scenario.getHeure() + "h" + scenario.getMinute();

        holder.getMcScenario_tvTitle().setText("Scenario " + scenario.getId());
        holder.getMcScenario_tvHour().setText(hour);
        holder.getMcScenario_bt().setText(R.string.btList);

        if (scenario.getEtat() == 1)
            holder.getMcScenario_rbON().toggle();
        else
            holder.getMcScenario_rbOFF().toggle();

        holder.getMcScenario_bt().setOnClickListener(v -> infoDevice(scenario));

        holder.getMcScenario_root().setOnClickListener(v -> infoDevice(scenario));

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

        holder.getMcScenario_btDelete().setOnClickListener(v -> deleteScenario(scenario));

    }


    @Override
    public int getItemCount() {
        return listScenario.size();
    }

    private void infoDevice(final Scenario scenario) {
        ArrayList<String> deviceName = new ArrayList<>();

        for (int i = 0; i < listDevice.size(); i++) {
            if (scenario.getId_devices().equals(listDevice.get(i).getId())) {
                deviceName.add(listDevice.get(i).getNom());
            }
        }

        infoDeviceDialog = new InfoDeviceDialog(this, deviceName);
        infoDeviceDialog.show(fragmentManager, "InfoDeviceDialog");
    }

    private void deleteScenario(final Scenario scenario) {
        new DeleteScenarioDialog(this, context, scenario.getId()).show(fragmentManager, "DeleteScenarioDialog");
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    /**
     * Méthode implémenté de la classe <b>InfoDeviceDialog</b>
     * qui permet de faire disparaitre le pop-up qui affiche
     * l'appareil en fonction du scénario.
     *
     * @see fr.modibo.boxdomotique.View.InfoDeviceDialog
     */
    @Override
    public void disappearDialog() {
        infoDeviceDialog.dismiss();
    }

    /**
     * Méthode implementé de la classe <b>DeleteScenarioDialog</b>
     *
     * @see fr.modibo.boxdomotique.View.DeleteScenarioDialog
     */
    @Override
    public void updateScenario() {
        listerner.updateScenario();
    }

    @Override
    public void errorDeleteScenario(String error) {
        listerner.errorDeleteScenario(error);
    }

    public interface scenarioAdapterListerner {
        /**
         * Méthode qui va etre implementé dans la classe <b>ScenarioFragment</b>
         * et qui met a jour la liste des scenarios.
         * <p>
         * !!! ATTENTION !!!
         * <p>
         * {@link fr.modibo.boxdomotique.Model.Thread.DeleteScenarioThread} -> {@link fr.modibo.boxdomotique.View.DeleteScenarioDialog} ->
         * {@link fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter} -> {@link fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment}
         *
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment
         */
        void updateScenario();

        void errorDeleteScenario(String error);
    }
}

