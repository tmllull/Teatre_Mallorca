package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NovaObra extends AppCompatActivity implements View.OnClickListener {

    private Button btNew;
    private EditText etNom, etDescripcio, etDurada, etPreu, etData;
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

        btNew.setOnClickListener(this);

        formatDate = new SimpleDateFormat("dd-MM-yy");

        prepareCalendar();

        dbHelper = new DbHelper(this);

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
            String places = "-";
            for (int i = 1; i < 41; ++i) {
                //Plaça lliure indicat amb un 1
                places = places+"1";
            }
            ContentValues values = new ContentValues();
            values.put(dbHelper.CN_NOM, etNom.getText().toString());
            values.put(dbHelper.CN_DESCRIPCIO, etDescripcio.getText().toString());
            values.put(dbHelper.CN_DURADA, etDurada.getText().toString());
            values.put(dbHelper.CN_PREU, etPreu.getText().toString());
            values.put(dbHelper.CN_DATA, tvData.getText().toString());
            values.put(dbHelper.CN_BUTAQUES, places);
            values.put(dbHelper.CN_PLACES_LLIURES, 40);

            dbHelper.newObra(values, dbHelper.OBRA_TABLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirmarNovaObra:
                if (etNom.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Has d'emplenar tots els camps",
                            Toast.LENGTH_LONG).show();
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("Nom",etNom.getText().toString());
                bundle.putString("Descripcio", etDescripcio.getText().toString());
                bundle.putString("Durada", etDurada.getText().toString());
                bundle.putString("Preu", etPreu.getText().toString());
                Intent intent = new Intent (getApplicationContext(), NovaObraDates.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
