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

public class ConfirmPurchase extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle, tvDate, tvTickets, tvTotal;
    Button btConfirm;
    EditText etMail;

    Bundle bundle;

    DbHelper dbHelper;

    String date, selectedPlaces, title, dayOfTheWeek;
    Integer freePlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_confirm);

        dbHelper = new DbHelper(this);

        tvTitle = (TextView) findViewById(R.id.tvPurchaseTitle);
        tvDate = (TextView) findViewById(R.id.tvPurchaseDate);
        tvTickets = (TextView) findViewById(R.id.tvPurchaseNumTickets);
        tvTotal = (TextView) findViewById(R.id.tvPurchaseTotal);
        btConfirm = (Button) findViewById(R.id.btPurchaseConfirm);
        etMail = (EditText) findViewById(R.id.etPurchaseMail);

        btConfirm.setOnClickListener(this);

        Integer tickets;
        Double total;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(String.valueOf(R.string.bundleTitle));
            tvTitle.setText(title);
            tickets = bundle.getInt(String.valueOf(R.string.bundleTickets));
            tvTickets.setText(String.valueOf(tickets));
            total = bundle.getDouble(String.valueOf(R.string.bundleTotal));
            DecimalFormat newTotal = new DecimalFormat("0.00");
            tvTotal.setText(newTotal.format(total) + "€");
            freePlaces = bundle.getInt(String.valueOf(R.string.bundlePlaces));
            selectedPlaces = bundle.getString(String.valueOf(R.string.bundleSeats));
            dayOfTheWeek = bundle.getString(String.valueOf(R.string.bundleDayOfTheWeek));
            date = bundle.getString(String.valueOf(R.string.bundleDate));
            tvDate.setText(dayOfTheWeek + ", " + date);
        }
    }

    void confirmPurchase() {
        if (!etMail.getText().toString().isEmpty()) {
            dbHelper.updatePlaces(tvTitle.getText().toString(), date, selectedPlaces);
            dbHelper.updateFreePlaces(tvTitle.getText().toString(), date, freePlaces);
            Cursor c = dbHelper.getUsers(tvTitle.getText().toString(), date);
            if (c.moveToFirst()) {
                String user = c.getString(c.getColumnIndex(dbHelper.CN_CLIENTS));
                user = etMail.getText().toString() + "^" + user;
                dbHelper.updateClients(tvTitle.getText().toString(), date, user);
            }
            Toast.makeText(getApplicationContext(), R.string.purchaseOk,
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), R.string.purchaseNeedMail,
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPurchaseConfirm:
                confirmPurchase();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString(String.valueOf(R.string.bundleTitle), title);
        bundle.putString(String.valueOf(R.string.bundleDate), date);
        bundle.putString(String.valueOf(R.string.bundleDayOfTheWeek), dayOfTheWeek);
        Intent intent = new Intent(getApplicationContext(), PlacesOcupacy.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
