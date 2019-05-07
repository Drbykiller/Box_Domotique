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
import fr.modibo.boxdomotique.R;

/**
 * La classe <b>DeviceAdapter</b> permet de recycler les View.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private Context context;
    private ArrayList<Devices> listDevice;
    private RequestOptions requestOptions;
    private updateDevice updateDevice;

    /**
     * Constructeur de la classe DeviceAdapter qui prend en paramètre 3 arguments.
     *
     * @param context      Context de l'application
     * @param list         Récupère la liste des capteurs/actionneurs.
     * @param updateDevice La classe qui implémente l'interface {@link updateDevice} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     */
    public DeviceAdapter(Context context, ArrayList<Devices> list, updateDevice updateDevice) {
        this.listDevice = list;
        this.context = context;
        this.updateDevice = updateDevice;

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

            updateDevice.update(listDevice);
        });

    }

    @Override
    public int getItemCount() {
        return listDevice.size();
    }

    public interface updateDevice {
        /**
         * Méthode qui va etre implémenté dans la classe {@link fr.modibo.boxdomotique.Controller.Fragment.SensorFragment}
         * et qui permet de mettre a jour la liste des capteurs/actionneurs.
         *
         * @param sendNewDevice La liste des capteurs/actionneurs passe en paramètre se qui permet de le récupèrer.
         */
        void update(ArrayList<Devices> sendNewDevice);
    }
}
