package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Dia {

    private String nom, dia, dia_setmana;
    private Integer places;

    Dia(String nom, Integer places, String dia, String dia_setmana) {
        this.nom = nom;
        this.places = places;
        this.dia = dia;
        this.dia_setmana = dia_setmana;
    }

    Dia(String nom) {
        this.nom = nom;
    }

    Dia() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public String getDia() {return dia;}

    public void setNom(String nom){this.nom = nom;}

    public String getDiaSetmana() {return dia_setmana;}
}
