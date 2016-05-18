package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OcupacioButaques extends AppCompatActivity implements View.OnClickListener {

    private Button[] butaca = new Button[41];

    Bundle bundle;
    DbHelper dbHelper;
    TextView tvTitol;
    Button btComprar;

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
        for(int i = 1; i < 41; i++) {
            butaca[i] = (Button) findViewById(r.getIdentifier("button" + i, "id", name));
            butaca[i].setOnClickListener(this);
        }

        tvTitol = (TextView) findViewById(R.id.tv_titol_ocupacio);
        btComprar = (Button) findViewById(R.id.bt_Comprar_ocupacio);
        btComprar.setOnClickListener(this);

        String places = "init places";

        dbHelper = new DbHelper(this);
        bundle = getIntent().getExtras();
        String titol = "Titol inicial";
        if (bundle != null) {
            titol = bundle.getString("Titol");
        }
        tvTitol.setText(titol.toString());
        /*Toast.makeText(getApplicationContext(), titol,
                Toast.LENGTH_LONG).show();*/

        Cursor c = dbHelper.getObra(titol);
        if (c.moveToFirst()) {
            places = c.getString(c.getColumnIndex(dbHelper.CN_BUTAQUES));
        }
        /*Toast.makeText(getApplicationContext(), places,
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
        /*Toast.makeText(getApplicationContext(), aux,
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ocupacio, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Button aux = (Button) findViewById(v.getId());
        aux.setBackgroundColor(0xffff0000);
        return;
    }
}
