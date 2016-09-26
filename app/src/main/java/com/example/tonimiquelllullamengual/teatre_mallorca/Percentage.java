package com.example.tonimiquelllullamengual.teatre_mallorca;

/**
 * Created by tonimiquelllullamengual on 4/6/16.
 */
public class Percentage implements Comparable {

    private String day;
    private Integer perc;
    private Integer tickets;
    private Integer sales;

    Percentage() {
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getTickets() {
        return tickets;
    }

    public String getDay() {
        return day;
    }

    public int getPerc() {
        return perc;
    }

    public int getSales() {
        return sales;
    }

    @Override
    public int compareTo(Object another) {
        //return ((Percentage)another).getSales() - this.sales; //Ordenar per ingressos
        //return ((Percentage)another).getPerc() - this.perc; //Ordenar per %
        return ((Percentage) another).getTickets() - this.tickets; //Ordenar per tickets
    }
}
