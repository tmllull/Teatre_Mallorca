package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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

public class OcupacioButaques extends AppCompatActivity implements View.OnClickListener {

    private Button[] butaca = new Button[41];
    private int[] butaca_clicada = new int[41];

    Bundle bundle;
    DbHelper dbHelper;
    TextView tvTitol;
    Button btComprar;
    String butaques_seleccionades, data, dataDiaSetmana, titol, butaques;
    Integer places_lliures, preu, entrades;
    ImageView ivComprar;
    Integer descompte;

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

        descompte = 0;
        ivComprar = (ImageView) findViewById(R.id.iv_compra_pati_butaques);
        ivComprar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (entrades == 0) {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar com a mínim" +
                                    " una butaca",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Aplicar descompte")
                            .setMessage("Si tens el carnet Jove o universitari " +
                                    "tens un 30% de descompte")
                            .setPositiveButton("   Sí, el tinc", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    descompte = 20;
                                    confirmar();
                                }
                            })
                            .setNegativeButton("No tinc cap carnet   ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    confirmar();
                                    // do nothing
                                }
                            })
                            .setIcon(R.drawable.discount)
                            .show();
                }
            }
        });

        entrades = 0;
        dbHelper = new DbHelper(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            titol = bundle.getString("Titol");
            data = bundle.getString("Data");
        }
        tvTitol.setText(titol);
        Cursor c = dbHelper.getObraData(titol, data);
        if (c.moveToFirst()) {
            butaques = c.getString(c.getColumnIndex(dbHelper.CN_BUTAQUES));
            butaques_seleccionades = butaques;
            places_lliures = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
            preu = c.getInt(c.getColumnIndex(dbHelper.CN_PREU));
        }
        String aux;
        for (int i = 1; i < 41; ++i) {
            aux = Character.toString(butaques.charAt(i));
            if (aux.equals("0")) {
                butaca[i].setBackgroundColor(0xffd3d3d3);
                butaca[i].setOnClickListener(null);
            }
        }
    }

    void confirmar() {
        int total = entrades * preu;
        total -= (total * descompte) / 100;
        Bundle bundle = new Bundle();
        bundle.putInt("Total", total);
        bundle.putInt("Entrades", entrades);
        bundle.putString("Titol", titol);
        bundle.putString("Data", data);
        bundle.putString("Butaques", butaques_seleccionades);
        bundle.putInt("Places", places_lliures);
        Intent intent = new Intent(getApplicationContext(), ConfirmarCompra.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_ocupacio, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.bt_Comprar_ocupacio) {
            for (Integer i = 1; i < 41; i++) {
                if (findViewById(v.getId()).equals(butaca[i])) {
                    if (butaca_clicada[i] == 0) { //Butaca no clicada
                        butaca_clicada[i] = 1;
                        char[] aux2 = butaques_seleccionades.toCharArray();
                        aux2[i] = '0';
                        butaques_seleccionades = String.valueOf(aux2);
                        places_lliures--;
                        entrades++;
                        Button aux = (Button) findViewById(v.getId());
                        aux.setBackgroundColor(0xffff0000);
                    } else {
                        butaca_clicada[i] = 0;
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
            return;
        } else {
            //confirmar();
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Titol", tvTitol.getText().toString());
        bundle.putString("Data", data);
        bundle.putString("DiaSetmana", dataDiaSetmana);
        Intent intent = new Intent(getApplicationContext(), InfoObra.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
