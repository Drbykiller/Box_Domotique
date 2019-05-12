package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Devices;
import fr.modibo.boxdomotique.Model.GetDeviceServer;
import fr.modibo.boxdomotique.View.LoadingDialog;

/**
 * Classe <b>DeviceThread</b> qui permet d'envoyer une requête sur internet
 * et qui va récuperer la liste des capteurs/actionneurs.
 */
public class DeviceThread extends AsyncTask<Void, Void, Void> {

    private ArrayList<Devices> result;
    private Exception e;
    private LoadingDialog dialog;
    private FragmentManager fragmentManager;
    private executeDeviceThread executeDeviceThread;


    /**
     * Constructeur de la classe <b>DeviceThread</b> qui prend en paramètre 2 arguments.
     *
     * @param executeDeviceThread La classe qui implémente l'interface {@link executeDeviceThread} passe en paramètre
     *                            pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param fragmentManager     L'argument fragmentManager permet a l'objet 'dialog' de type : {@link fr.modibo.boxdomotique.View.LoadingDialog}, d'afficher correctement le pop-up de chargement.
     */
    public DeviceThread(executeDeviceThread executeDeviceThread, FragmentManager fragmentManager) {
        this.executeDeviceThread = executeDeviceThread;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new LoadingDialog();
        dialog.show(fragmentManager, "Loading Devices");
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
            executeDeviceThread.errorDeviceThread(e.getMessage());
        } else
            executeDeviceThread.resultDeviceThread(result);
    }


    /* ************************
        INTERFACE + IMPLEMENTATION
     *************************/

    /**
     * Les 2 méthodes vont etre implémenté dans la classe {@link fr.modibo.boxdomotique.Controller.Fragment.SensorFragment}.
     */
    public interface executeDeviceThread {
        /**
         * La 1er méthode envoie la liste des capteurs/actionneurs.
         *
         * @param resultDevice Liste des capteurs/actionneurs passé en paramètre.
         * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#resultDeviceThread(ArrayList)
         */
        void resultDeviceThread(ArrayList<Devices> resultDevice);

        /**
         * La 2ème méthode envoie, si il y a une erreur, une erreur.
         *
         * @param error L'Erreur passe en parametre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#errorDeviceThread(String)
         */
        void errorDeviceThread(String error);
    }
}
