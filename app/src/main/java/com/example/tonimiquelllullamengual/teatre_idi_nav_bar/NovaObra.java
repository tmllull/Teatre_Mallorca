package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovaObra extends AppCompatActivity implements View.OnClickListener {

    private Button btNew;
    private EditText etNom, etDescripcio, etDurada, etPreu, etData;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_obra);

        btNew = (Button) findViewById(R.id.bt_confirmarNovaObra);
        etNom = (EditText) findViewById(R.id.et_nomNovaObra);
        etDescripcio = (EditText) findViewById(R.id.et_descripcioNovaObra);
        etDurada = (EditText) findViewById(R.id.et_duradaNovaObra);
        etPreu = (EditText) findViewById(R.id.et_preuNovaObra);
        etData = (EditText) findViewById(R.id.et_dataNovaObra);

        btNew.setOnClickListener(this);

        dbHelper = new DbHelper(this);
    }

    public void newObra(View v) {
        if (etNom.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Has d'emplenar tots els camps",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else  {
            Cursor c = dbHelper.getObra(String.valueOf(etNom.getText()));
            if (c.moveToFirst()) {
                Toast.makeText(getApplicationContext(), "L'obra ja existeix",
                        Toast.LENGTH_LONG).show();
                return;
            }
            byte[] places = new byte[41];
            for (int i = 0; i < 41; ++i) {
                places[i] = 0;
            }
            ContentValues values = new ContentValues();
            values.put(dbHelper.CN_NOM, String.valueOf(etNom));
            values.put(dbHelper.CN_DESCRIPCIO, String.valueOf(etDescripcio));
            values.put(dbHelper.CN_DURADA, String.valueOf(etDurada));
            values.put(dbHelper.CN_PREU, String.valueOf(etPreu));
            values.put(dbHelper.CN_DATA, String.valueOf(etData));
            values.put(dbHelper.CN_BUTAQUES, places);

            dbHelper.newObra(values, dbHelper.OBRA_TABLE);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
