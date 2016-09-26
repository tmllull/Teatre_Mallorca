package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Statistics extends AppCompatActivity {

    DbHelper dbHelper;
    TextView tvTotalEntries, tvMon, tvTue, tvWed, tvThu, tvFri, tvSat, tvSun, tvMonEntries,
            tvTueEntries, tvWedEntries, tvThuEntries, tvFriEntries, tvSatEntries, tvSunEntries;

    LinearLayout lays[] = new LinearLayout[7];

    Percentage percentage[];

    Calendar calendar = new GregorianCalendar();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        ////////////////////////Prova per agafar days setmana////////////////////////

        SimpleDateFormat formatter = new SimpleDateFormat("c");
        String days[] = new String[7];

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 7; ++i) {
            days[i] = formatter.format(calendar.getTime());
            calendar.add(calendar.DAY_OF_WEEK, 1);
        }

        /*for (int i = 0; i < days.length; i++) {
            Log.i("DAYS: ", days[i].toString());
        }*/

        //////////////////////////////////////////////////////////////////////////////

        dbHelper = new DbHelper(this);
        tvTotalEntries = (TextView) findViewById(R.id.tvTotalEntries);
        tvMon = (TextView) findViewById(R.id.tvMonday);
        tvTue = (TextView) findViewById(R.id.tvTuesday);
        tvWed = (TextView) findViewById(R.id.tvWednesday);
        tvThu = (TextView) findViewById(R.id.tvThursday);
        tvFri = (TextView) findViewById(R.id.tvFriday);
        tvSat = (TextView) findViewById(R.id.tvSaturday);
        tvSun = (TextView) findViewById(R.id.tvSunday);
        tvMonEntries = (TextView) findViewById(R.id.tvMonEntries);
        tvTueEntries = (TextView) findViewById(R.id.tvTueEntries);
        tvWedEntries = (TextView) findViewById(R.id.tvWedEntries);
        tvThuEntries = (TextView) findViewById(R.id.tvThuEntries);
        tvFriEntries = (TextView) findViewById(R.id.tvFriEntries);
        tvSatEntries = (TextView) findViewById(R.id.tvSatEntries);
        tvSunEntries = (TextView) findViewById(R.id.tvSunEntries);
        lays[0] = (LinearLayout) findViewById(R.id.layMon);
        lays[1] = (LinearLayout) findViewById(R.id.layTue);
        lays[2] = (LinearLayout) findViewById(R.id.layWed);
        lays[3] = (LinearLayout) findViewById(R.id.layThu);
        lays[4] = (LinearLayout) findViewById(R.id.layFri);
        lays[5] = (LinearLayout) findViewById(R.id.laySat);
        lays[6] = (LinearLayout) findViewById(R.id.laySun);


        //Total d'tickets per days
        int tickets = dbHelper.getTotalEntries();
        int monday = dbHelper.getTickets(days[0]);
        int tuesday = dbHelper.getTickets(days[1]);
        int wednesday = dbHelper.getTickets(days[2]);
        int thursday = dbHelper.getTickets(days[3]);
        int friday = dbHelper.getTickets(days[4]);
        int saturday = dbHelper.getTickets(days[5]);
        int sunday = dbHelper.getTickets(days[6]);

        /*
        int dilluns = dbHelper.getTickets("Mon", "Lun.");
        int dimarts = dbHelper.getTickets("Tue", "Mar.");
        int dimecres = dbHelper.getTickets("Wed", "Mié.");
        int dijous = dbHelper.getTickets("Thu", "Jue.");
        int divendres = dbHelper.getTickets("Fri", "Vie.");
        int dissabte = dbHelper.getTickets("Sat", "Sáb.");
        int diumenge = dbHelper.getTickets("Sun", "Dom.");
        */

        //Detecció de cap entrada venuda encara per evitar problemes
        if (tickets == 0) return;

        //Array on guardem tota la informació de cada day de la setmana
        percentage = new Percentage[7];
        percentage[0] = new Percentage();
        percentage[0].setDay(days[0]);
        percentage[0].setPerc(monday * 100 / tickets);
        percentage[0].setTickets(monday);
        percentage[0].setSales(dbHelper.getIncome(days[0]));
        percentage[1] = new Percentage();
        percentage[1].setDay(days[1]);
        percentage[1].setPerc(tuesday * 100 / tickets);
        percentage[1].setTickets(tuesday);
        percentage[1].setSales(dbHelper.getIncome(days[1]));
        percentage[2] = new Percentage();
        percentage[2].setDay(days[2]);
        percentage[2].setPerc(wednesday * 100 / tickets);
        percentage[2].setTickets(wednesday);
        percentage[2].setSales(dbHelper.getIncome(days[2]));
        percentage[3] = new Percentage();
        percentage[3].setDay(days[3]);
        percentage[3].setPerc(thursday * 100 / tickets);
        percentage[3].setTickets(thursday);
        percentage[3].setSales(dbHelper.getIncome(days[3]));
        percentage[4] = new Percentage();
        percentage[4].setDay(days[4]);
        percentage[4].setPerc(friday * 100 / tickets);
        percentage[4].setTickets(friday);
        percentage[4].setSales(dbHelper.getIncome(days[4]));
        percentage[5] = new Percentage();
        percentage[5].setDay(days[5]);
        percentage[5].setPerc(saturday * 100 / tickets);
        percentage[5].setTickets(saturday);
        percentage[5].setSales(dbHelper.getIncome(days[5]));
        percentage[6] = new Percentage();
        percentage[6].setDay(days[6]);
        percentage[6].setPerc(sunday * 100 / tickets);
        percentage[6].setTickets(sunday);
        percentage[6].setSales(dbHelper.getIncome(days[6]));

        //Ordenació de major a menor en nombre d'tickets venudes
        Arrays.sort(percentage);

        //Array de colors per pintar-los de forma "dinàmica"
        int colors[] = new int[7];
        colors[0] = 0x6f11FF00; //verd
        colors[1] = 0x6f77FF00;
        colors[2] = 0x6fC4FF00;
        colors[3] = 0x6fFFF700;
        colors[4] = 0x6fFFB700;
        colors[5] = 0x6fFF6B00;
        colors[6] = 0x6fFF3300; //vermell

        //Processat de la informació a mostrar
        tvTotalEntries.setText(R.string.tickets + String.valueOf(tickets) + "(100%)\n" +
                R.string.estimated + dbHelper.getTotalSells() + "€");
        int aux = percentage[0].getTickets();
        for (int i = 0, j = 0; i < 7; ++i) {
            if (percentage[i].getDay().equals(days[0])) {
                tvMonEntries.setText(R.string.tickets + String.valueOf(monday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvMon.setText(R.string.estimated + dbHelper.getIncome(days[0]) + "€");
                if (monday == 0) lays[0].setBackgroundColor(colors[6]);
                else if (monday == aux) lays[0].setBackgroundColor(colors[j]);
                else {
                    aux = monday;
                    lays[0].setBackgroundColor(colors[++j]);
                }
            } else if (percentage[i].getDay().equals(days[1])) {
                tvTueEntries.setText(R.string.tickets + String.valueOf(tuesday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvTue.setText(R.string.estimated + dbHelper.getIncome(days[1]) + "€");
                if (tuesday == 0) lays[1].setBackgroundColor(colors[6]);
                else if (tuesday == aux) lays[1].setBackgroundColor(colors[j]);
                else {
                    aux = tuesday;
                    lays[1].setBackgroundColor(colors[++j]);
                }
            } else if (percentage[i].getDay().equals(days[2])) {
                tvWedEntries.setText(R.string.tickets + String.valueOf(wednesday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvWed.setText(R.string.estimated + dbHelper.getIncome(days[2]) + "€");
                if (wednesday == 0) lays[2].setBackgroundColor(colors[6]);
                else if (wednesday == aux) lays[2].setBackgroundColor(colors[j]);
                else {
                    aux = wednesday;
                    lays[2].setBackgroundColor(colors[++j]);
                }
            } else if (percentage[i].getDay().equals(days[3])) {
                tvThuEntries.setText(R.string.tickets + String.valueOf(thursday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvThu.setText(R.string.estimated + dbHelper.getIncome(days[3]) + "€");
                if (thursday == 0) lays[3].setBackgroundColor(colors[6]);
                else if (thursday == aux) lays[3].setBackgroundColor(colors[j]);
                else {
                    aux = thursday;
                    lays[3].setBackgroundColor(colors[++j]);
                }
            } else if (percentage[i].getDay().equals(days[4])) {
                tvFriEntries.setText(R.string.tickets + String.valueOf(friday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvFri.setText(R.string.estimated + dbHelper.getIncome(days[4]) + "€");
                if (friday == 0) lays[4].setBackgroundColor(colors[6]);
                else if (friday == aux) lays[4].setBackgroundColor(colors[j]);
                else {
                    aux = friday;
                    lays[4].setBackgroundColor(colors[++j]);
                }
            } else if (percentage[i].getDay().equals(days[5])) {
                tvSatEntries.setText(R.string.tickets + String.valueOf(saturday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvSat.setText(R.string.estimated + dbHelper.getIncome(days[5]) + "€");
                if (saturday == 0) lays[5].setBackgroundColor(colors[6]);
                else if (saturday == aux) lays[5].setBackgroundColor(colors[j]);
                else {
                    aux = saturday;
                    lays[5].setBackgroundColor(colors[++j]);
                }
            } else if (percentage[i].getDay().equals(days[6])) {
                tvSunEntries.setText(R.string.tickets + String.valueOf(sunday) +
                        "(" + percentage[i].getPerc() + "%)");
                tvSun.setText(R.string.estimated + dbHelper.getIncome(days[6]) + "€");
                if (sunday == 0) lays[6].setBackgroundColor(colors[6]);
                else if (sunday == aux) lays[6].setBackgroundColor(colors[j]);
                else {
                    aux = sunday;
                    lays[6].setBackgroundColor(colors[++j]);
                }
            }
        }
    }
}
