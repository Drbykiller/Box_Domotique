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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.Thread.DeviceThread;
import fr.modibo.boxdomotique.Model.Thread.JsonThread;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.DeviceAdapter;

/**
 * Classe <b>SensorFragment</b> qui fait le lien entre la recuperation des differents capteurs/actionneurs
 * et l'affichage de ces derniers avec un RecycleView.
 */
public class SensorFragment extends Fragment implements DeviceAdapter.updateDevice, DeviceThread.executeDeviceThread {

    private ArrayList<Device> data;
    private DeviceAdapter adapter;
    private errorFromDeviceThread errorFromDeviceThread;
    private FragmentManager fragmentManager;


    public SensorFragment() {
    }

    /**
     * Constructeur de la classe <b>SensorFragment</b> surchargé.
     *
     * @param errorFromDeviceThread La classe qui implémente l'interface {@link errorFromDeviceThread} passe en paramètre pour s'assurer que
     *                              cette classe implémente bien les méthodes de l'interface.
     */
    public SensorFragment(errorFromDeviceThread errorFromDeviceThread) {
        this.errorFromDeviceThread = errorFromDeviceThread;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        RecyclerView rv = view.findViewById(R.id.rv);

        data = new ArrayList<>();
        adapter = new DeviceAdapter(getContext(), data, this);
        rv.setAdapter(adapter);

        boolean tablet = getResources().getBoolean(R.bool.tablet);

        if (tablet) {
            rv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else
            rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            errorFromDeviceThread = (SensorFragment.errorFromDeviceThread) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement (SensorFragment.errorFromDeviceThread) ");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        new DeviceThread(this, fragmentManager).execute();
    }


    /* ************************
        TOOLBAR
     *************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sensorfragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tb_refresh)
            new DeviceThread(this, fragmentManager).execute();

        return super.onOptionsItemSelected(item);
    }

    /* ************************
        INTERFACE + IMPLEMENTATION
     *************************/
    public interface errorFromDeviceThread {
        /**
         * Méthode qui va etre implementer dans la classe <b>MainActivity</b>
         * et qui permet de recupérer, si il y a une erreur,
         * l'erreur de la methode <b>errorDeviceThread</b>.
         *
         * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.MainActivity
         * @see SensorFragment#errorDeviceThread(String)
         */
        void error(String error);
    }

    /**
     * Methode implementé de la classe <b>DeviceAdapter</b>
     * qui permet de prendre en compte les modifications effectuées par l'utilisateur et
     * qui envoie ces derniers au format JSON au serveur via la classe <b>JsonThread</b>.
     *
     * @param sendNewDevice Liste des capteurs/actionneurs modifiés par l'utilisateur.
     * @see fr.modibo.boxdomotique.View.DeviceAdapter
     * @see fr.modibo.boxdomotique.Model.Thread.JsonThread
     */
    @Override
    public void update(ArrayList<Device> sendNewDevice) {
        new JsonThread().execute(sendNewDevice);
    }

    /**
     * Methode implementé de la classe <b>DeviceThread</b>
     * qui recupere la liste des capteurs/actionneurs depuis le serveur
     * via la classe <b>DeviceThread</b> et qui met a jour le RecycleView.
     *
     * @param resultDevice Récupère la liste des capteurs/actionneurs.
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread#executeDeviceThread
     */
    @Override
    public void resultDeviceThread(ArrayList<Device> resultDevice) {
        data.clear();
        data.addAll(resultDevice);
        adapter.notifyDataSetChanged();
    }

    /**
     * Methode implementé de la classe <b>DeviceThread</b>
     * qui recupere, si il y a une erreur,
     * l'erreur lors de la recuperation de la
     * liste des capteurs/actionneurs et qui
     * l'envoi dans l'interface <b>errorFromDeviceThread</b>
     *
     * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
     * @see errorFromDeviceThread
     * @see fr.modibo.boxdomotique.Model.Thread.DeviceThread#executeDeviceThread
     */
    @Override
    public void errorDeviceThread(String error) {
        errorFromDeviceThread.error(error);
    }

}

