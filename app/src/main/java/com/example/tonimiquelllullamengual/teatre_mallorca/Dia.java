package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Dia {

    private String nom, data, dia_setmana;
    private Integer places;

    Dia(String nom, Integer places, String data, String dia_setmana) {
        this.nom = nom;
        this.places = places;
        this.data = data;
        this.dia_setmana = dia_setmana;
    }

    Dia() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public String getDia() {return data;}

    public void setNom(String nom){this.nom = nom;}

    public String getDiaSetmana() {return dia_setmana;}
}
