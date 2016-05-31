package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Obra {

    private String nom, dia, sessions;
    private Integer places;

    Obra(String nom, Integer places, String dia, String sessions) {
        this.nom = nom;
        this.places = places;
        this.dia = dia;
        this.sessions = sessions;
    }

    Obra (String nom) {
        this.nom = nom;
    }

    Obra() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public String getDia() {return dia;}

    public void setNom(String nom){this.nom = nom;}

    public String getSessions() {return sessions;}
}
