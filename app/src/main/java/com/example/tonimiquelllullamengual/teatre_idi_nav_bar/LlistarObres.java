package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class LlistarObres extends AppCompatActivity {

    //ListView obres;

    DbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;

    private MyCustomAdapter adapter;
    ArrayList<Obra> obres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistar_obres);

        dbHelper = new DbHelper(this);

        Cursor c = dbHelper.getAllObres();
        if (c.moveToFirst()) {
            do {
                String nom = c.getString(c.getColumnIndex(dbHelper.CN_NOM));
                Integer places = c.getInt(c.getColumnIndex(dbHelper.CN_PLACES_LLIURES));
                Obra obra = new Obra(nom, places);
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
        adapter = new MyCustomAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setDataSet(obres);
    }
}
