package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoObra extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle;
    TextView tvTitol, tvDescripcio, tvPreu, tvPlaces, tvDurada, tvData;
    Button comprar;
    DbHelper dbHelper;
    boolean places_lliures = false;
    String aux, auxTitol;

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
        tvData = (TextView) findViewById(R.id.tv_data_info);

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
        auxTitol = titol;

        Cursor c = dbHelper.getObra(titol);
        if (c.moveToFirst()) {
            tvDescripcio.setText(c.getString(c.getColumnIndex(dbHelper.CN_DESCRIPCIO)));
            preu = c.getInt(c.getColumnIndex(dbHelper.CN_PREU));
            tvPreu.setText(preu.toString());
            places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
            //aux = places.toString();
            //tvPlaces.setText(places.toString());
            durada = c.getInt(c.getColumnIndex(dbHelper.CN_DURADA));
            tvDurada.setText(durada.toString());
            tvData.setText(c.getString(c.getColumnIndex(dbHelper.CN_DATA)));
            //aux = c.getString(c.getColumnIndex(dbHelper.CN_BUTAQUES));

            if (c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES)) > 0) places_lliures = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Comprar_Info:
                //if (places_lliures) {
                Bundle bundle = new Bundle();
                bundle.putString("Titol", auxTitol);
                Intent intent = new Intent(getApplicationContext(), LlistarDies.class);
                //Intent intent = new Intent(getApplicationContext(), LlistarObres.class);
                //Intent intent = new Intent(getApplicationContext(), OcupacioButaques.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                    /*Toast.makeText(getApplicationContext(), aux,
                            Toast.LENGTH_LONG).show();*/
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_obra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.menu_eliminar_obra:
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar obra")
                        .setMessage("Estàs segur que vols eliminar l'obra?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deteleObra(auxTitol);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LlistarObres.class);
        startActivity(intent);
        finish();
    }
}
