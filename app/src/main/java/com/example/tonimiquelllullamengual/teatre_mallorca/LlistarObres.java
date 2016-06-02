package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class LlistarObres extends AppCompatActivity {

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    boolean ordre = false;

    private MyCustomAdapterObres adapter;
    ArrayList<Obra> obres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistar_obres);

        carregar_view();

    }

    public void carregar_view() {
        dbHelper = new DbHelper(this);
        Cursor c = dbHelper.getAllObresDistinct();
        if (c.moveToFirst()) {
            do {
                String titol = c.getString(c.getColumnIndex(dbHelper.CN_TITOL));
                Integer sessions = dbHelper.comptarSessions(titol);
                Obra obra = new Obra(titol,sessions.toString());
                obres.add(obra);
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
        adapter = new MyCustomAdapterObres();
        mRecyclerView.setAdapter(adapter);
        adapter.setDataSet(obres);

    }

    public void updateData(boolean ordre) {
        obres = new ArrayList<>();
        carregar_view();
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
            case R.id.menu_mostrar_tot:
                //ordre = true;
                //updateData(ordre);
                return false;
            default:
                return false;
        }
    }
}
