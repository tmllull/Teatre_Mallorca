package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Obra {

    private String nom;
    private Integer places;

    Obra(String nom, Integer places) {
        this.nom = nom;
        this.places = places;
    }

    Obra() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public void setNom(String nom){this.nom = nom;}
}
