package fr.modibo.boxdomotique.View.ViewHolder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import fr.modibo.boxdomotique.R;

/**
 * La Classe <b>ScenarioViewHolder</b> permet d'initialiser les composants graphiques.
 * mcScenario = material_card_scenario
 */
public class ScenarioViewHolder extends RecyclerView.ViewHolder {

    private View mcScenario_root;
    private TextView mcScenario_tvTitle;
    private RadioGroup mcScenario_rg;
    private RadioButton mcScenario_rbOFF;
    private RadioButton mcScenario_rbON;
    private TextView mcScenario_tvHour;
    private MaterialButton mcScenario_bt;

    public ScenarioViewHolder(@NonNull View itemView) {
        super(itemView);
        mcScenario_root = itemView.findViewById(R.id.mcScenario_root);
        mcScenario_tvTitle = itemView.findViewById(R.id.mcScenario_tvTitle);
        mcScenario_rg = itemView.findViewById(R.id.mcScenario_rg);
        mcScenario_rbOFF = itemView.findViewById(R.id.mcScenario_rbOFF);
        mcScenario_rbON = itemView.findViewById(R.id.mcScenario_rbON);
        mcScenario_tvHour = itemView.findViewById(R.id.mcScenario_tvHour);
        mcScenario_bt = itemView.findViewById(R.id.mcScenario_bt);
    }

    public View getMcScenario_root() {
        return mcScenario_root;
    }

    public void setMcScenario_root(View mcScenario_root) {
        this.mcScenario_root = mcScenario_root;
    }

    public TextView getMcScenario_tvTitle() {
        return mcScenario_tvTitle;
    }

    public void setMcScenario_tvTitle(TextView mcScenario_tvTitle) {
        this.mcScenario_tvTitle = mcScenario_tvTitle;
    }

    public RadioGroup getMcScenario_rg() {
        return mcScenario_rg;
    }

    public void setMcScenario_rg(RadioGroup mcScenario_rg) {
        this.mcScenario_rg = mcScenario_rg;
    }

    public RadioButton getMcScenario_rbOFF() {
        return mcScenario_rbOFF;
    }

    public void setMcScenario_rbOFF(RadioButton mcScenario_rbOFF) {
        this.mcScenario_rbOFF = mcScenario_rbOFF;
    }

    public RadioButton getMcScenario_rbON() {
        return mcScenario_rbON;
    }

    public void setMcScenario_rbON(RadioButton mcScenario_rbON) {
        this.mcScenario_rbON = mcScenario_rbON;
    }

    public TextView getMcScenario_tvHour() {
        return mcScenario_tvHour;
    }

    public void setMcScenario_tvHour(TextView mcScenario_tvHour) {
        this.mcScenario_tvHour = mcScenario_tvHour;
    }

    public MaterialButton getMcScenario_bt() {
        return mcScenario_bt;
    }

    public void setMcScenario_bt(MaterialButton mcScenario_bt) {
        this.mcScenario_bt = mcScenario_bt;
    }
}

