package fr.modibo.boxdomotique.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scenario {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("id_appareil")
    @Expose
    private Integer id_appareil;

    @SerializedName("etat_appareil")
    @Expose
    private Integer etat_appareil;

    @SerializedName("heure")
    @Expose
    private Integer heure;

    @SerializedName("minutes")
    @Expose
    private Integer minutes;

    public Scenario(Integer id, Integer id_appareil, Integer etat_appareil, Integer heure, Integer minutes) {
        this.id = id;
        this.id_appareil = id_appareil;
        this.etat_appareil = etat_appareil;
        this.heure = heure;
        this.minutes = minutes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_devices() {
        return id_appareil;
    }

    public void setId_devices(Integer id_devices) {
        this.id_appareil = id_devices;
    }

    public Integer getEtat() {
        return etat_appareil;
    }

    public void setEtat(Integer etat) {
        this.etat_appareil = etat;
    }

    public Integer getHeure() {
        return heure;
    }

    public void setHeure(Integer heure) {
        this.heure = heure;
    }

    public Integer getMinute() {
        return minutes;
    }

    public void setMinute(Integer minute) {
        this.minutes = minute;
    }
}

