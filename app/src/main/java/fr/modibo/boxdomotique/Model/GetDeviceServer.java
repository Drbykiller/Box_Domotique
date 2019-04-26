package fr.modibo.boxdomotique.Model;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class <b>GetDeviceServer</b> qui effectue une requête vers le serveur
 * pour récuperer la liste des capteurs/actionneurs.
 */
public class GetDeviceServer {

    private static final String URL = UrlServer.URL_SERVER + UrlServer.URL_JSON;

    /**
     * Méthode qui récuperer la liste des capteurs/actionneurs.
     *
     * @return Retourne la liste des capteurs/actionneurs.
     * @throws Exception Si il n'arrive pas contacter le serveur ou si la liste des capteurs/actionneurs est vide.
     */
    public static ArrayList<Device> getDeviceServer() throws Exception {

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

        /* ********************
         PARTIE 2
         ******************** */

        //JSON
        Gson gson = new Gson();
        DevicesBase devicesBase = gson.fromJson(result, DevicesBase.class);

        ArrayList<Device> device = new ArrayList<>();

        if (devicesBase == null)
            throw new Exception("Erreur !!!");
        else if (devicesBase.getDevices() != null)
            device.addAll(devicesBase.getDevices());

        return device;
    }

}
