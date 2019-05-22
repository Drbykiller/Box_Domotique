package fr.modibo.boxdomotique.View.ViewHolder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.R;

public class ScenarioViewHolder extends GroupViewHolder {

    private TextView mcScenario_tvScenario;
    private RadioGroup mcScenario_rg;
    private RadioButton mcScenario_rbOFF;
    private RadioButton mcScenario_rbON;
    private TextView mcScenario_hour;
    private Spinner mcScenario_sp;

    public ScenarioViewHolder(@NonNull View itemView) {
        super(itemView);
        mcScenario_tvScenario = itemView.findViewById(R.id.mcScenario_tvScenario);
        mcScenario_rg = itemView.findViewById(R.id.mcScenario_rg);
        mcScenario_rbOFF = itemView.findViewById(R.id.mcScenario_rbOFF);
        mcScenario_rbON = itemView.findViewById(R.id.mcScenario_rbON);
        mcScenario_hour = itemView.findViewById(R.id.mcScenario_hour);
        mcScenario_sp = itemView.findViewById(R.id.mcScenario_sp);
    }

    public void bind(Scenario scenario){
        mcScenario_tvScenario.setText(scenario.getTitle());
        mcScenario_hour.setText(scenario.getTime());
    }


}
