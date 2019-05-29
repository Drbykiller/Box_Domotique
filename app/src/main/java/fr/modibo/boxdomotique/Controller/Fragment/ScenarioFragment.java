package fr.modibo.boxdomotique.Controller.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Controller.MainActivity;
import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.Model.Thread.DeviceThread;
import fr.modibo.boxdomotique.Model.Thread.JsonThread;
import fr.modibo.boxdomotique.Model.Thread.ScenarioThread;
import fr.modibo.boxdomotique.Model.UrlServer;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.ScenarioAdapter;
import fr.modibo.boxdomotique.View.ChoiceDialog;

/**
 * Classe <b>ScenarioFragment</b> qui fait le lien entre la récuperation des differents scénarios
 * et l'affichage de ces derniers avec un RecycleView.
 */
public class ScenarioFragment extends Fragment implements DeviceThread.deviceThreadListerner, ChoiceDialog.choiceDialogListerner, ScenarioThread.scenarioThreadListerner, ScenarioAdapter.scenarioAdapterListerner {

    private FloatingActionButton fab;
    private ScenarioAdapter adapter;
    private byte removeDuplicateScenario = 0;

    private String[] list;
    private ArrayList<Device> listDevice;
    private ArrayList<Scenario> listScenario;

    private scenarioFragmentListerner listerner;


    public ScenarioFragment() {
    }

    /**
     * Constructeur de la classe <b>ScenarioFragment</b> surchargé.
     *
     * @param listerner La classe qui implémente l'interface {@link scenarioFragmentListerner} passe en paramètre pour s'assurer que
     *                  cette classe implémente bien les méthodes de l'interface.
     */
    public ScenarioFragment(scenarioFragmentListerner listerner) {
        this.listerner = listerner;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenario, container, false);

        RecyclerView rv_scenario = view.findViewById(R.id.rv_scenario);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && getResources().getBoolean(R.bool.tablet))
            rv_scenario.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        else
            rv_scenario.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fab = MainActivity.getFab();
        fab.show();

        listDevice = new ArrayList<>();
        listScenario = new ArrayList<>();

        adapter = new ScenarioAdapter(this, listDevice, listScenario, getFragmentManager(), getContext());

        rv_scenario.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            if (list == null) {
                listerner.errorFloatingButton();
            } else {
                ChoiceDialog dialog = new ChoiceDialog(this, list, getContext());
                dialog.show(getFragmentManager(), "Choice Dialog");
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DeviceThread(this, getFragmentManager()).execute();
        new ScenarioThread(this, getFragmentManager()).execute();
    }

    @Override
    public void onDestroy() {
        fab.hide();
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listerner = (ScenarioFragment.scenarioFragmentListerner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement (ScenarioFragment.scenarioFragmentListerner) ");
        }
    }


    /* ////////////////////////////
        TOOLBAR
    *////////////////////////// /
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tb_refresh) {
            new DeviceThread(this, getFragmentManager()).execute();
            new ScenarioThread(this, getFragmentManager()).execute();
        }
        return super.onOptionsItemSelected(item);
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    /**
     * Méthode implémenté de la classe <b>DeviceThread</b>
     * qui récupère la liste des capteurs/actionneurs depuis le serveur
     * via la classe <b>DeviceThread</b>.
     *
     * @param devices Liste des capteurs/actionneurs passé en paramètre.
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread
     */
    @Override
    public void successListDevice(ArrayList<Device> devices) {
        listDevice.addAll(devices);
        adapter.notifyDataSetChanged();

        list = new String[devices.size()];

        for (int i = 0; i < devices.size(); i++) {
            list[i] = devices.get(i).getNom();
        }
    }

    /**
     * Methode implementé de la classe <b>DeviceThread</b>
     * qui récupère, si il y a une erreur,
     * l'erreur lors de la récuperation de la
     * liste des capteurs/actionneurs et qui l'envoie
     * dans l'interface {@link scenarioFragmentListerner}
     *
     * @param error L'Erreur passe en parametre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread
     */
    @Override
    public void errorListDevice(String error) {
        listerner.errorFromDeviceOrScenario(error);
    }

    /**
     * Méthode implémenté de la classe <b>ChoiceDialog</b> qui permet
     * de récuperer le choix, heure et les minutes définie par l'utilisateur.
     *
     * @param check  Récupère un tableau de type boolean auquel on va correspondre
     *               a la liste des capteurs/actionneurs.
     * @param state  Récupere l'état du scénario, si il doit etre activé par default ou non.
     * @param hour   Récupere l'heure choisi par l'utilisateur.
     * @param minute Récupere les minutes choisi par l'utilisateur.
     * @see fr.modibo.boxdomotique.View.ChoiceDialog
     */
    @Override
    public void choiceUser(boolean[] check, int state, int hour, int minute) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            /* Résout le bug de duplication de scénario quand on rajoute un
                scénario sur les version d'Android inferieur à Android 5.0
            */
            removeDuplicateScenario++;
        }

        if (removeDuplicateScenario < 2) {
            /*
            ArrayList<Integer> nameDevice = new ArrayList<>();

            for (int i = 0; i < list.length; i++) {
                if (check[i])
                    nameDevice.add(listDevice.get(i).getId());
            }
            */
            if (!listScenario.isEmpty()) {

                int id_device = 0;
                for (int i = 0; i < listDevice.size(); i++) {
                    if (check[i]) {
                        id_device = listDevice.get(i).getId();
                    }
                }

                int size = listScenario.size() - 1;
                int id = listScenario.get(size).getId() + 1;

                listScenario.add(new Scenario(id, id_device, state, hour, minute));
                adapter.notifyDataSetChanged();

                new JsonThread(listScenario, UrlServer.SEND_JSON_URL_SCENARIO).execute();

            } else {

                int id_device = 0;
                for (int i = 0; i < listDevice.size(); i++) {
                    if (check[i]) {
                        id_device = listDevice.get(i).getId();
                    }
                }

                listScenario.add(new Scenario(0, id_device, state, hour, minute));
                adapter.notifyDataSetChanged();

                new JsonThread(listScenario, UrlServer.SEND_JSON_URL_SCENARIO).execute();
            }

        } else
            removeDuplicateScenario = 0;

    }

    /**
     * Méthode implémenté de la classe <b>ChoiceDialog</b>
     * qui signale à l'utilisateur qui n'a pas selectionné
     * de capteurs/actionneurs et qui l'envoie dans
     * l'interface {@link scenarioFragmentListerner}
     *
     * @see fr.modibo.boxdomotique.View.ChoiceDialog
     */
    @Override
    public void errorChoiceDevice() {
        listerner.errorChoiceDeviceFromChoiceDialog();
    }

    /**
     * Méthode implémenté de la classe <b>ChoiceDialog</b>
     * qui signale à l'utilisateur qu'il ne peut pas
     * ajouter plus de 1 capteurs/actionneurs et qui
     * l'envoie dans l'interface {@link scenarioFragmentListerner}
     *
     * @see fr.modibo.boxdomotique.View.ChoiceDialog
     */
    @Override
    public void errorOneDevice() {
        listerner.errorSingleDevice();
    }

    /**
     * Méthode implementé de la classe <b>ScenarioThread</b>
     * qui récupere la liste des scénarios et qui met a jour
     * le RecyclerView.
     *
     * @param resultScenario Liste des scénarios passé en paramètre.
     * @see fr.modibo.boxdomotique.Model.Thread.ScenarioThread
     */
    @Override
    public void successListScenario(ArrayList<Scenario> resultScenario) {
        listScenario.clear();
        listScenario.addAll(resultScenario);
        adapter.notifyDataSetChanged();
    }

    /**
     * Méthode implémenté de la classe <b>ScenarioThread</b>
     * qui récupere, si il y a une erreur,
     * l'erreur lors de la récuperation de la
     * liste des scénarios et qui l'envoie
     * dans l'interface {@link scenarioFragmentListerner}
     *
     * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.Model.Thread.ScenarioThread
     */
    @Override
    public void errorListScenario(String error) {
        listerner.errorFromDeviceOrScenario(error);
    }

    @Override
    public void updateScenario() {
        new ScenarioThread(this, getFragmentManager()).execute();
    }


    public interface scenarioFragmentListerner {
        /**
         * Méthode qui va etre implementé dans la classe <b>MainActivity</b>
         * et qui permet de recupérer, si il y a une erreur,
         * l'erreur de la méthode <b>errorListDevice</b> et/ou <b>errorListScenario</b>
         *
         * @param error L'erreur passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment#errorListDevice(String)
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment#errorListScenario(String)
         */
        void errorFromDeviceOrScenario(String error);

        /**
         * Méthode qui va etre implementé dans la classe <b>MainActivity</b>
         * et qui va signaler à l'utilisateur qui n'a pas selectionné
         * de capteurs/actionneurs.
         *
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         */
        void errorChoiceDeviceFromChoiceDialog();

        /**
         * Méthode qui va etre implementé dans la classe <b>MainActivity</b>
         * et qui affiche une erreur si l'utilisateur essaye de rajouter
         * un scénario alors que la récuperation des capteurs/actionneurs
         * a échoué.
         *
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         */
        void errorFloatingButton();

        /**
         * Méthode qui va etre implementé dans la classe <b>MainActivity</b>
         * et qui signale a l'utlisateur qu'il ne peut pas ajouter plus de
         * 1 capteurs/actionneurs.
         *
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         */
        void errorSingleDevice();
    }
}
