package fr.modibo.boxdomotique.Controller.Fragment;

import android.content.Context;
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

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.Thread.DeviceThread;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.DeviceAdapter;

/**
 * Classe <b>SensorFragment</b> qui fait le lien entre la récuperation des differents capteurs/actionneurs
 * et l'affichage de ces derniers avec un RecycleView.
 */
public class SensorFragment extends Fragment implements DeviceThread.deviceThreadListerner {

    private DeviceAdapter adapter;
    private ArrayList<Device> data;
    private errorFromDeviceThreadListerner listerner;


    public SensorFragment() {
    }

    /**
     * Constructeur de la classe <b>SensorFragment</b> surchargé.
     *
     * @param listerner La classe qui implémente l'interface {@link errorFromDeviceThreadListerner} passe en paramètre pour s'assurer que
     *                  cette classe implémente bien les méthodes de l'interface.
     */
    public SensorFragment(errorFromDeviceThreadListerner listerner) {
        this.listerner = listerner;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        RecyclerView rv_sensor = view.findViewById(R.id.rv_sensor);

        data = new ArrayList<>();
        adapter = new DeviceAdapter(getContext(), data);
        rv_sensor.setAdapter(adapter);

        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            rv_sensor.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else
            rv_sensor.setLayoutManager(new LinearLayoutManager(view.getContext()));

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DeviceThread(this, getFragmentManager()).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listerner = (SensorFragment.errorFromDeviceThreadListerner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement (SensorFragment.errorFromDeviceThreadListerner) ");
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
        if (item.getItemId() == R.id.tb_refresh)
            new DeviceThread(this, getFragmentManager()).execute();

        return super.onOptionsItemSelected(item);
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    /**
     * Methode implementé de la classe <b>DeviceThread</b>
     * qui récupere la liste des capteurs/actionneurs depuis le serveur
     * et qui met a jour le RecycleView.
     *
     * @param devices Récupère la liste des capteurs/actionneurs.
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread
     */
    @Override
    public void successListDevice(ArrayList<Device> devices) {
        data.clear();
        data.addAll(devices);
        adapter.notifyDataSetChanged();
    }

    /**
     * Methode implementé de la classe <b>DeviceThread</b>
     * qui récupère, si il y a une erreur,
     * l'erreur lors de la récuperation de la
     * liste des capteurs/actionneurs et qui
     * l'envoi dans l'interface {@link errorFromDeviceThreadListerner}
     *
     * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread
     */
    @Override
    public void errorListDevice(String error) {
        listerner.error(error);
    }

    public interface errorFromDeviceThreadListerner {
        /**
         * Méthode qui va etre implementé dans la classe <b>MainActivity</b>
         * et qui permet de récupérer, si il y a une erreur,
         * l'erreur de la méthode <b>errorListDevice</b>.
         *
         * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         * @see SensorFragment#errorListDevice(String)
         */
        void error(String error);
    }

}

