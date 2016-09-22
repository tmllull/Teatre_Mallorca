package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ConfirmarCompra extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitol, tvData, tvEntrades, tvTotal;
    Button btConfirmar;
    EditText etMail;

    Bundle bundle;

    DbHelper dbHelper;

    String data, butaques_seleccionades, titol, dia_setmana;
    Integer places_lliures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_compra);

        dbHelper = new DbHelper(this);

        tvTitol = (TextView) findViewById(R.id.tv_titol_compra);
        tvData = (TextView) findViewById(R.id.tv_data_compra);
        tvEntrades = (TextView) findViewById(R.id.tv_numero_entrades_compra);
        tvTotal = (TextView) findViewById(R.id.tv_total_compra);
        btConfirmar = (Button) findViewById(R.id.bt_confirmar_compra);
        etMail = (EditText) findViewById(R.id.et_mail_compra);

        btConfirmar.setOnClickListener(this);

        Integer entrades;
        Double total;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            titol = bundle.getString("Titol");
            tvTitol.setText(titol);
            entrades = bundle.getInt("Entrades");
            tvEntrades.setText(String.valueOf(entrades));
            total = bundle.getDouble("Total");
            DecimalFormat total_nou = new DecimalFormat("0.00");
            tvTotal.setText(total_nou.format(total) + "€");
            places_lliures = bundle.getInt("Places");
            butaques_seleccionades = bundle.getString("Butaques");
            dia_setmana = bundle.getString("DiaSetmana");
            data = bundle.getString("Data");
            tvData.setText(dia_setmana + ", " + data);
        }
    }

    void confirmar_compra() {
        if (!etMail.getText().toString().isEmpty()) {
            dbHelper.updateOcupacio(tvTitol.getText().toString(), data, butaques_seleccionades);
            dbHelper.updatePlacesLliures(tvTitol.getText().toString(), data, places_lliures);
            Cursor c = dbHelper.getUsuaris(tvTitol.getText().toString(), data);
            if (c.moveToFirst()) {
                String usuari = c.getString(c.getColumnIndex(dbHelper.CN_COMPRADORS));
                usuari = etMail.getText().toString() + "^" + usuari;
                dbHelper.updateCompradors(tvTitol.getText().toString(), data, usuari);
            }
            Toast.makeText(getApplicationContext(), "La seva compra s'ha realitzat correctament",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Has d'emplenar el mail com a mínim",
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirmar_compra:
                confirmar_compra();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Titol", titol);
        bundle.putString("Data", data);
        bundle.putString("DiaSetmana", dia_setmana);
        Intent intent = new Intent(getApplicationContext(), OcupacioButaques.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
