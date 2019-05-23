package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.Model.UrlServer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Classe <b>JsonThread</b> qui permet d'envoyer les modifications effectuées par l'utilisateur au serveur.
 */
public class JsonThread extends AsyncTask<Void, Void, Void> {

    private ArrayList<Device> listDevice;
    private ArrayList<Scenario> listScenario;
    private Gson gson;
    private String URL;

    /**
     * Constructeur de la classe <b>JsonThread</b> surchargé.
     *
     * @param listDevice Liste des capteurs/actionneurs avec les modifications effectuées par l'utilisateur.
     */
    public JsonThread(ArrayList<Device> listDevice) {
        this.listDevice = new ArrayList<>();
        this.listDevice = listDevice;
        this.URL = UrlServer.URL_SERVER + UrlServer.SEND_JSON_URL_DEVICE;
    }

    /**
     * Deuxieme constructeur de la classe <b>JsonThread</b> qui prend en paramètre 2 arguments.
     *
     * @param listScenario La liste des scénario
     * @param url          URL du serveur qui récupere la liste des scénario
     */
    public JsonThread(ArrayList<Scenario> listScenario, String url) {
        this.listScenario = listScenario;
        this.URL = UrlServer.URL_SERVER + url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        gson = new Gson();
    }

    @Override
    protected final Void doInBackground(Void... voids) {
        String json;

        if (URL.equalsIgnoreCase(UrlServer.URL_SERVER + UrlServer.SEND_JSON_URL_DEVICE)) {
            json = gson.toJson(listDevice);
        } else {
            json = gson.toJson(listScenario);
        }

        MediaType JSON = MediaType.parse("application/json;charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        try {
            okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
