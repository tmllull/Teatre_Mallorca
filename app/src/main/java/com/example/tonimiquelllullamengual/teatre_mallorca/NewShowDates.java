package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewShowDates extends AppCompatActivity implements View.OnClickListener {

    Button btSave;

    private DatePickerDialog pdDay1, pdDay2;
    private SimpleDateFormat formatDate;
    private Bundle bundle;
    private DbHelper dbHelper;
    private String from, to, yearFrom, yearTo, monthFrom, monthTo, dayFrom, dayTo,
            dayShow, monthShow, yearShow;
    private int cont;
    private int dayFromVal, monthFromVal, yearFromVal, dayToVal, monthToVal, yearToVal;
    private Boolean ok = true;
    private CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;

    TextView tvDay1, tvDay2;

    Calendar calendar = new GregorianCalendar();
    Calendar date1, date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_show_dates);

        cont = 0;

        tvDay1 = (TextView) findViewById(R.id.tvDay1);
        tvDay2 = (TextView) findViewById(R.id.tvDay2);
        btSave = (Button) findViewById(R.id.btSaveDatesShow);
        cbMon = (CheckBox) findViewById(R.id.cbMon);
        cbTue = (CheckBox) findViewById(R.id.cbTue);
        cbWed = (CheckBox) findViewById(R.id.cbWeb);
        cbThu = (CheckBox) findViewById(R.id.cbThu);
        cbFri = (CheckBox) findViewById(R.id.cbFri);
        cbSat = (CheckBox) findViewById(R.id.cbSat);
        cbSun = (CheckBox) findViewById(R.id.cbSun);

        tvDay1.setOnClickListener(this);
        tvDay2.setOnClickListener(this);
        btSave.setOnClickListener(this);

        formatDate = new SimpleDateFormat("dd/MM/yy");

        dbHelper = new DbHelper(this);

        prepareCalendar();

        bundle = getIntent().getExtras();

    }

    public void prepareCalendar() {

        Calendar calendar = Calendar.getInstance();
        pdDay1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date1 = Calendar.getInstance();
                date1.set(year, monthOfYear, dayOfMonth);
                tvDay1.setText(formatDate.format(date1.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        pdDay2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date2 = Calendar.getInstance();
                date2.set(year, monthOfYear, dayOfMonth);
                tvDay2.setText(formatDate.format(date2.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void saveShow() {

        //Guardem els valors del day, mes y any de la date inici
        from = tvDay1.getText().toString();
        char[] aux1 = from.toCharArray();
        yearFrom = String.valueOf(aux1[6]) + String.valueOf(aux1[7]);
        yearFromVal = Integer.valueOf(yearFrom);
        monthFrom = String.valueOf(aux1[3]) + String.valueOf(aux1[4]);
        monthFromVal = Integer.valueOf(monthFrom);
        dayFrom = String.valueOf(aux1[0]) + String.valueOf(aux1[1]);
        dayFromVal = Integer.valueOf(dayFrom);

        yearFromVal = date1.get(date1.YEAR);
        monthFromVal = date1.get(date1.MONTH)+1;
        dayFromVal = date1.get(date1.DAY_OF_MONTH);


        //Guardem els valor del day, mes i any de la date final
        /*to = tvDay2.getText().toString();
        char[] aux2 = to.toCharArray();
        yearTo = String.valueOf(aux2[6]) + String.valueOf(aux2[7]);
        yearToVal = Integer.valueOf(yearTo);
        monthTo = String.valueOf(aux2[3]) + String.valueOf(aux2[4]);
        monthToVal = Integer.valueOf(monthTo);
        dayTo = String.valueOf(aux2[0]) + String.valueOf(aux2[1]);
        dayToVal = Integer.valueOf(dayTo);*/

        yearToVal = date2.get(date2.YEAR);
        monthToVal = date2.get(date2.MONTH)+1;
        dayToVal = date2.get(date2.DAY_OF_MONTH);

        //Variables que utilitzarem per formar la date final
        dayShow = String.valueOf(dayFromVal);
        monthShow = String.valueOf(monthFromVal);
        yearShow = String.valueOf(yearFromVal);

        if (yearFromVal != yearToVal) {
            Toast.makeText(getApplicationContext(), "Afegir shows entre diferents anys no està " +
                            "implementat",
                    Toast.LENGTH_LONG).show();
            ok = false;
            return;
        } else if (monthFromVal > monthToVal) {
            Toast.makeText(getApplicationContext(), "El rang de dates és incorrecte",
                    Toast.LENGTH_LONG).show();
            ok = false;
            return;
        } else if (monthToVal > monthFromVal) {
            while (monthToVal > monthFromVal) {
                monthShow = String.valueOf(monthFromVal);
                if (monthFromVal == 1 || monthFromVal == 3 || monthFromVal == 5 ||
                        monthFromVal == 7 || monthFromVal == 8 || monthFromVal == 10 ||
                        monthFromVal == 12) {
                    calcul_dies(dayFromVal, 31);
                    dayFromVal = 1;
                    monthFromVal++;
                } else if (monthFromVal == 2) {
                    calcul_dies(dayFromVal, 28);
                    dayFromVal = 1;
                    monthFromVal++;
                } else {
                    calcul_dies(dayFromVal, 30);
                    dayFromVal = 1;
                    monthFromVal++;
                }
                monthShow = String.valueOf(monthFromVal);
            }
            calcul_dies(dayFromVal, dayToVal);
            ok = true;
        } else if (dayToVal >= dayFromVal) {
            calcul_dies(dayFromVal, dayToVal);
            ok = true;
        } else {
            Toast.makeText(getApplicationContext(), "El rang de dates és incorrecte",
                    Toast.LENGTH_LONG).show();
            ok = false;
            return;
        }
    }

    public void calcul_dies(int dia_from_val, int dia_to_val) {
        for (int i = dia_from_val; i <= dia_to_val; ++i) {
            String dataObra = i + "/" + monthShow + "/" + yearShow;
            String places = "-";
            String dia_setmana = "";
            for (int j = 1; j < 41; ++j) {
                //Plaça lliure indicat amb un 1
                places = places + "1";
            }
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
            Date d = null;
            try {
                d = f.parse(dataObra);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("c");
            dia_setmana = formatter.format(new java.sql.Date(milliseconds));
            ContentValues values = new ContentValues();
            values.put(dbHelper.CN_TITLE, bundle.getString("Nom"));
            values.put(dbHelper.CN_DESCRIPTION, bundle.getString("Descripcio"));
            values.put(dbHelper.CN_DURATION, bundle.getString("Durada"));
            values.put(dbHelper.CN_PRICE, String.valueOf(bundle.getString("Preu")));
            values.put(dbHelper.CN_DATE, dataObra.toString());
            values.put(dbHelper.CN_SEATS, places);
            values.put(dbHelper.CN_MILIS, String.valueOf(milliseconds));
            values.put(dbHelper.CN_FREE_SEATS, 40);
            values.put(dbHelper.CN_CLIENTS, "^");
            values.put(dbHelper.CN_DAY_OF_THE_WEEK, dia_setmana);

            SimpleDateFormat formatter2 = new SimpleDateFormat("c");
            String days[] = new String[7];

            calendar.set(Calendar.DAY_OF_WEEK, 2);
            for (int j = 0; j < 7; ++j) {
                days[j] = formatter2.format(calendar.getTime());
                calendar.add(calendar.DAY_OF_WEEK, 1);
            }

            if (cbMon.isChecked()){
                if (dia_setmana.equals(days[0])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
            if (cbTue.isChecked()) {
                if (dia_setmana.equals(days[1])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
            if (cbWed.isChecked()) {
                if (dia_setmana.equals(days[2])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
            if (cbThu.isChecked()) {
                if (dia_setmana.equals(days[3])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
            if (cbFri.isChecked()) {
                if (dia_setmana.equals(days[4])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
            if (cbSat.isChecked()) {
                if (dia_setmana.equals(days[5])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
            if (cbSun.isChecked()) {
                if (dia_setmana.equals(days[6])) {
                    dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                    ++cont;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDay1:
                pdDay1.show();
                break;
            case R.id.tvDay2:
                pdDay2.show();
                break;
            case R.id.btSaveDatesShow:
                if (tvDay1.getText().toString().equals("Seleccionar date") ||
                        tvDay2.getText().toString().equals("Seleccionar date")) {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar date d'inici" +
                                    " i date de fi",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (!cbMon.isChecked() && !cbTue.isChecked() && !cbWed.isChecked() &&
                        !cbThu.isChecked() && !cbFri.isChecked() && !cbSat.isChecked()
                        && !cbSun.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Selecciona almenys un day",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                saveShow();
                if (ok) {
                    if (cont == 0) {
                        Toast.makeText(getApplicationContext(), "No es pot afegir cap sessió " +
                                "en aquest rang de dates i days seleccionats",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "S'han afegit " + String.valueOf(cont) + " dates",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), NewShow.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
