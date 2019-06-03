package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.GetScenarioServer;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.View.LoadingDialog;

/**
 * Classe <b>ScenarioThread</b> qui permet d'envoyer une requête sur internet
 * et qui va récuperer la liste des scénarios.
 */
public class ScenarioThread extends AsyncTask<Void, Void, Void> {

    private Exception e;
    private LoadingDialog dialog;
    private FragmentManager fragmentManager;
    private scenarioThreadListerner listerner;

    private ArrayList<Scenario> result;


    /**
     * Constructeur de la classe <b>DeviceThread</b> qui prend en paramètre 2 arguments.
     *
     * @param listerner       La classe qui implémente l'interface {@link scenarioThreadListerner} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param fragmentManager L'argument fragmentManager permet a l'objet 'dialog' de type : {@link LoadingDialog}, d'afficher correctement le pop-up de chargement.
     */
    public ScenarioThread(scenarioThreadListerner listerner, FragmentManager fragmentManager) {
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
            listerner.errorListScenario(e.getMessage());
        } else
            listerner.successListScenario(result);
    }


    /* ////////////////////////////
        INTERFACE + IMPLEMENTATION
    *////////////////////////// /
    public interface scenarioThreadListerner {
        /**
         * La 1er méthode de l'interface envoie la liste des scénarios.
         *
         * @param resultScenario Liste des scénarios passé en paramètre.
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment#successListScenario(ArrayList)
         */
        void successListScenario(ArrayList<Scenario> resultScenario);

        /**
         * La 2ème méthode de l'interface envoie, si il y a une erreur, une erreur.
         *
         * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment#errorListScenario(String)
         */
        void errorListScenario(String error);
    }
}
