package fr.modibo.boxdomotique.View.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import fr.modibo.boxdomotique.Model.ExpandScenario;
import fr.modibo.boxdomotique.R;

public class ExpandScenarioViewHolder extends ChildViewHolder {

    private TextView expand_mcScenario_device;

    public ExpandScenarioViewHolder(View itemView) {
        super(itemView);
        expand_mcScenario_device = itemView.findViewById(R.id.expand_mcScenario_device);
    }

    public void bind(ExpandScenario expandScenario) {
        this.expand_mcScenario_device.setText(expandScenario.device);
    }

}
