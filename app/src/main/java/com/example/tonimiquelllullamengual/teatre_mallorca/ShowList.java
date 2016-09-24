package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ShowList extends AppCompatActivity {

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    private MyCustomAdapterShows adapter;
    ArrayList<Show> shows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        viewLoad();
    }

    public void viewLoad() {
        dbHelper = new DbHelper(this);
        Cursor c = dbHelper.getAllShowsDistinct();
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(dbHelper.CN_TITLE));
                Integer sessions = dbHelper.sessionsCounter(title);
                Show show = new Show(title,sessions.toString());
                shows.add(show);
            } while (c.moveToNext());
        }

        //findViewById del layout activity_main
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);

        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);


        //El adapter se encarga de  adaptar un objeto definido en el c�digo a una vista en xml
        //seg�n la estructura definida.
        //Asignamos nuestro custom Adapter
        adapter = new MyCustomAdapterShows();
        mRecyclerView.setAdapter(adapter);
        adapter.setDataSet(shows);

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
            case R.id.menuDaySelection:
                return false;
            case R.id.menuSeeAll:
                return false;
            default:
                return false;
        }
    }
}

