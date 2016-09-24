package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoShow extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle;
    TextView tvTitle, tvDescription, tvPrice, tvPlaces, tvDuration, tvDate, tvSeatsSelection;
    DbHelper dbHelper;
    boolean freePlaces = false;
    String title, date, dayOfTheWeek;
    ImageView ivBuy;
    Integer places, available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_show);

        dbHelper = new DbHelper(this);

        tvTitle = (TextView) findViewById(R.id.tvInfoTitle);
        tvDescription = (TextView) findViewById(R.id.tvInfoDescription);
        tvPrice = (TextView) findViewById(R.id.tvInfoPrice);
        tvPlaces = (TextView) findViewById(R.id.tvInfoPlaces);
        tvDuration = (TextView) findViewById(R.id.tvInfoDuration);
        tvDate = (TextView) findViewById(R.id.tvInfoDate);
        ivBuy = (ImageView) findViewById(R.id.ivInfoBuy);
        tvSeatsSelection = (TextView) findViewById(R.id.tvSeatSelection);

        //comprar.setOnClickListener(this);
        tvSeatsSelection.setOnClickListener(this);

        ivBuy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (available == 0) {
                    Toast.makeText(getApplicationContext(), "Aquesta sessió ha expirat. " +
                            "No es poden comprar tickets", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (freePlaces) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", title);
                    bundle.putString("Date", date);
                    bundle.putString("DayOfTheWeek", dayOfTheWeek);
                    bundle.putInt("Hold", 0); //Per indicar que és un accés nou
                    Intent intent = new Intent(getApplicationContext(), PlacesOcupacy.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "No queden places per aquesta " +
                        "funció", Toast.LENGTH_SHORT).show();
            }
        });

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("Title");
            date = bundle.getString("Date");
            dayOfTheWeek = bundle.getString("DayOfTheWeek");
            available = bundle.getInt("Available");
        }

        Cursor c = dbHelper.getShow(title, date);
        if (c.moveToFirst()) {
            tvTitle.setText(title);
            tvDescription.setText(c.getString(c.getColumnIndex(dbHelper.CN_DESCRIPTION)));
            tvDuration.setText(c.getInt(c.getColumnIndex(dbHelper.CN_DURATION))+" min.");
            tvDate.setText(dayOfTheWeek +", "+ date);
            tvPrice.setText(c.getString(c.getColumnIndex(dbHelper.CN_PRICE))+"€");
            places = c.getInt(c.getColumnIndex(dbHelper.CN_FREE_SEATS));
            tvPlaces.setText(places.toString());

            if (c.getInt(c.getColumnIndex(dbHelper.CN_FREE_SEATS)) > 0) freePlaces = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSeatSelection:
                if (available == 0) {
                    Toast.makeText(getApplicationContext(), "Aquesta sessió ha expirat. " +
                            "No es poden comprar tickets", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (freePlaces) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", title);
                    bundle.putString("Date", date);
                    bundle.putString("DayOfTheWeek", dayOfTheWeek);
                    bundle.putInt("Hold", 0); //Per indicar que és un accés nou
                    Intent intent = new Intent(getApplicationContext(), PlacesOcupacy.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "No queden places per aquesta " +
                            "funció", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.menuDeleteShow:
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar funció")
                        .setMessage("Estàs segur que vols eliminar la funció? Aquesta " +
                                "operació només afecta a aquesta sessió, però no es pot " +
                                "desfer.")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deteleSession(title, date);
                                Bundle bundle = new Bundle();
                                bundle.putString("Title", title);
                                Intent intent = new Intent(getApplicationContext(),
                                        DaysList.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No!!!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.trash)
                        .show();
                return false;
            case R.id.menuUsersShow:
                Bundle bundle = new Bundle();
                bundle.putString("Title", title);
                bundle.putString("Date", date);
                Intent intent = new Intent(getApplicationContext(), UsersList.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Title", title);
        Intent intent = new Intent(getApplicationContext(), DaysList.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
