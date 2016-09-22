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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LlistarDies extends AppCompatActivity {

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    String titol, filtre, dia_setmana, data;
    TextView tvSessio, tvTitol;
    Integer places;

    Bundle bundle;

    Calendar calendar = new GregorianCalendar();

    private MyCustomAdapterDies adapter;
    ArrayList<Dia> dies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistar_dies);

        tvSessio = (TextView) findViewById(R.id.tv_sessions);
        tvTitol = (TextView) findViewById(R.id.tv_llistar_sessions_titol);
        filtre = "No";
        carregar_view(filtre);

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
                //boolean disp = comprovar_disponibilitat(milis);
                /*if (disp) places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                else {
                    places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                }*/
                places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                dia_setmana = c.getString(c.getColumnIndex(dbHelper.CN_DIA_SETMANA));
                //traduir_dia(dia_setmana);
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
            } while (c.moveToNext());
        }

        if (dies.isEmpty()) tvSessio.setText("Cap sessio programada per");
        else tvSessio.setText(R.string.selectDate);
        tvTitol.setText(titol);

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
        //Selecció de dia de filtrat

        SimpleDateFormat formatter = new SimpleDateFormat("c");
        String days[] = new String[7];

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 7; ++i) {
            days[i] = formatter.format(calendar.getTime());
            calendar.add(calendar.DAY_OF_WEEK, 1);
        }
        switch (item.getItemId()) {
            case R.id.menu_seleccionar_dia:
                return false;
            case R.id.dilluns:
                updateData(days[0]);
                return false;
            case R.id.dimarts:
                updateData(days[1]);
                return false;
            case R.id.dimecres:
                updateData(days[2]);
                return false;
            case R.id.dijous:
                updateData(days[3]);
                return false;
            case R.id.divendres:
                updateData(days[4]);
                return false;
            case R.id.dissabte:
                updateData(days[5]);
                return false;
            case R.id.diumenge:
                updateData(days[6]);
                return false;
            case R.id.menu_mostrar_tot:
                updateData("No");
                return false;
            case R.id.afegir_un_dia:
                modificar_dates(1);
                return false;
            case R.id.ampliar_dates:
                modificar_dates(2);
                return false;
            case R.id.reduir_dates:
                //modificar_dates(3);
                Toast.makeText(getApplicationContext(), "Aquesta funcionalitat no està implementada",
                        Toast.LENGTH_SHORT).show();
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

    //Traduim el dia que ens retorna el sistema al català (de moment des d'anglès i espanyol)
    /*void traduir_dia(String dia_setmana) {
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
    }*/

    void modificar_dates(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("Titol", titol);
        bundle.putInt("Opcio", i);
        Intent intent = new Intent(getApplicationContext(), ModificarDates.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /*
    //Comprovem que la sessió no hagi expirat
    boolean comprovar_disponibilitat(String milis) {
        Long milis_act = System.currentTimeMillis();
        if (Long.valueOf(milis) < milis_act) return false;
        return true;
    }*/
}
