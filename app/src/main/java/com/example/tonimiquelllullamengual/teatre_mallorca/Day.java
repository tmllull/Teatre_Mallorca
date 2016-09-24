package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class Day {

    private String name, date, dayOfTheWeek;
    private Integer places;

    Day(String name, Integer places, String date, String dayOfTheWeek) {
        this.name = name;
        this.places = places;
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
    }

    Day() {}

    public String getName() {return name;}

    public Integer getPlaces() {return places;}

    public String getDate() {return date;}

    public void setName(String name){this.name = name;}

    public String getDayOfTheWeek() {return dayOfTheWeek;}
}
