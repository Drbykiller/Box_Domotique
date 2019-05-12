package fr.modibo.boxdomotique.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fr.modibo.boxdomotique.Model.Devices;
import fr.modibo.boxdomotique.Model.Thread.JsonThread;
import fr.modibo.boxdomotique.R;

/**
 * La classe <b>DeviceAdapter</b> permet de recycler les View.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private Context context;
    private ArrayList<Devices> listDevice;
    private RequestOptions requestOptions;


    /**
     * Constructeur de la classe DeviceAdapter qui prend en paramètre 2 arguments.
     *
     * @param context Context de l'application
     * @param list    Récupère la liste des capteurs/actionneurs.
     */
    public DeviceAdapter(Context context, ArrayList<Devices> list) {
        this.listDevice = list;
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
        final Devices device = listDevice.get(position);

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

            new JsonThread(listDevice).execute();
        });
    }

    @Override
    public int getItemCount() {
        return listDevice.size();
    }
}
