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
                "podrem afegir obres i quins dies es representarà, així com poder " +
                "consultar la informació d'una obra en un dia determinat o comprar " +
                "entrades per una sessió determinada.\n\n" +
                "Des de la pantalla de selecció d'obres escollirem la que volem " +
                "consultar, i posteriorment escollirem un dia. Dins de la mateixa " +
                "pantalla de selecció de dia podrem filtrar perquè ens mostri només " +
                "les obren en un dia de la setmana determinat (Dilluns, Dimarts...) o " +
                "totes les obres.\n\n" +
                "Des de la pantalla d'informació podrem consultar quins usuaris han comprat " +
                "entrades, eliminar aquesta sessió (sense afectar a la resta de sessions " +
                "de l'obra), o poder passar a seleccionar butaques per comprar.\n\n" +
                "La pantalla de selecció de butaques ens permet marcar/desmarcar les butaques " +
                "que volem comprar, per passar a la finalització de compra. Si tenim " +
                "el carnet jove o universitari, ho podrem indicar per aplicar un 30% de " +
                "descompte.\n\n" +
                "A la pantalla de finalització veurem un resum de la compra, amb el títol " +
                "de l'obra, el dia de la sessió, el número d'entrades i el preu total (amb " +
                "el descompte aplicat si escau), i haurem d'introduïr el correu electronic, " +
                "nom i cognoms del comprador per poder finalitzar la compra.\n\n" +
                "També podrem eliminar una obra amb totes les seves funcions des de " +
                "l'opció 'Eliminar obra', on tan sols haurem d'escollir de la llista quina " +
                "obra volem eliminar.");
    }
}
