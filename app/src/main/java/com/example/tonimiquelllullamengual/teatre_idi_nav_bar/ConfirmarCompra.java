package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmarCompra extends AppCompatActivity implements View.OnClickListener{

    TextView tvTitol, tvData, tvEntrades, tvTotal;
    Button btConfirmar;
    EditText etMail;

    Bundle bundle;

    DbHelper dbHelper;

    String data, butaques_seleccionades, titol;
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

        Integer total, entrades;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitol.setText(bundle.getString("Titol"));
            titol = bundle.getString("Titol");
            tvData.setText(bundle.getString("Data"));
            entrades = bundle.getInt("Entrades");
            tvEntrades.setText(String.valueOf(entrades));
            total = bundle.getInt("Total");
            tvTotal.setText(String.valueOf(total));
            data = bundle.getString("Data");
            places_lliures = bundle.getInt("Places");
            butaques_seleccionades = bundle.getString("Butaques");
        }
    }

    void confirmar_compra() {
        dbHelper.updateOcupacio(tvTitol.getText().toString(), data, butaques_seleccionades);
        dbHelper.updatePlacesLliures(tvTitol.getText().toString(), data, places_lliures);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirmar_compra:
                confirmar_compra();
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Titol",titol);
        bundle.putString("Data", data);
        Intent intent = new Intent(getApplicationContext(), OcupacioButaques.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
