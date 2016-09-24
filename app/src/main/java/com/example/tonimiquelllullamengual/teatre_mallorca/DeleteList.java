package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteList extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    Spinner spinnerShows;

    List<String> shows = new ArrayList<>();
    Button btDelete;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_show);

        dbHelper = new DbHelper(this);

        spinnerShows = (Spinner) findViewById(R.id.spShows);
        btDelete = (Button) findViewById(R.id.btDeleteShow);

        LoadSpinnerShows();
        spinnerShows.setOnItemSelectedListener(this);
        btDelete.setOnClickListener(this);

    }

    void LoadSpinnerShows() {
        // Spinner shows
        shows.add("Selecciona una obra de la llista");

        Cursor c = dbHelper.getAllShowsDistinct();
        //Cursor c = dbHelper.getAllShows();
        if (c.moveToFirst()) {
            do {
                String name = c.getString(c.getColumnIndex(dbHelper.CN_TITLE));
                shows.add(name);
            } while (c.moveToNext());
        }

        // Creem adaptador
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, shows);

        //
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching date adapter to spinner
        spinnerShows.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String show = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btDeleteShow:
                final String showSelected = spinnerShows.getSelectedItem().toString();
                if (!showSelected.equals("Selecciona una obra de la llista")) {
                    new AlertDialog.Builder(this)
                            .setTitle("Eliminar obra")
                            .setMessage("Aquesta acció no es pot desfer. " +
                                    "Estàs segur que vols eliminar l'obra i totes " +
                                    "les seves funcions?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dbHelper.deteleShow(showSelected);
                                    finish();
                                }
                            })
                            .setNegativeButton("No!!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.trash)
                            .show();
                    break;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar una obra",
                            Toast.LENGTH_LONG).show();
                }
            default:
                break;
        }
    }
}
