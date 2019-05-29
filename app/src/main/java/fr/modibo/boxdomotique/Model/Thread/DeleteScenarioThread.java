package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import fr.modibo.boxdomotique.Model.UrlServer;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Classe <b>DeleteScenarioThread</b> qui envoie l'id du scénario
 * pour le supprimer.
 */
public class DeleteScenarioThread extends AsyncTask<Integer, Void, Void> {

    private int scenarioID;
    private Exception e;
    private deleteScenarioListerner listerner;

    private final String URL = UrlServer.URL_SERVER + UrlServer.DELETE_SCENARIO;


    /**
     * Constructeur de la classe <b>DeleteScenarioThread</b> qui prend en paramètre 3 arguments.
     *
     * @param listerner  La classe qui implémente l'interface {@link deleteScenarioListerner} passe en paramètre
     *                   pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     * @param scenarioID L'ID du scénario passe en paramètre.
     */
    public DeleteScenarioThread(deleteScenarioListerner listerner, int scenarioID) {
        this.listerner = listerner;
        this.scenarioID = scenarioID;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(scenarioID))
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                String result = response.body().string();

                if (result.equalsIgnoreCase("ok"))
                    listerner.successDeleteScenario();
            }

        } catch (Exception ex) {
            e = ex;
        }

        return null;
    }

    public interface deleteScenarioListerner {
        void successDeleteScenario();
    }

}
