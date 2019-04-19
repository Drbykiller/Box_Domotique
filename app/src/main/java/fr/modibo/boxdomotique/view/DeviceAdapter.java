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
    private ArrayList<Device> list;
    private RequestOptions requestOptions;

    public DeviceAdapter(Context context, ArrayList<Device> list) {
        this.list = list;
        this.context = context;

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

        final Device device = list.get(position);

        holder.setMcs_tvTitle(device.getName());
        holder.setMcs_tvInfo(device.getType());

        Glide.with(context).load(list.get(0).getImage()).apply(requestOptions).into(holder.getMcs_iv());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
