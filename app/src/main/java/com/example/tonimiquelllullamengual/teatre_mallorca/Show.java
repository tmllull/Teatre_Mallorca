package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Show {

    private String nom, dia, sessions;
    private Integer places;

    Show(String nom, Integer places, String dia, String sessions) {
        this.nom = nom;
        this.places = places;
        this.dia = dia;
        this.sessions = sessions;
    }

    Show(String nom, String sessions) {
        this.nom = nom;
        this.sessions = sessions;
    }

    Show() {}

    public String getNom() {return nom;}

    public Integer getPlaces() {return places;}

    public String getDia() {return dia;}

    public void setNom(String nom){this.nom = nom;}

    public String getSessions() {return sessions;}
}
