package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OcupacioButaques extends AppCompatActivity implements View.OnClickListener {

    private Button[] butaca = new Button[41];
    private int[] butaca_clicada = new int[41];

    Bundle bundle;
    DbHelper dbHelper;
    TextView tvTitol;
    Button btComprar;
    String butaques_seleccionades, data;
    Integer places_lliures, preu, entrades;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocupacio_butaques);

        Resources r = getResources();
        String name = getPackageName();
        for (int i = 1; i < 41; i++) {
            butaca[i] = (Button) findViewById(r.getIdentifier("button" + i, "id", name));
            butaca[i].setOnClickListener(this);
            butaca_clicada[i] = 0;
        }

        tvTitol = (TextView) findViewById(R.id.tv_titol_ocupacio);
        btComprar = (Button) findViewById(R.id.bt_Comprar_ocupacio);
        btComprar.setOnClickListener(this);

        entrades = 0;

        String places = "init places";

        dbHelper = new DbHelper(this);
        bundle = getIntent().getExtras();
        String titol = "Titol inicial";
        String dia = "Dia sense determinar";
        if (bundle != null) {
            titol = bundle.getString("Titol");
            dia = bundle.getString("Data");
        }
        tvTitol.setText(titol.toString());
        /*Toast.makeText(getApplicationContext(), titol,
                Toast.LENGTH_LONG).show();*/

        //Cursor c = dbHelper.getObra(titol);
        Cursor c = dbHelper.getObraData(titol, dia);
        if (c.moveToFirst()) {
            places = c.getString(c.getColumnIndex(dbHelper.CN_BUTAQUES));
            butaques_seleccionades = places;
            places_lliures = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
            preu = c.getInt(c.getColumnIndex(dbHelper.CN_PREU));
            data = c.getString(c.getColumnIndex(dbHelper.CN_DATA));
        }
        /*Toast.makeText(getApplicationContext(), butaques_seleccionades,
                Toast.LENGTH_LONG).show();*/

        String aux = "p";
        for (int i = 1; i < 41; ++i) {
            aux = Character.toString(places.charAt(i));
            if (aux.equals("0")) {
                butaca[i].setBackgroundColor(0xffd3d3d3);
                butaca[i].setOnClickListener(null);
            }

            //else butaca[i].setBackgroundColor(0xffff0000);
        }
        /*Toast.makeText(getApplicationContext(), butaques_seleccionades,
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ocupacio, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.bt_Comprar_ocupacio) {
            //Button aux = (Button) findViewById(v.getId());
            //aux.setBackgroundColor(0xffff0000);
            for (Integer i = 1; i < 41; i++) {
                if (findViewById(v.getId()).equals(butaca[i])) {
                    if (butaca_clicada[i] == 0) { //Butaca no clicada
                        butaca_clicada[i] = 1;
                    /*Toast.makeText(getApplicationContext(),
                            i.toString(), Toast.LENGTH_LONG).show();*/
                        char[] aux2 = butaques_seleccionades.toCharArray();
                        aux2[i] = '0';
                        butaques_seleccionades = String.valueOf(aux2);
                        places_lliures--;
                        entrades++;
                        Button aux = (Button) findViewById(v.getId());
                        aux.setBackgroundColor(0xffff0000);
                        //return;
                        // }
                    } else {
                        butaca_clicada[i] = 0;
                    /*Toast.makeText(getApplicationContext(),
                            i.toString(), Toast.LENGTH_LONG).show();*/
                        char[] aux2 = butaques_seleccionades.toCharArray();
                        aux2[i] = '1';
                        butaques_seleccionades = String.valueOf(aux2);
                        places_lliures++;
                        entrades--;
                        Button aux = (Button) findViewById(v.getId());
                        aux.setBackgroundColor(0xff4efe4b);
                    }
                }
            }
            /*Toast.makeText(getApplicationContext(),
                    butaques_seleccionades, Toast.LENGTH_LONG).show();*/
                //dbHelper.updateOcupacio(tvTitol.toString(), butaques_seleccionades);
                return;
            }
            else{
            /*Toast.makeText(getApplicationContext(),
                    tvTitol.getText(), Toast.LENGTH_LONG).show();*/
                dbHelper.updateOcupacio(tvTitol.getText().toString(), butaques_seleccionades);
                dbHelper.updatePlacesLliures(tvTitol.getText().toString(), places_lliures);
                int total = entrades * preu;
                Bundle bundle = new Bundle();
                bundle.putInt("Total", total);
                bundle.putInt("Entrades", entrades);
                bundle.putString("Titol", tvTitol.getText().toString());
                bundle.putString("Data", data);
                Intent intent = new Intent(getApplicationContext(), ConfirmarCompra.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return;
            }
        }
    }
