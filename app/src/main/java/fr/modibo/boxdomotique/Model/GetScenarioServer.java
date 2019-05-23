package fr.modibo.boxdomotique.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class <b>GetScenarioServer</b> qui effectue une requête vers le serveur
 * pour récuperer la liste des scenarios.
 */
public class GetScenarioServer {

    private static final String URL = UrlServer.URL_SERVER + UrlServer.URL_SCENARIO;

    /**
     * Méthode qui récuperer la liste des scenarios
     *
     * @return Retourne la liste des scenarios
     * @throws Exception Si il n'arrive pas contacter le serveur ou si la liste des scenarios est vide.
     */
    public static ArrayList<Scenario> getScenarioServer() throws Exception {

        OkHttpClient client = new OkHttpClient();

        //Création de la requête
        Request request = new Request.Builder().url(URL).build();

        //Exécution de la requête
        Response response = client.newCall(request).execute();

        String result;

        //Analyse du code retour
        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw new Exception(String.valueOf(response.code()));
        } else {
            //Résultat de la requête
            result = response.body().string();
        }

        Gson gson = new Gson();

        Type ScenarioType = new TypeToken<ArrayList<Scenario>>() {
        }.getType();

        ArrayList<Scenario> resultListScenario = gson.fromJson(result, ScenarioType);

        ArrayList<Scenario> scenarios;

        if (resultListScenario == null)
            throw new Exception("Erreur !!!");
        else
            scenarios = new ArrayList<>(resultListScenario);

        return scenarios;
    }

}
