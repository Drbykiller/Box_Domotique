package fr.modibo.boxdomotique.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Device;
import fr.modibo.boxdomotique.Model.Thread.JsonThread;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.ViewHolder.DeviceViewHolder;

/**
 * La classe <b>DeviceAdapter</b> permet de recycler les View.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private Context context;
    private ArrayList<Device> listDevice;
    private RequestOptions requestOptions;


    /**
     * Constructeur de la classe DeviceAdapter qui prend en paramètre 2 arguments.
     *
     * @param context    Contexte de l'application
     * @param listDevice Récupère la liste des capteurs/actionneurs.
     */
    public DeviceAdapter(Context context, ArrayList<Device> listDevice) {
        this.listDevice = listDevice;
        this.context = context;
        requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.error_loading_image);
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
            holder.getMcs_rbON().toggle();
        else
            holder.getMcs_rbOFF().toggle();

        Glide.with(context).load(listDevice.get(position).getImage()).apply(requestOptions).into(holder.getMcs_iv());

        holder.getMcs_rg().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.mcs_rbON:
                    listDevice.get(position).setEtat(1);
                    break;
                case R.id.mcs_rbOFF:
                    listDevice.get(position).setEtat(0);
                    break;
            }

            new JsonThread(listDevice).execute();
        });
    }

    @Override
    public int getItemCount() {
        return listDevice.size();
    }
}
