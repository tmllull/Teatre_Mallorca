package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    TextView intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        intro = (TextView) findViewById(R.id.tv_help_intro);

        intro.setText("Gràcies per instal·lar Teatre Mallorca.\n\n" +
                "Aquesta aplicació ens permet gestionar un petit teatre, amb la qual " +
                "podrem afegir shows i quins days es representarà, així com poder " +
                "consultar la informació d'una obra en un day determinat o comprar " +
                "tickets per una sessió determinada.\n\n" +
                "Des de la pantalla de selecció d'shows escollirem la que volem " +
                "consultar, i posteriorment escollirem un day. Dins de la mateixa " +
                "pantalla de selecció de day podrem filtrar perquè ens mostri només " +
                "les obren en un day de la setmana determinat (Dilluns, Dimarts...) o " +
                "totes les shows.\n\n" +
                "Des de la pantalla d'informació podrem consultar quins users han comprat " +
                "tickets, eliminar aquesta sessió (sense afectar a la resta de sessions " +
                "de l'obra), o poder passar a seleccionar seats per comprar.\n\n" +
                "La pantalla de selecció de seats ens permet marcar/desmarcar les seats " +
                "que volem comprar, per passar a la finalització de compra. Si tenim " +
                "el carnet jove o universitari, ho podrem indicar per aplicar un 30% de " +
                "discount.\n\n" +
                "A la pantalla de finalització veurem un resum de la compra, amb el títol " +
                "de l'obra, el day de la sessió, el número d'tickets i el price total (amb " +
                "el discount aplicat si escau), i haurem d'introduïr el correu electronic, " +
                "name i cognoms del comprador per poder finalitzar la compra.\n\n" +
                "Podem eliminar una obra amb totes les seves funcions des de " +
                "l'opció 'Eliminar obra', on tan sols haurem d'escollir de la llista quina " +
                "obra volem eliminar.\n\n" +
                "Com a opció extra, podrem consultar les estadístiques de vendes per day " +
                "de la setmana, on veurem cada day amb la seva corresponent recaptació " +
                "i número d'tickets venudes, ajudat d'un codi de colors per fer-ho " +
                "més visual.");
    }
}
