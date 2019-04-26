package fr.modibo.boxdomotique.Model;

import java.io.Serializable;

/**
 * Classe <b>Device</b> qui répertorie les caractéristiques des capteurs/actionneurs.
 */
public class Device implements Serializable {

    private Integer id;
    private String nom;
    private String type;
    private Integer etat;
    private Integer valeur;
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

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public String getImage() {
        return image;
    }

    private void setImage(String image) {
        this.image = image;
    }
}
