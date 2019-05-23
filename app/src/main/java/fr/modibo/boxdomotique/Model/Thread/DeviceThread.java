package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.GetDeviceServer;
import fr.modibo.boxdomotique.View.LoadingDialog;

/**
 * Classe <b>DeviceThread</b> qui permet d'envoyer une requête sur internet
 * et qui va récuperer la liste des capteurs/actionneurs.
 */
public class DeviceThread extends AsyncTask<Void, Void, Void> {

    private ArrayList<Device> result;
    private Exception e;
    private LoadingDialog dialog;
    private FragmentManager fragmentManager;
    private deviceThreadListerner listerner;


    /**
     * Constructeur de la classe <b>DeviceThread</b> qui prend en paramètre 2 arguments.
     *
     * @param listerner       La classe qui implémente l'interface {@link deviceThreadListerner} passe en paramètre
     *                        pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param fragmentManager L'argument fragmentManager permet a l'objet 'dialog' de type : {@link fr.modibo.boxdomotique.View.LoadingDialog}
     *                        d'afficher correctement le pop-up de chargement.
     */
    public DeviceThread(deviceThreadListerner listerner, FragmentManager fragmentManager) {
        this.listerner = listerner;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new LoadingDialog();
        dialog.show(fragmentManager, "Loading Dialog");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            result = GetDeviceServer.getDeviceServer();
        } catch (Exception ex) {
            e = ex;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();

        if (e != null) {
            e.printStackTrace();
            listerner.errorListDevice(e.getMessage());
        } else
            listerner.successListDevice(result);
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /

    public interface deviceThreadListerner {
        /**
         * La 1er méthode de l'interface envoie la liste des capteurs/actionneurs.
         *
         * @param devices Liste des capteurs/actionneurs passé en paramètre.
         * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#successListDevice(ArrayList)
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment#successListDevice(ArrayList)
         */
        void successListDevice(ArrayList<Device> devices);

        /**
         * La 2ème méthode de l'interface envoie, si il y a une erreur, une erreur.
         *
         * @param error L'Erreur passe en parametre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#errorListDevice(String)
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment#errorListDevice(String)
         */
        void errorListDevice(String error);
    }
}
