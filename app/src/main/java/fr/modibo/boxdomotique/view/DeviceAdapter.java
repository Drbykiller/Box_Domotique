package fr.modibo.boxdomotique.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.model.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private ArrayList<Device> list;

    public DeviceAdapter(ArrayList<Device> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {

        final Device device = list.get(position);

        holder.setMaterial_card_tvTitle(device.getName());
        holder.setMaterial_card_tvInfo(device.getType());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
