package fr.modibo.boxdomotique.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Device implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nom")
    @Expose
    private String nom;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("etat")
    @Expose
    private Integer etat;

    @SerializedName("valeur")
    @Expose
    private Integer valeur;

    @SerializedName("image")
    @Expose
    private String image;


    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Integer getValeur() {
        return valeur;
    }

    private void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
