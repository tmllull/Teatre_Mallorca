package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 4/6/16.
 */
public class Percentage implements Comparable{

    private String dia;
    private Integer perc;
    private Integer entrades;
    private Integer vendes;

    Percentage(String dia, int perc) {
        this.dia = dia;
        this.perc = perc;
    }

    Percentage() {}

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }

    public void setEntrades(int entrades) {
        this.entrades = entrades;
    }

    public void setVendes(int vendes) {
        this.vendes = vendes;
    }

    public int getEntrades() {
        return entrades;
    }

    public String getDia() {
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
        return ((Percentage)another).getEntrades() - this.entrades; //Ordenar per entrades
    }
}
