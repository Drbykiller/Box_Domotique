package fr.modibo.boxdomotique.controller.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.model.Device;
import fr.modibo.boxdomotique.model.webservices.DeviceData;
import fr.modibo.boxdomotique.view.DeviceAdapter;

public class SensorFragment extends Fragment {

    private RecyclerView rv;
    private View view;
    private ArrayList<Device> donnee;
    private DeviceAdapter adapter;

    public SensorFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sensor, container, false);

        rv = view.findViewById(R.id.rv);

        donnee = new ArrayList<>();
        adapter = new DeviceAdapter(donnee);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DeviceThread thread = new DeviceThread();
        thread.execute();
    }

    class DeviceThread extends AsyncTask<Void, Void, Void> {

        private ArrayList<Device> resultat;
        private Exception e;

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
            } else {
                donnee.clear();
                donnee.addAll(resultat);
                adapter.notifyDataSetChanged();
            }
        }
    }


}

