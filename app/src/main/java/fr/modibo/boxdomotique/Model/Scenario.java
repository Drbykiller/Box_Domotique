package fr.modibo.boxdomotique.Model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Scenario extends ExpandableGroup<ExpandScenario> {

    private String name;
    private String time;

    public Scenario(String title, String time, List<ExpandScenario> items) {
        super(title, items);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
