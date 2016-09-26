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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DaysList extends AppCompatActivity {

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    String title, filter, dayOfTheWeek, date;
    TextView tvSession, tvTitle;
    Integer places;

    Bundle bundle;

    Calendar calendar = new GregorianCalendar();

    private MyCustomAdapterDays adapter;
    ArrayList<Day> days = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_days);

        tvSession = (TextView) findViewById(R.id.tvSessions);
        tvTitle = (TextView) findViewById(R.id.tvListSessionsTitle);
        filter = "No";
        loadView(filter);

    }

    public void loadView(String filter) {
        dbHelper = new DbHelper(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(String.valueOf(R.string.bundleTitle));
        }
        Day day;
        Cursor c = dbHelper.getDatesShow(title);
        if (c.moveToFirst()) {
            do {
                date = c.getString(c.getColumnIndex(dbHelper.CN_DATE));
                places = c.getInt(c.getColumnIndex(dbHelper.CN_FREE_SEATS));
                dayOfTheWeek = c.getString(c.getColumnIndex(dbHelper.CN_DAY_OF_THE_WEEK));
                if (filter.equals(R.string.no)) {
                    day = new Day(title, places, date, dayOfTheWeek);
                    days.add(day);
                } else {
                    if (filter.equals(dayOfTheWeek)) {
                        day = new Day(title, places, date, dayOfTheWeek);
                        days.add(day);
                    }
                }
            } while (c.moveToNext());
        }

        if (days.isEmpty()) tvSession.setText(R.string.noSessions);
        else tvSession.setText(R.string.selectDate);
        tvTitle.setText(title);

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
        adapter = new MyCustomAdapterDays();
        mRecyclerView.setAdapter(adapter);
        adapter.setDataSet(days);

    }

    public void updateData(String filter) {
        days = new ArrayList<>();
        loadView(filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        //Selecció de day de filtrat

        SimpleDateFormat formatter = new SimpleDateFormat("c");
        String days[] = new String[7];

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 7; ++i) {
            days[i] = formatter.format(calendar.getTime());
            calendar.add(calendar.DAY_OF_WEEK, 1);
        }
        switch (item.getItemId()) {
            case R.id.menuDaySelection:
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
            case R.id.menuSeeAll:
                updateData(String.valueOf(R.string.no));
                return false;
            case R.id.afegir_un_dia:
                modificar_dates(1);
                return false;
            case R.id.ampliar_dates:
                modificar_dates(2);
                return false;
            case R.id.reduir_dates:
                //modificar_dates(3);
                Toast.makeText(getApplicationContext(), R.string.notImplemented,
                        Toast.LENGTH_SHORT).show();
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ShowList.class);
        startActivity(intent);
        finish();
    }

    //Traduim el day que ens retorna el sistema al català (de moment des d'anglès i espanyol)
    /*void traduir_dia(String dayOfTheWeek) {
        if (dayOfTheWeek.equals("Mon") || dayOfTheWeek.equals("Lun."))
            this.dayOfTheWeek = "Dilluns";
        else if (dayOfTheWeek.equals("Tue") || dayOfTheWeek.equals("Mar."))
            this.dayOfTheWeek = "Dimarts";
        else if (dayOfTheWeek.equals("Wed") || dayOfTheWeek.equals("Mié."))
            this.dayOfTheWeek = "Dimecres";
        else if (dayOfTheWeek.equals("Thu") || dayOfTheWeek.equals("Jue."))
            this.dayOfTheWeek = "Dijous";
        else if (dayOfTheWeek.equals("Fri") || dayOfTheWeek.equals("Vie."))
            this.dayOfTheWeek = "Divendres";
        else if (dayOfTheWeek.equals("Sat") || dayOfTheWeek.equals("Sáb."))
            this.dayOfTheWeek = "Dissabte";
        else if (dayOfTheWeek.equals("Sun") || dayOfTheWeek.equals("Dom."))
            this.dayOfTheWeek = "Diumenge";
    }*/

    void modificar_dates(int i) {
        Bundle bundle = new Bundle();
        bundle.putString(String.valueOf(R.string.bundleTitle), title);
        bundle.putInt(String.valueOf(R.string.bundleOption), i);
        Intent intent = new Intent(getApplicationContext(), ModifyDates.class);
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
