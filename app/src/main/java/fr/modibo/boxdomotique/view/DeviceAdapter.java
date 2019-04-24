package fr.modibo.boxdomotique.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.model.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private Context context;
    private ArrayList<Device> listDevice;
    private RequestOptions requestOptions;

    //INTERFACE
    private updateListerner updateListerner;

    public interface updateListerner {
        void update(ArrayList<Device> sendData);
    }

    public DeviceAdapter(Context context, ArrayList<Device> list, updateListerner updateListerner) {
        this.listDevice = list;
        this.context = context;
        this.updateListerner = updateListerner;

        requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card_sensor, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {

        final Device device = listDevice.get(position);

        holder.setMcs_tvTitle(device.getNom());
        holder.setMcs_tvInfo(device.getType());

        if (device.getEtat() == 1)
            holder.setMcs_switch(true);
        else
            holder.setMcs_switch(false);

        Glide.with(context).load(listDevice.get(position).getImage()).apply(requestOptions).into(holder.getMcs_iv());

        holder.getMcs_switch().setOnCheckedChangeListener((buttonView, isChecked) -> {

            int etat = (isChecked) ? 1 : 0;

            listDevice.get(position).setEtat(etat);

            updateListerner.update(listDevice);
        });

    }

    @Override
    public int getItemCount() {
        return listDevice.size();
    }
}
