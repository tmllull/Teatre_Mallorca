package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesOcupacy extends AppCompatActivity implements View.OnClickListener {

    private Button[] seat = new Button[41];
    private int[] clickedSeat = new int[41];

    Bundle bundle;
    DbHelper dbHelper;
    TextView tvTitle, tvPurchase;
    Button btPurchase;
    String selectedSeats, date, dayOfTheWeek, title, seats;
    Integer freePlaces, tickets;
    Double price;
    ImageView ivPurchase;
    Integer discount;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocupation_seats);

        Resources r = getResources();
        String name = getPackageName();
        for (int i = 1; i < 41; i++) {
            seat[i] = (Button) findViewById(r.getIdentifier("button" + i, "id", name));
            seat[i].setOnClickListener(this);
            clickedSeat[i] = 0;
        }

        tvTitle = (TextView) findViewById(R.id.tvOcupationTitle);
        btPurchase = (Button) findViewById(R.id.btOcupationPurchase);
        tvPurchase = (TextView) findViewById(R.id.tvPit);
        btPurchase.setOnClickListener(this);
        tvPurchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tickets == 0) {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar com a mínim" +
                                    " una seat",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Aplicar discount")
                            .setMessage("Si tens el carnet Jove o universitari " +
                                    "tens un 30% de discount")
                            .setPositiveButton("   Sí, el tinc", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    discount = 30;
                                    confirmar();
                                }
                            })
                            .setNegativeButton("No tinc cap carnet   ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    confirmar();
                                    // do nothing
                                }
                            })
                            .setIcon(R.drawable.discount)
                            .show();
                }
            }
        });

        discount = 0;
        ivPurchase = (ImageView) findViewById(R.id.ivPit);
        ivPurchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tickets == 0) {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar com a mínim" +
                                    " una seat",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Aplicar discount")
                            .setMessage("Si tens el carnet Jove o universitari " +
                                    "tens un 30% de discount")
                            .setPositiveButton("   Sí, el tinc", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    discount = 30;
                                    confirmar();
                                }
                            })
                            .setNegativeButton("No tinc cap carnet   ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    confirmar();
                                    // do nothing
                                }
                            })
                            .setIcon(R.drawable.discount)
                            .show();
                }
            }
        });

        tickets = 0;
        dbHelper = new DbHelper(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("Title");
            date = bundle.getString("Date");
            dayOfTheWeek = bundle.getString("DayOfTheWeek");
        }
        tvTitle.setText(title);
        Cursor c = dbHelper.getShowDate(title, date);
        if (c.moveToFirst()) {
            seats = c.getString(c.getColumnIndex(dbHelper.CN_SEATS));
            selectedSeats = seats;
            freePlaces = c.getInt(c.getColumnIndex(dbHelper.CN_FREE_SEATS));
            price = c.getDouble(c.getColumnIndex(dbHelper.CN_PRICE));
        }
        String aux;
        for (int i = 1; i < 41; ++i) {
            aux = Character.toString(seats.charAt(i));
            if (aux.equals("0")) {
                seat[i].setBackgroundColor(0xffd3d3d3);
                seat[i].setOnClickListener(null);
            }
        }
    }

    void confirmar() {
        Double total = tickets * price;
        total -= (total * discount) / 100;
        Bundle bundle = new Bundle();
        bundle.putDouble("Total", total);
        bundle.putInt("Tickets", tickets);
        bundle.putString("Title", title);
        bundle.putString("Date", date);
        bundle.putString("Seats", selectedSeats);
        bundle.putInt("Places", freePlaces);
        bundle.putString("DayOfTheWeek", dayOfTheWeek);
        Intent intent = new Intent(getApplicationContext(), ConfirmPurchase.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_ocupacio, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.btOcupationPurchase) {
            for (Integer i = 1; i < 41; i++) {
                if (findViewById(v.getId()).equals(seat[i])) {
                    if (clickedSeat[i] == 0) { //Butaca no clicada
                        clickedSeat[i] = 1;
                        char[] aux2 = selectedSeats.toCharArray();
                        aux2[i] = '0';
                        selectedSeats = String.valueOf(aux2);
                        freePlaces--;
                        tickets++;
                        Button aux = (Button) findViewById(v.getId());
                        aux.setBackgroundColor(0xffff0000);
                    } else {
                        clickedSeat[i] = 0;
                        char[] aux2 = selectedSeats.toCharArray();
                        aux2[i] = '1';
                        selectedSeats = String.valueOf(aux2);
                        freePlaces++;
                        tickets--;
                        Button aux = (Button) findViewById(v.getId());
                        aux.setBackgroundColor(0xff4efe4b);
                    }
                }
            }
            return;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Title", tvTitle.getText().toString());
        bundle.putString("Date", date);
        bundle.putString("DayOfTheWeek", dayOfTheWeek);
        bundle.putInt("Available", 1);
        Intent intent = new Intent(getApplicationContext(), InfoShow.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
