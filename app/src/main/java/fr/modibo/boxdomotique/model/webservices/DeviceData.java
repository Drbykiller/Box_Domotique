package fr.modibo.boxdomotique.model.webservices;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import fr.modibo.boxdomotique.model.UrlServer;
import fr.modibo.boxdomotique.model.Device;
import fr.modibo.boxdomotique.model.DevicesBase;

public class DeviceData {

    private static final String URL = UrlServer.URL_SERVER + UrlServer.URL_JSON;

    public static ArrayList<Device> getDeviceServer() throws Exception {

        // URL
        String reponse = OkHttpUtils.sendGetOkHttpRequest(URL);

        //JSON
        Gson gson = new Gson();
        DevicesBase devicesBase = gson.fromJson(reponse, DevicesBase.class);

        ArrayList<Device> device = new ArrayList<>();

        if (devicesBase == null)
            throw new Exception("Erreur !!!");
        else if (devicesBase.getDevices() != null)
            device.addAll(devicesBase.getDevices());

        Log.w("TAG", reponse);

        return device;
    }

}
