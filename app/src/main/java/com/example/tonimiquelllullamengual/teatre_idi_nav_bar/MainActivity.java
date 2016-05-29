package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DbHelper dbHelper;

    Button btAfegir, btEliminar, btButaques, btUsuaris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //init_data();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DbHelper(this);

        btAfegir = (Button) findViewById(R.id.bt_afegir_obra_main);
        btEliminar = (Button) findViewById(R.id.bt_eliminar_obra_main);
        btButaques = (Button) findViewById(R.id.bt_llistar_obres_main);
        btUsuaris = (Button) findViewById(R.id.bt_mostrar_usuaris_main);

        btAfegir.setOnClickListener(this);
        btEliminar.setOnClickListener(this);
        btButaques.setOnClickListener(this);
        btUsuaris.setOnClickListener(this);

        //reset = (Button) findViewById(R.id.bt_reset);
        //init = (Button) findViewById(R.id.init_data);
        /*reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Base de dades eliminada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dbHelper.resetAll();
            }
        });*/

        /*init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Dades iniciades", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                init_data();
            }
        });*/
        //init_data();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.init_data) {
            init_data();
        } else if (id == R.id.delete_data) {
            dbHelper.resetAll();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init_data() {
        String dataObra = "03-05-16";
        String places = "-";
        int p = 0;
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) places = places + "0";
            else {
                places = places + "1";
                p++;
            }
        }

        /*SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy");
        Date d = null;
        try {
            d = (Date) f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();*/
        ContentValues values = new ContentValues();
        values.put(dbHelper.CN_NOM, "El rey leon");
        values.put(dbHelper.CN_DESCRIPCIO, "Mor un lleó. Fin");
        values.put(dbHelper.CN_DURADA, String.valueOf(120));
        values.put(dbHelper.CN_PREU, String.valueOf(60));
        values.put(dbHelper.CN_DATA, dataObra);
        values.put(dbHelper.CN_BUTAQUES, places.toString());
        //values.put(dbHelper.CN_MILIS, milliseconds);
        values.put(dbHelper.CN_PLACES_LLIURES, p);

        dbHelper.newObra(values, dbHelper.OBRA_TABLE);

        p = 0;
        places = "-";
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) places = places + "0";
            else {
                places = places + "1";
                p++;
            }
        }
        dataObra = "03-05-16";
        /*f = new SimpleDateFormat("dd-MM-yy");
        d = null;
        try {
            d = (Date) f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();*/
        values = new ContentValues();
        values.put(dbHelper.CN_NOM, "Mamma Mia");
        values.put(dbHelper.CN_DESCRIPCIO, "Cuando serás mia");
        values.put(dbHelper.CN_DURADA, String.valueOf(90));
        values.put(dbHelper.CN_PREU, String.valueOf(45));
        values.put(dbHelper.CN_DATA, dataObra);
        values.put(dbHelper.CN_PLACES_LLIURES, p);
        //values.put(dbHelper.CN_MILIS, milliseconds);
        values.put(dbHelper.CN_BUTAQUES, places.toString());

        dbHelper.newObra(values, dbHelper.OBRA_TABLE);

        p = 0;
        places = "-";
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) places = places + "0";
            else {
                places = places + "1";
                p++;
            }
        }
        /*dataObra = "03-05-16";
        f = new SimpleDateFormat("dd-MM-yy");
        d = null;
        try {
            d = (Date) f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();*/
        values = new ContentValues();
        values.put(dbHelper.CN_NOM, "Queen");
        values.put(dbHelper.CN_DESCRIPCIO, "Freddy for president");
        values.put(dbHelper.CN_DURADA, String.valueOf(120));
        values.put(dbHelper.CN_PREU, String.valueOf(60));
        values.put(dbHelper.CN_DATA, dataObra);
        values.put(dbHelper.CN_BUTAQUES, places.toString());
        //values.put(dbHelper.CN_MILIS, milliseconds);
        values.put(dbHelper.CN_PLACES_LLIURES, p);

        dbHelper.newObra(values, dbHelper.OBRA_TABLE);

        p = 0;
        places = "-";
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) places = places + "0";
            else {
                places = places + "1";
                p++;
            }
        }
        /*dataObra = "04-05-16";
        f = new SimpleDateFormat("dd-MM-yy");
        d = null;
        try {
            d = (Date) f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();*/
        values = new ContentValues();
        values.put(dbHelper.CN_NOM, "Queen");
        values.put(dbHelper.CN_DESCRIPCIO, "Freddy for president");
        values.put(dbHelper.CN_DURADA, String.valueOf(120));
        values.put(dbHelper.CN_PREU, String.valueOf(60));
        values.put(dbHelper.CN_DATA, dataObra);
        values.put(dbHelper.CN_BUTAQUES, places.toString());
        //values.put(dbHelper.CN_MILIS, milliseconds);
        values.put(dbHelper.CN_PLACES_LLIURES, p);

        dbHelper.newObra(values, dbHelper.OBRA_TABLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_afegir_obra_main:
                intent = new Intent(getApplicationContext(), NovaObra.class);
                startActivity(intent);
                break;
            case R.id.bt_eliminar_obra_main:
                intent = new Intent(getApplicationContext(), EliminarObra.class);
                startActivity(intent);
                break;
            case R.id.bt_llistar_obres_main:
                intent = new Intent(getApplicationContext(), LlistarObres.class);
                startActivity(intent);
                break;
            case R.id.bt_mostrar_usuaris_main:
                //intent = new Intent(getApplicationContext(), NovaObra.class);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }
}
