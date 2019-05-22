package fr.modibo.boxdomotique.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import fr.modibo.boxdomotique.CustomExpandableRecyclerViewAdapter;
import fr.modibo.boxdomotique.Model.ExpandScenario;
import fr.modibo.boxdomotique.Model.Scenario;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.ViewHolder.ExpandScenarioViewHolder;
import fr.modibo.boxdomotique.View.ViewHolder.ScenarioViewHolder;

public class ScenarioAdapter extends CustomExpandableRecyclerViewAdapter<ScenarioViewHolder, ExpandScenarioViewHolder> {

    public ScenarioAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ScenarioViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card_scenario, parent, false);
        return new ScenarioViewHolder(v);
    }

    @Override
    public ExpandScenarioViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_material_card_scenario, parent, false);
        return new ExpandScenarioViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(ExpandScenarioViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ExpandScenario expandScenario = (ExpandScenario) group.getItems().get(childIndex);
        holder.bind(expandScenario);
    }

    @Override
    public void onBindGroupViewHolder(ScenarioViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Scenario scenario = (Scenario) group;
        holder.bind(scenario);
    }

    public void addAll(List<? extends ExpandableGroup> groups) {
        ((List<ExpandableGroup>) getGroups()).addAll(groups);

        notifyGroupDataChanged();
        notifyDataSetChanged();

    }

}
