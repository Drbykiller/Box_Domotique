package fr.modibo.boxdomotique.model.Thread;

import android.os.AsyncTask;

import java.util.ArrayList;

import fr.modibo.boxdomotique.model.Device;
import fr.modibo.boxdomotique.model.webservices.DeviceData;

public class DeviceThread extends AsyncTask<Void, Void, Void> {


    //INTERFACE
    private resultDeviceThread resultDeviceThread;

    public interface resultDeviceThread {
        void resultDevice(ArrayList<Device> result);

        void errorDeviceThread(String error);
    }

    private ArrayList<Device> resultat;
    private Exception e;

    public DeviceThread(resultDeviceThread resultDeviceThread) {
        this.resultDeviceThread = resultDeviceThread;

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            resultat = DeviceData.getDeviceServer();
        } catch (Exception e1) {
            e = e1;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (e != null) {
            e.printStackTrace();
            resultDeviceThread.errorDeviceThread(e.getMessage());
        } else
            resultDeviceThread.resultDevice(resultat);
    }
}
