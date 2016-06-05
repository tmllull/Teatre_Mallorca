package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class Estadistiques extends AppCompatActivity {

    DbHelper dbHelper;
    TextView tv_total_entrades, tv_dilluns, tv_dimarts, tv_dimecres, tv_dijous, tv_divendres,
            tv_dissabte, tv_diumenge, tv_entrades_dilluns, tv_entrades_dimarts,
            tv_entrades_dimecres, tv_entrades_dijous, tv_entrades_divendres, tv_entrades_dissabte,
            tv_entrades_diumenge;

    LinearLayout lays[] = new LinearLayout[7];

    Percentage percentatge[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistiques);

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
        int dilluns = dbHelper.getEntrades("Mon", "Lun.");
        int dimarts = dbHelper.getEntrades("Tue", "Mar.");
        int dimecres = dbHelper.getEntrades("Wed", "Mié.");
        int dijous = dbHelper.getEntrades("Thu", "Jue.");
        int divendres = dbHelper.getEntrades("Fri", "Vie.");
        int dissabte = dbHelper.getEntrades("Sat", "Sáb.");
        int diumenge = dbHelper.getEntrades("Sun", "Dom.");

        //Detecció de cap entrada venuda encara per evitar problemes
        if (entrades == 0) return;

        //Array on guardem tota la informació de cada dia de la setmana
        percentatge = new Percentage[7];
        percentatge[0] = new Percentage();
        percentatge[0].setDia("Dilluns");
        percentatge[0].setPerc(dilluns * 100 / entrades);
        percentatge[0].setEntrades(dilluns);
        percentatge[0].setVendes(dbHelper.getRecaptacio("Mon", "Lun."));
        percentatge[1] = new Percentage();
        percentatge[1].setDia("Dimarts");
        percentatge[1].setPerc(dimarts * 100 / entrades);
        percentatge[1].setEntrades(dimarts);
        percentatge[1].setVendes(dbHelper.getRecaptacio("Tue", "Mar."));
        percentatge[2] = new Percentage();
        percentatge[2].setDia("Dimecres");
        percentatge[2].setPerc(dimecres * 100 / entrades);
        percentatge[2].setEntrades(dimecres);
        percentatge[2].setVendes(dbHelper.getRecaptacio("Wed", "Mié."));
        percentatge[3] = new Percentage();
        percentatge[3].setDia("Dijous");
        percentatge[3].setPerc(dijous * 100 / entrades);
        percentatge[3].setEntrades(dijous);
        percentatge[3].setVendes(dbHelper.getRecaptacio("Thu", "Jue."));
        percentatge[4] = new Percentage();
        percentatge[4].setDia("Divendres");
        percentatge[4].setPerc(divendres * 100 / entrades);
        percentatge[4].setEntrades(divendres);
        percentatge[4].setVendes(dbHelper.getRecaptacio("Fri", "Vie."));
        percentatge[5] = new Percentage();
        percentatge[5].setDia("Dissabte");
        percentatge[5].setPerc(dissabte * 100 / entrades);
        percentatge[5].setEntrades(dissabte);
        percentatge[5].setVendes(dbHelper.getRecaptacio("Sat", "Sáb."));
        percentatge[6] = new Percentage();
        percentatge[6].setDia("Diumenge");
        percentatge[6].setPerc(diumenge * 100 / entrades);
        percentatge[6].setEntrades(diumenge);
        percentatge[6].setVendes(dbHelper.getRecaptacio("Sun", "Dom."));

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
        tv_total_entrades.setText("Entrades: " + String.valueOf(entrades) + "(100%)\n" +
                "Recaptació estimada: " + dbHelper.getTotalVentes() + "€");
        int aux = percentatge[0].getEntrades();
        for (int i = 0, j = 0; i < 7; ++i) {
            if (percentatge[i].getDia().equals("Dilluns")) {
                tv_entrades_dilluns.setText("Entrades: "+String.valueOf(dilluns) +
                                "(" + percentatge[i].getPerc() + "%)");
                tv_dilluns.setText("Rec. estimada: "+dbHelper.getRecaptacio("Mon", "Lun.") + "€");
                if (dilluns == 0)lays[0].setBackgroundColor(colors[6]);
                else if (dilluns == aux) lays[0].setBackgroundColor(colors[j]);
                else {
                    aux = dilluns;
                    lays[0].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals("Dimarts")) {
                tv_entrades_dimarts.setText("Entrades: "+String.valueOf(dimarts) +
                "(" + percentatge[i].getPerc() + "%)");
                tv_dimarts.setText("Rec. estimada: "+dbHelper.getRecaptacio("Tue", "Mar.") + "€");
                if (dimarts == 0)lays[1].setBackgroundColor(colors[6]);
                else if (dimarts == aux) lays[1].setBackgroundColor(colors[j]);
                else {
                    aux = dimarts;
                    lays[1].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals("Dimecres")) {
                tv_entrades_dimecres.setText("Entrades: "+String.valueOf(dimecres) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dimecres.setText("Rec. estimada: "+dbHelper.getRecaptacio("Wed", "Mié.") + "€");
                if (dimecres == 0)lays[2].setBackgroundColor(colors[6]);
                else if (dimecres == aux) lays[2].setBackgroundColor(colors[j]);
                else {
                    aux = dimecres;
                    lays[2].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals("Dijous")) {
                tv_entrades_dijous.setText("Entrades: "+String.valueOf(dijous) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dijous.setText("Rec. estimada: "+dbHelper.getRecaptacio("Tue", "Jue.") + "€");
                if (dijous == 0)lays[3].setBackgroundColor(colors[6]);
                else if (dijous == aux) lays[3].setBackgroundColor(colors[j]);
                else {
                    aux = dijous;
                    lays[3].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals("Divendres")) {
                tv_entrades_divendres.setText("Entrades: "+String.valueOf(divendres) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_divendres.setText("Rec. estimada: "+dbHelper.getRecaptacio("Fri", "Vie.") + "€");
                if (divendres == 0)lays[4].setBackgroundColor(colors[6]);
                else if (divendres == aux) lays[4].setBackgroundColor(colors[j]);
                else {
                    aux = divendres;
                    lays[4].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals("Dissabte")) {
                tv_entrades_dissabte.setText("Entrades: "+String.valueOf(dissabte) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_dissabte.setText("Rec. estimada: "+dbHelper.getRecaptacio("Sat", "Sáb.") + "€");
                if (dissabte == 0)lays[5].setBackgroundColor(colors[6]);
                else if (dissabte == aux) lays[5].setBackgroundColor(colors[j]);
                else {
                    aux = dissabte;
                    lays[5].setBackgroundColor(colors[++j]);
                }
            } else if (percentatge[i].getDia().equals("Diumenge")) {
                tv_entrades_diumenge.setText("Entrades: "+String.valueOf(diumenge) +
                        "(" + percentatge[i].getPerc() + "%)");
                tv_diumenge.setText("Rec. estimada: "+dbHelper.getRecaptacio("Sun", "Dom.") + "€");
                if (diumenge == 0)lays[6].setBackgroundColor(colors[6]);
                else if (diumenge == aux) lays[6].setBackgroundColor(colors[j]);
                else {
                    aux = diumenge;
                    lays[6].setBackgroundColor(colors[++j]);
                }
            }
        }
    }
}
