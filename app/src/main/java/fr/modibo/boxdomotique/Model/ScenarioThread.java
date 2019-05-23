package fr.modibo.boxdomotique.Model;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import fr.modibo.boxdomotique.View.LoadingDialog;

/**
 * Classe <b>ScenarioThread</b> qui permet d'envoyer une requête sur internet
 * et qui va récuperer la liste des capteurs/actionneurs.
 */
public class ScenarioThread extends AsyncTask<Void, Void, Void> {

    private ArrayList<Scenario> result;
    private Exception e;
    private LoadingDialog dialog;
    private FragmentManager fragmentManager;
    private executeScenarioThread executeScenarioThread;


    /**
     * Constructeur de la classe <b>DeviceThread</b> qui prend en paramètre 2 arguments.
     *
     * @param executeScenarioThread La classe qui implémente l'interface {@link executeScenarioThread} passe en paramètre
     *                              pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param fragmentManager       L'argument fragmentManager permet a l'objet 'dialog' de type : {@link LoadingDialog}, d'afficher correctement le pop-up de chargement.
     */
    public ScenarioThread(executeScenarioThread executeScenarioThread, FragmentManager fragmentManager) {
        this.executeScenarioThread = executeScenarioThread;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new LoadingDialog();
        dialog.show(fragmentManager, "Loading Scenario");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            result = GetScenarioServer.getScenarioServer();
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
            executeScenarioThread.errorScenarioThread(e.getMessage());
        } else
            executeScenarioThread.resultScenarioThread(result);
    }


    /* ************************
        INTERFACE + IMPLEMENTATION
     *************************/

    /**
     * Les 2 méthodes vont etre implémenté dans la classe {@link fr.modibo.boxdomotique.Controller.Fragment.SensorFragment}.
     */
    public interface executeScenarioThread {
        /**
         * La 1er méthode envoie la liste des capteurs/actionneurs.
         *
         * @param resultScenario Liste des capteurs/actionneurs passé en paramètre.
         * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#resultDeviceThread(ArrayList)
         */
        void resultScenarioThread(ArrayList<Scenario> resultScenario);

        /**
         * La 2ème méthode envoie, si il y a une erreur, une erreur.
         *
         * @param error L'Erreur passe en parametre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#errorDeviceThread(String)
         */
        void errorScenarioThread(String error);
    }
}
