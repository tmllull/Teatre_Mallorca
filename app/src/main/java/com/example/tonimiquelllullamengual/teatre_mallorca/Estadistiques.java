package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Estadistiques extends AppCompatActivity {

    DbHelper dbHelper;
    TextView tv_total_entrades, tv_dilluns, tv_dimarts, tv_dimecres, tv_dijous, tv_divendres,
            tv_dissabte, tv_diumenge, tv_entrades_dilluns, tv_entrades_dimarts,
            tv_entrades_dimecres, tv_entrades_dijous, tv_entrades_divendres, tv_entrades_dissabte,
            tv_entrades_diumenge;

    LinearLayout lays[] = new LinearLayout[7];

    Percentage percentatge[];

    Calendar calendar = new GregorianCalendar();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistiques);


        ////////////////////////Prova per agafar dies setmana////////////////////////

        SimpleDateFormat formatter = new SimpleDateFormat("c");
        String days[] = new String[7];

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 7; ++i) {
            days[i] = formatter.format(calendar.getTime());
            calendar.add(calendar.DAY_OF_WEEK, 1);
        }

        /*for (int i = 0; i < days.length; i++) {
            Log.i("DAYS: ", days[i].toString());
        }*/

        //////////////////////////////////////////////////////////////////////////////

        dbHelper = new DbHelper(this);
        tv_total_entrades = (TextView) findViewById(R.id.tv_total_entrades);
        tv_dilluns = (TextView) findViewById(R.id.tv_dilluns);
        tv_dimarts = (TextView) findViewById(R.id.tv_dimarts);
        tv_dimecres = (TextView) findViewById(R.id.tv_dimecres);
        tv_dijous = (TextView) findViewById(R.id.tv_dijous);
        tv_divendres = (TextView) findViewById(R.id.tv_divendres);
        tv_dissabte = (TextView) findViewById(R.id.tv_dissabte);
        tv_diumenge = (TextView) findViewById(R.id.tv_diumenge);
        tv_entrades_dilluns = (TextView) findViewById(R.id.tv_entrades_dilluns);
        tv_entrades_dimarts = (TextView) findViewById(R.id.tv_entrades_dimarts);
        tv_entrades_dimecres = (TextView) findViewById(R.id.tv_entrades_dimecres);
        tv_entrades_dijous = (TextView) findViewById(R.id.tv_entrades_dijous);
        tv_entrades_divendres = (TextView) findViewById(R.id.tv_entrades_divendres);
        tv_entrades_dissabte = (TextView) findViewById(R.id.tv_entrades_dissabte);
        tv_entrades_diumenge = (TextView) findViewById(R.id.tv_entrades_diumenge);
        lays[0] = (LinearLayout) findViewById(R.id.lay_dilluns);
        lays[1] = (LinearLayout) findViewById(R.id.lay_dimarts);
        lays[2] = (LinearLayout) findViewById(R.id.lay_dimecres);
        lays[3] = (LinearLayout) findViewById(R.id.lay_dijous);
        lays[4] = (LinearLayout) findViewById(R.id.lay_divendres);
        lays[5] = (LinearLayout) findViewById(R.id.lay_dissabtes);
        lays[6] = (LinearLayout) findViewById(R.id.lay_diumenges);


        //Total d'entrades per dies
        int entrades = dbHelper.getTotalEntrades();
        int dilluns = dbHelper.getEntrades(days[0]);
        int dimarts = dbHelper.getEntrades(days[1]);
        int dimecres = dbHelper.getEntrades(days[2]);
        int dijous = dbHelper.getEntrades(days[3]);
        int divendres = dbHelper.getEntrades(days[4]);
        int dissabte = dbHelper.getEntrades(days[5]);
        int diumenge = dbHelper.getEntrades(days[6]);

        /*
        int dilluns = dbHelper.getEntrades("Mon", "Lun.");
        int dimarts = dbHelper.getEntrades("Tue", "Mar.");
        int dimecres = dbHelper.getEntrades("Wed", "Mié.");
        int dijous = dbHelper.getEntrades("Thu", "Jue.");
        int divendres = dbHelper.getEntrades("Fri", "Vie.");
        int dissabte = dbHelper.getEntrades("Sat", "Sáb.");
        int diumenge = dbHelper.getEntrades("Sun", "Dom.");
        */

        //Detecció de cap entrada venuda encara per evitar problemes
        if (entrades == 0) return;

        //Array on guardem tota la informació de cada dia de la setmana
        percentatge = new Percentage[7];
        percentatge[0] = new Percentage();
        percentatge[0].setDia(days[0]);
        percentatge[0].setPerc(dilluns * 100 / entrades);
        percentatge[0].setEntrades(dilluns);
        percentatge[0].setVendes(dbHelper.getRecaptacio(days[0]));
        percentatge[1] = new Percentage();
        percentatge[1].setDia(days[1]);
        percentatge[1].setPerc(dimarts * 100 / entrades);
        percentatge[1].setEntrades(dimarts);
        percentatge[1].setVendes(dbHelper.getRecaptacio(days[1]));
        percentatge[2] = new Percentage();
        percentatge[2].setDia(days[2]);
        percentatge[2].setPerc(dimecres * 100 / entrades);
        percentatge[2].setEntrades(dimecres);
        percentatge[2].setVendes(dbHelper.getRecaptacio(days[2]));
        percentatge[3] = new Percentage();
        percentatge[3].setDia(days[3]);
        percentatge[3].setPerc(dijous * 100 / entrades);
        percentatge[3].setEntrades(dijous);
        percentatge[3].setVendes(dbHelper.getRecaptacio(days[3]));
        percentatge[4] = new Percentage();
        percentatge[4].setDia(days[4]);
        percentatge[4].setPerc(divendres * 100 / entrades);
        percentatge[4].setEntrades(divendres);
        percentatge[4].setVendes(dbHelper.getRecaptacio(days[4]));
        percentatge[5] = new Percentage();
        percentatge[5].setDia(days[5]);
        percentatge[5].setPerc(dissabte * 100 / entrades);
        percentatge[5].setEntrades(dissabte);
        percentatge[5].setVendes(dbHelper.getRecaptacio(days[5]));
        percentatge[6] = new Percentage();
        percentatge[6].setDia(days[6]);
        percentatge[6].setPerc(diumenge * 100 / entrades);
        percentatge[6].setEntrades(diumenge);
        percentatge[6].setVendes(dbHelper.getRecaptacio(days[6]));

        //Ordenació de major a menor en nombre d'entrades venudes
        Arrays.sort(percentatge);

        //Array de colors per pintar-los de forma "dinàmica"
        int colors[] = new int[7];
        colors[0] = 0x6f11FF00; //verd
        colors[1] = 0x6f77FF00;
        colors[2] = 0x6fC4FF00;
        colors[3] = 0x6fFFF700;
        colors[4] = 0x6fFFB700;
        colors[5] = 0x6fFF6B00;
        colors[6] = 0x6fFF3300; //vermell

        //Processat de la informació a mostrar
        tv_total_entrades.setText(R.string.tickets + String.valueOf(entrades) + "(100%)\n" +
                R.string.estimated + dbHelper.getTotalVentes() + "€");
        int aux = percentatge[0].getEntrades();
        for (int i = 0, j = 0; i < 7; ++i) {
            if (percentatge[i].getDia().equals(days[0])) {
                tv_entrades_dilluns.setText(R.string.tickets + String.valueOf(dilluns) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dilluns.setText(R.string.estimated + dbHelper.getRecaptacio(days[0]) + "€");
                if (dilluns == 0) lays[0].setBackgroundColor(colors[6]);
                else if (dilluns == aux) lays[0].setBackgroundColor(colors[j]);
                else {
                    aux = dilluns;
                    lays[0].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals(days[1])) {
                tv_entrades_dimarts.setText(R.string.tickets + String.valueOf(dimarts) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dimarts.setText(R.string.estimated + dbHelper.getRecaptacio(days[1]) + "€");
                if (dimarts == 0) lays[1].setBackgroundColor(colors[6]);
                else if (dimarts == aux) lays[1].setBackgroundColor(colors[j]);
                else {
                    aux = dimarts;
                    lays[1].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals(days[2])) {
                tv_entrades_dimecres.setText(R.string.tickets + String.valueOf(dimecres) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dimecres.setText(R.string.estimated + dbHelper.getRecaptacio(days[2]) + "€");
                if (dimecres == 0) lays[2].setBackgroundColor(colors[6]);
                else if (dimecres == aux) lays[2].setBackgroundColor(colors[j]);
                else {
                    aux = dimecres;
                    lays[2].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals(days[3])) {
                tv_entrades_dijous.setText(R.string.tickets + String.valueOf(dijous) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dijous.setText(R.string.estimated + dbHelper.getRecaptacio(days[3]) + "€");
                if (dijous == 0) lays[3].setBackgroundColor(colors[6]);
                else if (dijous == aux) lays[3].setBackgroundColor(colors[j]);
                else {
                    aux = dijous;
                    lays[3].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals(days[4])) {
                tv_entrades_divendres.setText(R.string.tickets + String.valueOf(divendres) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_divendres.setText(R.string.estimated + dbHelper.getRecaptacio(days[4]) + "€");
                if (divendres == 0) lays[4].setBackgroundColor(colors[6]);
                else if (divendres == aux) lays[4].setBackgroundColor(colors[j]);
                else {
                    aux = divendres;
                    lays[4].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals(days[5])) {
                tv_entrades_dissabte.setText(R.string.tickets + String.valueOf(dissabte) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dissabte.setText(R.string.estimated + dbHelper.getRecaptacio(days[5]) + "€");
                if (dissabte == 0) lays[5].setBackgroundColor(colors[6]);
                else if (dissabte == aux) lays[5].setBackgroundColor(colors[j]);
                else {
                    aux = dissabte;
                    lays[5].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals(days[6])) {
                tv_entrades_diumenge.setText(R.string.tickets + String.valueOf(diumenge) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_diumenge.setText(R.string.estimated + dbHelper.getRecaptacio(days[6]) + "€");
                if (diumenge == 0) lays[6].setBackgroundColor(colors[6]);
                else if (diumenge == aux) lays[6].setBackgroundColor(colors[j]);
                else {
                    aux = diumenge;
                    lays[6].setBackgroundColor(colors[++j]);
                }
            }
        }
    }
}
