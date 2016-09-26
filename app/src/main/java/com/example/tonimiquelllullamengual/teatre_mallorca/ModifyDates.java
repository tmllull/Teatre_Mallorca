package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ModifyDates extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle;
    DbHelper dbHelper;
    private DatePickerDialog pdDay1, pdDay2;
    private String from, to, yearFrom, yearTo, monthFrom, monthTo, dayFrom, dayTo,
            showMonth, showYear;

    private Integer dayFromVal, monthFromVal, yearFromVal, dayToVal, monthToVal, yearToVal;

    private Boolean ok = true;

    private int cont;

    TextView tvOneDay, tvLastScheduled, tvAddTo, tvTitle;

    Button btOneDay, btAdd;

    String title, milisLastSession;
    int option;

    private SimpleDateFormat formatDate;

    private CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;

    LinearLayout lyOne, lyAdd, lyReduce;

    Calendar calendar = new GregorianCalendar();
    String days[] = new String[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_dates);

        dbHelper = new DbHelper(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(String.valueOf(R.string.bundleTitle));
            option = bundle.getInt(String.valueOf(R.string.bundleOption));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("c");


        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 7; ++i) {
            days[i] = formatter.format(calendar.getTime());
            calendar.add(calendar.DAY_OF_WEEK, 1);
        }

        formatDate = new SimpleDateFormat("dd/MM/yy");

        lyOne = (LinearLayout) findViewById(R.id.lyChangeDatesOneDay);
        lyAdd = (LinearLayout) findViewById(R.id.lyChangeDateAdd);
        lyReduce = (LinearLayout) findViewById(R.id.lyChangeDatesReduce);
        tvOneDay = (TextView) findViewById(R.id.tvChangeDatesOneDay);
        tvLastScheduled = (TextView) findViewById(R.id.tvChangeDatesLastScheduled);
        tvAddTo = (TextView) findViewById(R.id.tvChangeDatesAddTo);
        btOneDay = (Button) findViewById(R.id.btChangeDatesOneDay);
        btAdd = (Button) findViewById(R.id.btChangeDatesAdd);
        tvTitle = (TextView) findViewById(R.id.tvChangeDatesTitle);

        cbMon = (CheckBox) findViewById(R.id.checkBox);
        cbTue = (CheckBox) findViewById(R.id.checkBox2);
        cbWed = (CheckBox) findViewById(R.id.checkBox3);
        cbThu = (CheckBox) findViewById(R.id.checkBox4);
        cbFri = (CheckBox) findViewById(R.id.checkBox5);
        cbSat = (CheckBox) findViewById(R.id.checkBox6);
        cbSun = (CheckBox) findViewById(R.id.checkBox7);

        tvOneDay.setOnClickListener(this);
        btOneDay.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        tvAddTo.setOnClickListener(this);

        tvTitle.setText(title);

        if (option == 1) { //Un sessio
            lyOne.setVisibility(View.VISIBLE);
            lyAdd.setVisibility(View.GONE);
            lyReduce.setVisibility(View.GONE);
        } else if (option == 2) { //Ampliar dates
            lyOne.setVisibility(View.GONE);
            lyAdd.setVisibility(View.VISIBLE);
            lyReduce.setVisibility(View.GONE);
            prepareExtend();
        } else { //Reduir dates
            lyOne.setVisibility(View.GONE);
            lyAdd.setVisibility(View.GONE);
            lyReduce.setVisibility(View.VISIBLE);
        }

        prepareCalendar();
    }

    public void prepareCalendar() {

        Calendar calendar = Calendar.getInstance();
        pdDay1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvOneDay.setText(formatDate.format(date.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        pdDay2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                tvAddTo.setText(formatDate.format(date.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    void addOneDay() {
        String data = tvOneDay.getText().toString();
        if (data.equals("Seleccionar date")) {
            Toast.makeText(getApplicationContext(), "Has de seleccionar una date",
                    Toast.LENGTH_SHORT).show();

            return;
        }
        char[] aux1 = data.toCharArray();
        String year = String.valueOf(aux1[6]) + String.valueOf(aux1[7]);
        String month = String.valueOf(aux1[3]) + String.valueOf(aux1[4]);
        Integer month_val = Integer.valueOf(month);
        String day = String.valueOf(aux1[0]) + String.valueOf(aux1[1]);
        Integer day_val = Integer.valueOf(day);

        String showDate = day_val + "/" + month_val + "/" + year;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
        Date d = null;
        try {
            d = f.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("c");
        Cursor c = dbHelper.getDatesShow(title);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(dbHelper.CN_MILIS)).equals(String.valueOf(milliseconds))) {
                    Toast.makeText(getApplicationContext(), "Ja hi ha una sessió programada " +
                                    "per aquesta date",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } while (c.moveToNext());
            c.moveToPrevious();
            String dayOfTheWeek = formatter.format(new java.sql.Date(milliseconds));
            String places = "-";
            for (int j = 1; j < 41; ++j) {
                //Plaça lliure indicat amb un 1
                places = places + "1";
            }
            ContentValues values = new ContentValues();
            values.put(dbHelper.CN_TITLE, c.getString(c.getColumnIndex(dbHelper.CN_TITLE)));
            values.put(dbHelper.CN_DESCRIPTION, c.getString(c.getColumnIndex(dbHelper.CN_DESCRIPTION)));
            values.put(dbHelper.CN_DURATION, c.getString(c.getColumnIndex(dbHelper.CN_DURATION)));
            values.put(dbHelper.CN_PRICE, c.getString(c.getColumnIndex(dbHelper.CN_PRICE)));
            values.put(dbHelper.CN_DATE, showDate.toString());
            values.put(dbHelper.CN_SEATS, places);
            values.put(dbHelper.CN_MILIS, String.valueOf(milliseconds));
            values.put(dbHelper.CN_FREE_SEATS, 40);
            values.put(dbHelper.CN_CLIENTS, "^");
            values.put(dbHelper.CN_DAY_OF_THE_WEEK, dayOfTheWeek);
            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
            Toast.makeText(getApplicationContext(), "Nova date afegida correctament",
                    Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putString("Title", title);
            Intent intent = new Intent(getApplicationContext(), DaysList.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    void extendDates() {
        String from = tvLastScheduled.getText().toString();
        SimpleDateFormat read = new SimpleDateFormat("d/M/yy");
        SimpleDateFormat write = new SimpleDateFormat("dd/MM/yy");
        Date d = null;
        try {
            d = read.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvLastScheduled.setText(write.format(d));
        saveShow();
    }

    void prepareExtend() {
        Cursor c = dbHelper.getDatesShowDesc(title);
        if (c.moveToFirst()) {
            tvLastScheduled.setText(c.getString(c.getColumnIndex(dbHelper.CN_DATE)));
            milisLastSession = c.getString(c.getColumnIndex(dbHelper.CN_MILIS));
        }
        String from = tvLastScheduled.getText().toString();
        SimpleDateFormat read = new SimpleDateFormat("d/M/yy");
        SimpleDateFormat write = new SimpleDateFormat("dd/MM/yy");
        Date d = null;
        try {
            d = read.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, 1);
        Date newDate = calendar.getTime();
        tvLastScheduled.setText(write.format(newDate));
    }

    public void saveShow() {

        //Guardem els valors del day, mes y any de la date inici
        from = tvLastScheduled.getText().toString();
        char[] aux1 = from.toCharArray();
        yearFrom = String.valueOf(aux1[6]) + String.valueOf(aux1[7]);
        yearFromVal = Integer.valueOf(yearFrom);
        monthFrom = String.valueOf(aux1[3]) + String.valueOf(aux1[4]);
        monthFromVal = Integer.valueOf(monthFrom);
        dayFrom = String.valueOf(aux1[0]) + String.valueOf(aux1[1]);
        dayFromVal = Integer.valueOf(dayFrom);

        //Guardem els valor del day, mes i any de la date final
        to = tvAddTo.getText().toString();
        char[] aux2 = to.toCharArray();
        yearTo = String.valueOf(aux2[6]) + String.valueOf(aux2[7]);
        yearToVal = Integer.valueOf(yearTo);
        monthTo = String.valueOf(aux2[3]) + String.valueOf(aux2[4]);
        monthToVal = Integer.valueOf(monthTo);
        dayTo = String.valueOf(aux2[0]) + String.valueOf(aux2[1]);
        dayToVal = Integer.valueOf(dayTo);

        //Variables que utilitzarem per formar la date final
        showMonth = monthFromVal.toString();
        showYear = yearFromVal.toString();

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
                showMonth = monthFromVal.toString();
                if (monthFromVal == 1 || monthFromVal == 3 || monthFromVal == 5 ||
                        monthFromVal == 7 || monthFromVal == 8 || monthFromVal == 10 ||
                        monthFromVal == 12) {
                    calculateDays(dayFromVal, 31);
                    dayFromVal = 1;
                    monthFromVal++;
                } else if (monthFromVal == 2) {
                    calculateDays(dayFromVal, 28);
                    dayFromVal = 1;
                    monthFromVal++;
                } else {
                    calculateDays(dayFromVal, 30);
                    dayFromVal = 1;
                    monthFromVal++;
                }
                showMonth = monthFromVal.toString();
            }
            calculateDays(dayFromVal, dayToVal);
            ok = true;
        } else if (dayToVal >= dayFromVal) {
            calculateDays(dayFromVal, dayToVal);
            ok = true;
        } else {
            Toast.makeText(getApplicationContext(), "El rang de dates és incorrecte",
                    Toast.LENGTH_LONG).show();
            ok = false;
            return;
        }
    }

    public void calculateDays(int dayFromVal, int dayToVal) {
        for (int i = dayFromVal; i <= dayToVal; ++i) {
            String showDate = i + "/" + showMonth + "/" + showYear;
            String places = "-";
            String dayOfTheWeek = "";
            for (int j = 1; j < 41; ++j) {
                //Plaça lliure indicat amb un 1
                places = places + "1";
            }
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
            Date d = null;
            try {
                d = f.parse(showDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("c");
            Cursor c = dbHelper.getDatesShow(title);
            if (c.moveToFirst()) {
                if (!c.getString(c.getColumnIndex(dbHelper.CN_MILIS)).equals(milliseconds)) {
                    dayOfTheWeek = formatter.format(new java.sql.Date(milliseconds));
                    ContentValues values = new ContentValues();
                    values.put(dbHelper.CN_TITLE, c.getString(c.getColumnIndex(dbHelper.CN_TITLE)));
                    values.put(dbHelper.CN_DESCRIPTION, c.getString(c.getColumnIndex(dbHelper.CN_DESCRIPTION)));
                    values.put(dbHelper.CN_DURATION, c.getString(c.getColumnIndex(dbHelper.CN_DURATION)));
                    values.put(dbHelper.CN_PRICE, c.getString(c.getColumnIndex(dbHelper.CN_PRICE)));
                    values.put(dbHelper.CN_DATE, showDate.toString());
                    values.put(dbHelper.CN_SEATS, places);
                    values.put(dbHelper.CN_MILIS, String.valueOf(milliseconds));
                    values.put(dbHelper.CN_FREE_SEATS, 40);
                    values.put(dbHelper.CN_CLIENTS, "^");
                    values.put(dbHelper.CN_DAY_OF_THE_WEEK, dayOfTheWeek);

                    if (cbMon.isChecked()) {
                        if (dayOfTheWeek.equals(days[0])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                    if (cbTue.isChecked()) {
                        if (dayOfTheWeek.equals(days[1])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                    if (cbWed.isChecked()) {
                        if (dayOfTheWeek.equals(days[2])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                    if (cbThu.isChecked()) {
                        if (dayOfTheWeek.equals(days[3])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                    if (cbFri.isChecked()) {
                        if (dayOfTheWeek.equals(days[4])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                    if (cbSat.isChecked()) {
                        if (dayOfTheWeek.equals(days[5])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                    if (cbSun.isChecked()) {
                        if (dayOfTheWeek.equals(days[6])) {
                            dbHelper.newShow(values, dbHelper.SHOW_TABLE);
                            ++cont;
                        }
                    }
                }
                while (c.moveToNext()) ;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString(String.valueOf(R.string.bundleTitle), title);
        Intent intent = new Intent(getApplicationContext(), DaysList.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btChangeDatesOneDay:
                addOneDay();
                break;
            case R.id.tvChangeDatesOneDay:
                pdDay1.show();
                break;
            case R.id.tvChangeDatesAddTo:
                pdDay2.show();
                break;
            case R.id.btChangeDatesAdd:
                if (tvAddTo.getText().toString().equals("Seleccionar date")) {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar date " +
                                    "de finalització.",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                extendDates();
                if (ok) {
                    if (cont == 0) {
                        Toast.makeText(getApplicationContext(), "No es pot afegir cap sessió " +
                                        "en aquest rang de dates i days seleccionats",
                                Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "S'han afegit " + String.valueOf(cont) + " dates",
                                Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString(String.valueOf(R.string.bundleTitle), title);
                        intent = new Intent(getApplicationContext(), DaysList.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            default:
                break;
        }
    }
}
