package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DbHelper dbHelper;

    Button btAfegir, btEliminar, btButaques, btUsuaris;

    ImageView ivLlistar, ivEliminar;

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
                Intent intent = new Intent(getApplicationContext(), NovaObra.class);
                startActivity(intent);
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
        ivLlistar = (ImageView) findViewById(R.id.iv_main_llistar);
        ivEliminar = (ImageView) findViewById(R.id.iv_main_eliminar);

        btAfegir.setOnClickListener(this);
        btEliminar.setOnClickListener(this);
        btButaques.setOnClickListener(this);
        btUsuaris.setOnClickListener(this);

        ivLlistar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LlistarObres.class);
                startActivity(intent);
            }
        });

        ivEliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LlistarObres.class);
                startActivity(intent);
            }
        });

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
        //getMenuInflater().inflate(R.menu.main, menu);
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
            dbHelper.initData();
        } else if (id == R.id.delete_data) {
            dbHelper.resetAll();
        } else if (id == R.id.nav_help) {
            intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage("Teatre Mallorca\n" +
                            "Autor: Toni Miquel Llull\n" +
                            "Versió: 0.1\n" +
                            "toni.miquel.llull@est.fib.upc.edu")
                    .setPositiveButton("Tancar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.info)
                    .show();
        }
        else if (id == R.id.estadistiques) {
            intent = new Intent(getApplicationContext(), Estadistiques.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            default:
                break;
        }
    }
}
