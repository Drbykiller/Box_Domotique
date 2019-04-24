package fr.modibo.boxdomotique.controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.model.Device;
import fr.modibo.boxdomotique.model.Thread.DeviceThread;
import fr.modibo.boxdomotique.model.Thread.JsonThread;
import fr.modibo.boxdomotique.view.DeviceAdapter;

public class SensorFragment extends Fragment implements DeviceAdapter.updateListerner, DeviceThread.resultDeviceThread {

    private RecyclerView rv;
    private View view;
    private ArrayList<Device> donnee;
    private DeviceAdapter adapter;

    //INTERFACE
    private DeviceThreadError deviceThreadError;

    public interface DeviceThreadError {
        void deviceThreadError(String error);
    }

    public SensorFragment(DeviceThreadError deviceThreadError) {
        this.deviceThreadError = deviceThreadError;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sensor, container, false);
        rv = view.findViewById(R.id.rv);

        donnee = new ArrayList<>();
        adapter = new DeviceAdapter(getContext(), donnee, this);
        rv.setAdapter(adapter);

        boolean tablet = getResources().getBoolean(R.bool.tablet);

        if (tablet) {
            rv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else
            rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DeviceThread(this).execute();
    }

    @Override
    public void update(ArrayList<Device> sendData) {
        new JsonThread().execute(sendData);
    }

    @Override
    public void resultDevice(ArrayList<Device> result) {
        donnee.clear();
        donnee.addAll(result);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void errorDeviceThread(String error) {
        deviceThreadError.deviceThreadError(error);
    }

}

