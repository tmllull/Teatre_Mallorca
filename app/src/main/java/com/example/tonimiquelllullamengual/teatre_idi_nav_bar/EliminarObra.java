package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.DialogInterface;
import android.content.Intent;
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

public class EliminarObra extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    Spinner spinner_obres;

    Button btEliminar;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_obra);

        dbHelper = new DbHelper(this);

        spinner_obres = (Spinner) findViewById(R.id.sp_obres);
        btEliminar = (Button) findViewById(R.id.bt_eliminar_eliminar);

        carregar_spinner_obres();
        spinner_obres.setOnItemSelectedListener(this);
        btEliminar.setOnClickListener(this);

    }

    void carregar_spinner_obres() {
        // Spinner obres
        List<String> obres = new ArrayList<>();

        Cursor c = dbHelper.getAllObresDistinct();
        //Cursor c = dbHelper.getAllObres();
        if (c.moveToFirst()) {
            do {
                String nom = c.getString(c.getColumnIndex(dbHelper.CN_NOM));
                obres.add(nom);
            } while (c.moveToNext());
        }

        // Creem adaptador
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, obres);

        //
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_obres.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String obra = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_eliminar_eliminar:
                final String obra_sel = spinner_obres.getSelectedItem().toString();
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar obra")
                        .setMessage("Aquesta acció no es pot desfer. " +
                                "Estàs segur que vols eliminar l'obra i totes " +
                                "les seves funcions?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deteleObra(obra_sel);
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                //startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No!!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.trash)
                        .show();
                break;
            default:
                break;
        }
    }
}
