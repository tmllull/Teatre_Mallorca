package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LlistarDies extends AppCompatActivity {

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    String titol, filtre, dia_setmana, data;
    TextView tvSessio;
    Integer places;

    Bundle bundle;

    private MyCustomAdapterDies adapter;
    ArrayList<Dia> dies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistar_dies);

        tvSessio = (TextView) findViewById(R.id.tv_sessions);
        filtre = "No";
        carregar_view(filtre);
        //carregar_view();

    }

    public void carregar_view(String filtre) {
        dbHelper = new DbHelper(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            titol = bundle.getString("Titol");
        }
        Dia dia = new Dia();
        Cursor c = dbHelper.getDatesObra(titol);
        if (c.moveToFirst()) {
            do {
                String milis = c.getString(c.getColumnIndex(dbHelper.CN_MILIS));
                //Long.valueOf(milis);
                data = c.getString(c.getColumnIndex(dbHelper.CN_DATA));
                boolean disp = comprovar_disponibilitat(milis);
                if (disp) places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                else {
                    //places = 0;
                    //dbHelper.updatePlacesLliures(titol, data, 0);
                    places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                }
                //places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));

                dia_setmana = c.getString(c.getColumnIndex(dbHelper.CN_DIA_SETMANA));
                traduir_dia(dia_setmana);
                if (filtre.equals("No")) {
                    dia = new Dia(titol, places, data, dia_setmana);
                    dies.add(dia);
                }
                else {
                    if (filtre.equals(dia_setmana)) {
                        dia = new Dia(titol, places, data, dia_setmana);
                        dies.add(dia);
                    }
                }
                //dies.add(dia);
            } while (c.moveToNext());
        }

        if (dies.isEmpty()) tvSessio.setText("Cap sessio programada");
        else tvSessio.setText("Sessions programades");

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

    public void carregar_view() {
        dbHelper = new DbHelper(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            titol = bundle.getString("Titol");
        }
        Dia dia;
        Cursor c = dbHelper.getDatesObra(titol);
        if (c.moveToFirst()) {
            do {
                places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                data = c.getString(c.getColumnIndex(dbHelper.CN_DATA));
                dia_setmana = c.getString(c.getColumnIndex(dbHelper.CN_DIA_SETMANA));
                traduir_dia(dia_setmana);
                dia = new Dia(titol, places, data, dia_setmana);
                dies.add(dia);
            } while (c.moveToNext());
        }

        if (dies.isEmpty()) tvSessio.setText("Cap sessio programada");
        else tvSessio.setText("Sessions programades");

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
        getMenuInflater().inflate(R.menu.menu_llista, menu);
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
                updateData("Dilluns");
                return false;
            case R.id.dimarts:
                updateData("Dimarts");
                return false;
            case R.id.dimecres:
                updateData("Dimecres");
                return false;
            case R.id.dijous:
                updateData("Dijous");
                return false;
            case R.id.divendres:
                updateData("Divendres");
                return false;
            case R.id.dissabte:
                updateData("Dissabte");
                return false;
            case R.id.diumenge:
                updateData("Diumenge");
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

    void traduir_dia(String dia_setmana) {
        if (dia_setmana.equals("Mon") || dia_setmana.equals("Lun."))
            this.dia_setmana = "Dilluns";
        else if (dia_setmana.equals("Tue") || dia_setmana.equals("Mar."))
            this.dia_setmana = "Dimarts";
        else if (dia_setmana.equals("Wed") || dia_setmana.equals("Mié."))
            this.dia_setmana = "Dimecres";
        else if (dia_setmana.equals("Thu") || dia_setmana.equals("Jue."))
            this.dia_setmana = "Dijous";
        else if (dia_setmana.equals("Fri") || dia_setmana.equals("Vie."))
            this.dia_setmana = "Divendres";
        else if (dia_setmana.equals("Sat") || dia_setmana.equals("Sáb."))
            this.dia_setmana = "Dissabte";
        else if (dia_setmana.equals("Sun") || dia_setmana.equals("Dom."))
            this.dia_setmana = "Diumenge";
    }

    boolean comprovar_disponibilitat(String milis) {
        /////////////////DATA ACTUAL//////////////////////
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String data_actual = sdf.format(new Date());
        Date d = null;
        try {
            d = sdf.parse(data_actual);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milis = d.getTime();
        /////////////DATA A COMPROVAR//////////////////
        Date d2 = null;
        try {
            d2 = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milis2 = d2.getTime();
            if ((int)milis2 < (int) milis) {
                return false;
            }*/
        Long milis_act = System.currentTimeMillis();
        if (Long.valueOf(milis) < milis_act) return false;
        return true;
    }
}
