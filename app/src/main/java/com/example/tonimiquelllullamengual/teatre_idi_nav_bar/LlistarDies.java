package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LlistarDies extends AppCompatActivity {

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    String filtre;
    String titolAux, dia_setmana;

    Bundle bundle;

    private MyCustomAdapterDies adapter;
    ArrayList<Dia> dies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistar_dies);
        filtre = "No";
        carregar_view(filtre);

    }

    public void carregar_view(String filtre) {
        dbHelper = new DbHelper(this);

        bundle = getIntent().getExtras();
        String titol = bundle.getString("Titol");
        titolAux = titol;
        Dia dia = new Dia();
        Cursor c = dbHelper.getAllObresData(titol);
        if (c.moveToFirst()) {
            do {
                String nom = c.getString(c.getColumnIndex(dbHelper.CN_NOM));
                Integer places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                String data = c.getString(c.getColumnIndex(dbHelper.CN_DATA));
                String dia_setmana = c.getString(c.getColumnIndex(dbHelper.CN_DIA_SETMANA));
                if (filtre.equals("No")) {
                    dia = new Dia(nom, places, data, dia_setmana);
                }
                else {
                    if (filtre.equals(dia_setmana)) dia = new Dia(nom, places, data, dia_setmana);
                }
                dies.add(dia);
            } while (c.moveToNext());
        }

        //findViewById del layout activity_main
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerViewDies);

        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);

        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);


        //El adapter se encarga de  adaptar un objeto definido en el c�digo a una vista en xml
        //seg�n la estructura definida.
        //Asignamos nuestro custom Adapter
        adapter = new MyCustomAdapterDies();
        mRecyclerView.setAdapter(adapter);
        adapter.setDataSet(dies);

    }

    public void updateData(String filtre) {
        dies = new ArrayList<>();
        carregar_view(filtre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_llista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.menu_seleccionar_dia:
                //ordre = false;
                //updateData(ordre);
                return false;
            case R.id.dilluns:
                updateData("Mon");
                return false;
            case R.id.dimarts:
                updateData("Tue");
                return false;
            case R.id.dimecres:
                updateData("Wed");
                return false;
            case R.id.dijous:
                updateData("Thu");
                return false;
            case R.id.divendres:
                updateData("Fri");
                return false;
            case R.id.dissabte:
                updateData("Sat");
                return false;
            case R.id.diumenge:
                updateData("Sun");
                return false;
            case R.id.menu_mostrar_tot:
                updateData("No");
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
