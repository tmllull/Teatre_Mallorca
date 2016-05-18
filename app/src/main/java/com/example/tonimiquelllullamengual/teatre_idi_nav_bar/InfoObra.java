package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoObra extends AppCompatActivity implements View.OnClickListener{

    Bundle bundle;
    TextView tvTitol, tvDescripcio, tvPreu, tvPlaces, tvDurada;
    Button comprar;
    DbHelper dbHelper;
    boolean places_lliures = false;
    String aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_obra);

        dbHelper = new DbHelper(this);

        tvTitol = (TextView) findViewById(R.id.tv_Titol_Info);
        tvDescripcio = (TextView) findViewById(R.id.tv_Descripcio_Info);
        tvPreu = (TextView) findViewById(R.id.tv_Preu_Info);
        tvPlaces = (TextView) findViewById(R.id.tv_Places_Info);
        tvDurada = (TextView) findViewById(R.id.tv_Durada_Info);

        comprar = (Button) findViewById(R.id.bt_Comprar_Info);

        comprar.setOnClickListener(this);

        String titol = "Cap obra Seleccionada";
        String descripcio = "Descripcio: ";
        Integer preu, places, durada;
        preu = places = 0;
        bundle = getIntent().getExtras();
        if (bundle != null)
            titol = bundle.getString("Titol");

        tvTitol.setText(titol.toString());
        aux = titol;

        Cursor c = dbHelper.getObra(titol);
        if (c.moveToFirst()) {
            tvDescripcio.setText(c.getString(c.getColumnIndex(dbHelper.CN_DESCRIPCIO)));
            preu = c.getInt(c.getColumnIndex(dbHelper.CN_PREU));
            tvPreu.setText(preu.toString());
            places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
            //aux = places.toString();
            tvPlaces.setText(places.toString());
            durada = c.getInt(c.getColumnIndex(dbHelper.CN_DURADA));
            tvDurada.setText(durada.toString());
            //aux = c.getString(c.getColumnIndex(dbHelper.CN_BUTAQUES));
            if (c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES)) > 0) places_lliures = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Comprar_Info:
                if (places_lliures) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Titol", aux);
                    Intent intent = new Intent (getApplicationContext(), OcupacioButaques.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    /*Toast.makeText(getApplicationContext(), aux,
                            Toast.LENGTH_LONG).show();*/
                    break;
                }
                else
                    Toast.makeText(getApplicationContext(), "No queden places lliures",
                            Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
