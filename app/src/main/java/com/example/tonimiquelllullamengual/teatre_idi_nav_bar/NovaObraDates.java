package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NovaObraDates extends AppCompatActivity implements View.OnClickListener {

    Button btGuardar;

    private DatePickerDialog pdDia1, pdDia2, pdDia3, pdDia4, pdDia5, pdDia6, pdDia7;

    private SimpleDateFormat formatDate;

    private Bundle bundle;

    private DbHelper dbHelper;

    TextView tvDia1, tvDia2, tvDia3, tvDia4, tvDia5, tvDia6, tvDia7;
    ArrayList<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_obra_dates);

        tvDia1 = (TextView) findViewById(R.id.tv_dia_1);
        tvDia2 = (TextView) findViewById(R.id.tv_dia_2);
        tvDia3 = (TextView) findViewById(R.id.tv_dia_3);
        tvDia4 = (TextView) findViewById(R.id.tv_dia_4);
        tvDia5 = (TextView) findViewById(R.id.tv_dia_5);
        tvDia6 = (TextView) findViewById(R.id.tv_dia_6);
        tvDia7 = (TextView) findViewById(R.id.tv_dia_7);
        btGuardar = (Button) findViewById(R.id.bt_guardar_dates_obra);

        tvDia1.setOnClickListener(this);
        tvDia2.setOnClickListener(this);
        tvDia3.setOnClickListener(this);
        tvDia4.setOnClickListener(this);
        tvDia5.setOnClickListener(this);
        tvDia6.setOnClickListener(this);
        tvDia7.setOnClickListener(this);
        btGuardar.setOnClickListener(this);

        formatDate = new SimpleDateFormat("dd-MM-yy");

        dates = new ArrayList<>();

        dbHelper = new DbHelper(this);

        prepareCalendar();

        bundle = getIntent().getExtras();

    }

    public void prepareCalendar() {

        Calendar calendari = Calendar.getInstance();
        pdDia1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia1.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
        pdDia2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia2.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
        pdDia3 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia3.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
        pdDia4 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia4.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
        pdDia5 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia5.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
        pdDia6 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia6.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
        pdDia7 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvDia7.setText(formatDate.format(date.getTime()));
            }
        }, calendari.get(Calendar.YEAR), calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH));
    }

    public void guardarObra() {
        /*int total = 0;
        if (!tvDia1.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia1.getText().toString());total++;
        if (!tvDia2.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia2.getText().toString());total++;
        if (!tvDia3.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia3.getText().toString());total++;
        if (!tvDia4.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia4.getText().toString());total++;
        if (!tvDia5.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia5.getText().toString());total++;
        if (!tvDia6.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia6.getText().toString());total++;
        if (!tvDia7.getText().toString().equals("Seleccionar data"))
            dates.add(tvDia7.getText().toString());total++;
        if (total == 0) {
            Toast.makeText(getApplicationContext(), "No queden places lliures",
                    Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < total; ++i) {

        }*/

        int cont = 0;

        String from = tvDia1.getText().toString();
        String to = tvDia2.getText().toString();
        char[] aux1 = from.toCharArray();
        char[] aux2 = to.toCharArray();
        String any_from = String.valueOf(aux1[6]) + String.valueOf(aux1[7]);
        Integer any_from_val = Integer.valueOf(any_from);
        String mes_from = String.valueOf(aux1[3]) + String.valueOf(aux1[4]);
        Integer mes_from_val = Integer.valueOf(mes_from);
        String dia_from = String.valueOf(aux1[0]) + String.valueOf(aux1[1]);
        Integer dia_from_val = Integer.valueOf(dia_from);
        //
        String any_to = String.valueOf(aux2[6]) + String.valueOf(aux2[7]);
        Integer any_to_val = Integer.valueOf(any_to);
        String mes_to = String.valueOf(aux2[3]) + String.valueOf(aux2[4]);
        Integer mes_to_val = Integer.valueOf(mes_to);
        String dia_to = String.valueOf(aux2[0]) + String.valueOf(aux2[1]);
        Integer dia_to_val = Integer.valueOf(dia_to);

        String diaObra = dia_from_val.toString();
        String mesObra = mes_from_val.toString();
        String anyObra = any_from_val.toString();
        //String dataObra = diaObra + "-" + mesObra + "-" + anyObra;

        /*Toast.makeText(getApplicationContext(), "DIA FROM " + dia_from_val.toString() +
                        " MES FROM " + mes_from + " ANY FROM " + any_from +
                        "DIA TO" + dia_to +
                        " MES TO" + mes_to + " ANY TO" + any_to,
                Toast.LENGTH_LONG).show();*/


        if (any_to_val > any_from_val) {

        } else if (mes_to_val > mes_from_val) {

        } else if (dia_to_val > dia_from_val) {
            for (int i = dia_from_val; i < dia_to_val; ++i) {
                String dataObra = i + "-" + mesObra + "-" + anyObra;
                String places = "-";
                for (int j = 1; j < 41; ++j) {
                    //Plaça lliure indicat amb un 1
                    places = places + "1";
                }
                ContentValues values = new ContentValues();
                values.put(dbHelper.CN_NOM, bundle.getString("Nom"));
                values.put(dbHelper.CN_DESCRIPCIO, bundle.getString("Descripcio"));
                values.put(dbHelper.CN_DURADA, bundle.getString("Durada"));
                values.put(dbHelper.CN_PREU, bundle.getString("Preu"));
                values.put(dbHelper.CN_DATA, dataObra.toString());
                values.put(dbHelper.CN_BUTAQUES, places);
                values.put(dbHelper.CN_PLACES_LLIURES, 40);

                dbHelper.newObra(values, dbHelper.OBRA_TABLE);

                /*places = "-";
                for (int j = 1; j < 41; ++j) {
                    //Plaça lliure indicat amb un 1
                    places = places + "1";
                }
                values = new ContentValues();
                values.put(dbHelper.CN_NOM, bundle.getString("Nom"));
                values.put(dbHelper.CN_DESCRIPCIO, bundle.getString("Descripcio"));
                values.put(dbHelper.CN_DURADA, bundle.getString("Durada"));
                values.put(dbHelper.CN_PREU, bundle.getString("Preu"));
                values.put(dbHelper.CN_DATA, dataObra.toString());
                values.put(dbHelper.CN_BUTAQUES, places);
                values.put(dbHelper.CN_PLACES_LLIURES, 40);

                dbHelper.newObra(values, dbHelper.OBRA_TABLE);

                places = "-";
                for (int j = 1; j < 41; ++j) {
                    //Plaça lliure indicat amb un 1
                    places = places + "1";
                }
                values = new ContentValues();
                values.put(dbHelper.CN_NOM, bundle.getString("Nom"));
                values.put(dbHelper.CN_DESCRIPCIO, bundle.getString("Descripcio"));
                values.put(dbHelper.CN_DURADA, bundle.getString("Durada"));
                values.put(dbHelper.CN_PREU, bundle.getString("Preu"));
                values.put(dbHelper.CN_DATA, dataObra.toString());
                values.put(dbHelper.CN_BUTAQUES, places);
                values.put(dbHelper.CN_PLACES_LLIURES, 40);

                dbHelper.newObra(values, dbHelper.OBRA_TABLE);
                //dbHelper.close();*/
                ++cont;
            }

        } else {

        }
        Toast.makeText(getApplicationContext(), String.valueOf(cont),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dia_1:
                pdDia1.show();
                break;
            case R.id.tv_dia_2:
                pdDia2.show();
                break;
            case R.id.tv_dia_3:
                pdDia3.show();
                break;
            case R.id.tv_dia_4:
                pdDia4.show();
                break;
            case R.id.tv_dia_5:
                pdDia5.show();
                break;
            case R.id.tv_dia_6:
                pdDia6.show();
                break;
            case R.id.tv_dia_7:
                pdDia7.show();
                break;
            case R.id.bt_guardar_dates_obra:
                guardarObra();
                break;
            default:
                break;
        }
    }
}
