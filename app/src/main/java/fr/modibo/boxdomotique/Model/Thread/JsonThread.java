package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.UrlServer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Classe <b>JsonThread</b> qui permet d'envoyer les modifications effectuées par l'utilisateur au serveur.
 */
public class JsonThread extends AsyncTask<ArrayList<Device>, Void, Void> {

    private Gson gson;
    private final String URL = UrlServer.URL_SERVER + UrlServer.SEND_URL_JSON;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        gson = new Gson();
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(ArrayList<Device>... arrayLists) {

        String json = gson.toJson(arrayLists);
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
