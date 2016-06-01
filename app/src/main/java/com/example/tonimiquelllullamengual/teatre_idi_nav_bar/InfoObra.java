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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class InfoObra extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle;
    TextView tvTitol, tvDescripcio, tvPreu, tvPlaces, tvDurada, tvData;
    Button comprar;
    DbHelper dbHelper;
    boolean places_lliures = false;
    String titol, auxTitol, data, dataDiaSetmana;
    ImageView ivComprar;
    String dia_setmana;

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
        ivComprar = (ImageView) findViewById(R.id.iv_comprar_entrades);
        comprar = (Button) findViewById(R.id.bt_Comprar_Info);
        dia_setmana ="0";

        comprar.setOnClickListener(this);

        ivComprar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (places_lliures) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Titol", auxTitol);
                    bundle.putString("Data", data);
                    bundle.putString("DiaSetmana", dataDiaSetmana);
                    Intent intent = new Intent(getApplicationContext(), OcupacioButaques.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "No queden places per aquesta " +
                        "funció", Toast.LENGTH_SHORT).show();
            }
        });

        Integer preu, places, durada;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            titol = bundle.getString("Titol");
            data = bundle.getString("Data");
            dataDiaSetmana = bundle.getString("DiaSetmana");
        }

        tvTitol.setText(titol.toString());
        auxTitol = titol;

        Cursor c = dbHelper.getObra(titol);
        if (c.moveToFirst()) {
            tvDescripcio.setText(c.getString(c.getColumnIndex(dbHelper.CN_DESCRIPCIO)));
            preu = c.getInt(c.getColumnIndex(dbHelper.CN_PREU));
            tvPreu.setText(preu.toString()+"€");
            places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
            tvPlaces.setText(places.toString());
            durada = c.getInt(c.getColumnIndex(dbHelper.CN_DURADA));
            tvDurada.setText(durada.toString()+" min.");
            tvData.setText(dataDiaSetmana);
            //String prova = c.getString(c.getColumnIndex(dbHelper.CN_BUTAQUES));
            //SimpleDateFormat formatter = new SimpleDateFormat("F");
            //String milis = c.getString(c.getColumnIndex(dbHelper.CN_MILIS));
            //dia_setmana = formatter.format(new Date(Long.parseLong(milis)));
            //dia_setmana = c.getString(c.getColumnIndex(dbHelper.CN_DIA_SETMANA));

            if (c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES)) > 0) places_lliures = true;
        }
        //Toast.makeText(getApplicationContext(), dia_setmana,
        //        Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Comprar_Info:
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
            case R.id.menu_eliminar_funcio:
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar obra")
                        .setMessage("Estàs segur que vols eliminar la funció?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deteleFuncio(auxTitol,data);
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                //startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No!!!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.trash)
                        .show();
                return false;
            case R.id.menu_usuaris_obra:
                Bundle bundle = new Bundle();
                bundle.putString("Titol", auxTitol);
                bundle.putString("Data", data);
                Intent intent = new Intent(getApplicationContext(), LlistarUsuaris.class);

                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Titol", auxTitol);
        Intent intent = new Intent(getApplicationContext(), LlistarDies.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
