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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_compra);

        tvTitol = (TextView) findViewById(R.id.tv_titol_compra);
        tvData = (TextView) findViewById(R.id.tv_data_compra);
        tvEntrades = (TextView) findViewById(R.id.tv_numero_entrades_compra);
        tvTotal = (TextView) findViewById(R.id.tv_total_compra);
        btConfirmar = (Button) findViewById(R.id.bt_confirmar_compra);
        etMail = (EditText) findViewById(R.id.et_mail_compra);

        btConfirmar.setOnClickListener(this);

        Integer total, entrades;
        String titol, data;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitol.setText(bundle.getString("Titol"));
            //tvData
            entrades = bundle.getInt("Entrades");
            tvEntrades.setText(String.valueOf(entrades));
            total = bundle.getInt("Total");
            tvTotal.setText(String.valueOf(total));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirmar_compra:
                Intent intent = new Intent (getApplicationContext(), FinalCompra.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
