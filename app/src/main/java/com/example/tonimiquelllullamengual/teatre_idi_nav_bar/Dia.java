package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Dia {

    private String nom, dia;
    private Integer places;

    Dia(String nom, Integer places, String dia) {
        this.nom = nom;
        this.places = places;
        this.dia = dia;
    }

    Dia(String nom) {
        this.nom = nom;
    }

    Dia() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public String getDia() {return dia;}

    public void setNom(String nom){this.nom = nom;}
}
