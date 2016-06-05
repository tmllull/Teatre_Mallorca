package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

    Bundle bundle;

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

        formatDate = new SimpleDateFormat("dd/MM/yy");

        prepareCalendar();

        dbHelper = new DbHelper(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            etNom.setText(bundle.getString("Nom"));
            etDescripcio.setText(bundle.getString("Descripcio"));
            etDurada.setText(bundle.getString("Durada"));
            etPreu.setText(bundle.getString("Preu"));
        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirmarNovaObra:
                String titol = etNom.getText().toString().toUpperCase();
                Cursor c = dbHelper.comprovarObra(titol);
                if (c.moveToFirst()) {
                    Toast.makeText(getApplicationContext(), "Ja exixteix una obra amb aquest títol",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (etNom.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Has d'emplenar tots els camps",
                            Toast.LENGTH_LONG).show();
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("Nom",titol);
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
