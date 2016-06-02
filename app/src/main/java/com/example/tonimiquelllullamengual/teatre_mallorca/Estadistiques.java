package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Estadistiques extends AppCompatActivity {

    DbHelper dbHelper;
    TextView tv_total_entrades, tv_dilluns, tv_dimarts, tv_dimecres, tv_dijous, tv_divendres,
    tv_dissabte, tv_diumenge;

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


        int entrades = dbHelper.getTotalEntrades();
        int dilluns = dbHelper.getVentesDia("Mon");
        int dimarts = dbHelper.getVentesDia("Tue");
        int dimecres = dbHelper.getVentesDia("Wed");
        int dijous = dbHelper.getVentesDia("Thu");
        int divendres = dbHelper.getVentesDia("Fri");
        int dissabte = dbHelper.getVentesDia("Sat");
        int diumenge = dbHelper.getVentesDia("Sun");
        tv_total_entrades.setText(String.valueOf(entrades) + "(100%)\n" + "Ganancies: " +
                ""+dbHelper.getTotalVentes()+"€");
        tv_dilluns.setText(String.valueOf(dilluns) + "(" + dilluns*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Mon")+"€");
        tv_dimarts.setText(String.valueOf(dimarts) + "(" + dimarts*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Tue")+"€");
        tv_dimecres.setText(String.valueOf(dimecres) + "(" + dimecres*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Wed")+"€");
        tv_dijous.setText(String.valueOf(dijous) + "(" + dijous*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Thu")+"€");
        tv_divendres.setText(String.valueOf(divendres) + "(" + divendres*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Fri")+"€");
        tv_dissabte.setText(String.valueOf(dissabte) + "(" + dissabte*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Sat")+"€");
        tv_diumenge.setText(String.valueOf(diumenge) + "(" + diumenge*100/entrades + "%)\n" +
                ""+dbHelper.getVentesDies("Sun")+"€");

    }
}
