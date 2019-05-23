package fr.modibo.boxdomotique.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Scenario {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_devices")
    @Expose
    private List<Integer> id_devices = null;
    @SerializedName("etat")
    @Expose
    private Integer etat;
    @SerializedName("heure")
    @Expose
    private Integer heure;
    @SerializedName("minute")
    @Expose
    private Integer minute;

    public Scenario(Integer id, List<Integer> id_devices, Integer etat, Integer heure, Integer minute) {
        this.id = id;
        this.id_devices = id_devices;
        this.etat = etat;
        this.heure = heure;
        this.minute = minute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getId_devices() {
        return id_devices;
    }

    public void setId_devices(List<Integer> id_devices) {
        this.id_devices = id_devices;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Integer getHeure() {
        return heure;
    }

    public void setHeure(Integer heure) {
        this.heure = heure;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }
}

