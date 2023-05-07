package com.example.annexe12;

public class Inventeur {
    private String nom,origine,invention;
    private int annee;

    public Inventeur(String nom, String origine,String invention,int annee){
        this.nom = nom;
        this.origine = origine;
        this.invention = invention;
        this.annee = annee;
    }

    public int getAnnee() {
        return annee;
    }

    public String getInvention() {
        return invention;
    }

    public String getNom() {
        return nom;
    }

    public String getOrigine() {
        return origine;
    }
}
