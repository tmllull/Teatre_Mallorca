package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Obra {

    private String nom, dia;
    private Integer places;

    Obra(String nom, Integer places, String dia) {
        this.nom = nom;
        this.places = places;
        this.dia = dia;
    }

    Obra() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public String getDia() {return dia;}

    public void setNom(String nom){this.nom = nom;}
}
