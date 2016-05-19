package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class NovaObra extends AppCompatActivity implements View.OnClickListener {

    private Button btNew;
    private EditText etNom, etDescripcio, etDurada, etPreu, etData;
    private Spinner spinner;
    private List<String> lista;
    private TextView tvData;

    private DatePickerDialog pickerDialog;

    private SimpleDateFormat formatDate;

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
        tvData = (TextView) findViewById(R.id.tv_data_nova_obra);

        btNew.setOnClickListener(this);
        tvData.setOnClickListener(this);

        formatDate = new SimpleDateFormat("dd-MM-yy");

        prepareCalendar();

        dbHelper = new DbHelper(this);

        //carregar_spinner();
    }

    public void prepareCalendar() {
        Calendar calendari = Calendar.getInstance();
        pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvData.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
    }

    public void newObra() {
        if (etNom.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Has d'emplenar tots els camps",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else  {
            /*Cursor c = dbHelper.getObra(String.valueOf(etNom.getText()));
            if (c.moveToFirst()) {
                String data = c.getString(c.getColumnIndex(dbHelper.CN_DATA));
                if (data.equals(etData))
                    Toast.makeText(getApplicationContext(), "Ja hi ha una obra amb el mateix " +
                            "nom per aquest dia.",
                            Toast.LENGTH_LONG).show();
                return;
            }*/
            String places = "-";
            for (int i = 1; i < 41; ++i) {
                places = places+"1";
            }
            ContentValues values = new ContentValues();
            values.put(dbHelper.CN_NOM, etNom.getText().toString());
            values.put(dbHelper.CN_DESCRIPCIO, etDescripcio.getText().toString());
            values.put(dbHelper.CN_DURADA, etDurada.getText().toString());
            values.put(dbHelper.CN_PREU, etPreu.getText().toString());
            values.put(dbHelper.CN_DATA, tvData.getText().toString());
            //values.put(dbHelper.CN_DATA, spinner.getSelectedItem().toString());
            values.put(dbHelper.CN_BUTAQUES, places);
            values.put(dbHelper.CN_PLACES_LLIURES, 40);

            dbHelper.newObra(values, dbHelper.OBRA_TABLE);
        }
    }

    public void carregar_spinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        lista = new ArrayList<String>();
        spinner = (Spinner) this.findViewById(R.id.spinner);
        lista.add("Dilluns");
        lista.add("Dimarts");
        lista.add("Dimecres");
        lista.add("Dijous");
        lista.add("Divendres");
        lista.add("Dissabte");
        lista.add("Diumenge");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirmarNovaObra:
                newObra();
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_data_nova_obra:
                pickerDialog.show();
            default:
                break;
        }
    }
}
