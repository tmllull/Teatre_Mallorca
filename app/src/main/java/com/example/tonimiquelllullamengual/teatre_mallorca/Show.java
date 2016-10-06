package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Show {

    private String title, day, sessions;
    private Integer places;

    Show(String title, Integer places, String day, String sessions) {
        this.title = title;
        this.places = places;
        this.day = day;
        this.sessions = sessions;
    }

    Show(String title, String sessions) {
        this.title = title;
        this.sessions = sessions;
    }

    Show() {}

    public String getTitle() {return title;}

    public Integer getPlaces() {return places;}

    public String getDay() {return day;}

    public void setTitle(String title){this.title = title;}

    public String getSessions() {return sessions;}
}
