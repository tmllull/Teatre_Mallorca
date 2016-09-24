package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 4/6/16.
 */
public class Percentage implements Comparable{

    private String dia;
    private Integer perc;
    private Integer entrades;
    private Integer vendes;

    Percentage() {}

    public void setDay(String dia) {
        this.dia = dia;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }

    public void setEntries(int entrades) {
        this.entrades = entrades;
    }

    public void setSales(int vendes) {
        this.vendes = vendes;
    }

    public int getEntries() {
        return entrades;
    }

    public String getDay() {
        return dia;
    }

    public int getPerc() {
        return perc;
    }

    public int getVendes() {
        return vendes;
    }

    @Override
    public int compareTo(Object another) {
        //return ((Percentage)another).getVendes() - this.vendes; //Ordenar per ingressos
        //return ((Percentage)another).getPerc() - this.perc; //Ordenar per %
        return ((Percentage)another).getEntries() - this.entrades; //Ordenar per tickets
    }
}
