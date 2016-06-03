package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NovaObraDates extends AppCompatActivity implements View.OnClickListener {

    Button btGuardar;

    private DatePickerDialog pdDia1, pdDia2;
    private SimpleDateFormat formatDate;
    private Bundle bundle;
    private DbHelper dbHelper;
    private String from, to, any_from, any_to, mes_from, mes_to, dia_from, dia_to,
            diaObra, mesObra, anyObra;
    private int cont;
    private Integer dia_from_val, mes_from_val, any_from_val, dia_to_val, mes_to_val, any_to_val;
    private Boolean ok = true;

    TextView tvDia1, tvDia2;
    //ArrayList<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_obra_dates);

        cont = 0;

        tvDia1 = (TextView) findViewById(R.id.tv_dia_1);
        tvDia2 = (TextView) findViewById(R.id.tv_dia_2);
        btGuardar = (Button) findViewById(R.id.bt_guardar_dates_obra);

        tvDia1.setOnClickListener(this);
        tvDia2.setOnClickListener(this);
        btGuardar.setOnClickListener(this);

        formatDate = new SimpleDateFormat("dd/MM/yy");

        //dates = new ArrayList<>();

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
    }

    public void guardarObra() {

        //Guardem els valors del dia, mes y any de la data inici
        from = tvDia1.getText().toString();
        char[] aux1 = from.toCharArray();
        any_from = String.valueOf(aux1[6]) + String.valueOf(aux1[7]);
        any_from_val = Integer.valueOf(any_from);
        mes_from = String.valueOf(aux1[3]) + String.valueOf(aux1[4]);
        mes_from_val = Integer.valueOf(mes_from);
        dia_from = String.valueOf(aux1[0]) + String.valueOf(aux1[1]);
        dia_from_val = Integer.valueOf(dia_from);

        //Guardem els valor del dia, mes i any de la data final
        to = tvDia2.getText().toString();
        char[] aux2 = to.toCharArray();
        any_to = String.valueOf(aux2[6]) + String.valueOf(aux2[7]);
        any_to_val = Integer.valueOf(any_to);
        mes_to = String.valueOf(aux2[3]) + String.valueOf(aux2[4]);
        mes_to_val = Integer.valueOf(mes_to);
        dia_to = String.valueOf(aux2[0]) + String.valueOf(aux2[1]);
        dia_to_val = Integer.valueOf(dia_to);

        //Variables que utilitzarem per formar la data final
        diaObra = dia_from_val.toString();
        mesObra = mes_from_val.toString();
        anyObra = any_from_val.toString();

        if (any_from_val != any_to_val) {
            Toast.makeText(getApplicationContext(), "Afegir obres entre diferents anys no està " +
                            "implementat",
                    Toast.LENGTH_LONG).show();
            ok = false;
            return;
        } else if (mes_from_val > mes_to_val) {
            Toast.makeText(getApplicationContext(), "El rang de dates és incorrecte",
                    Toast.LENGTH_LONG).show();
            ok = false;
            return;
        } else if (mes_to_val > mes_from_val) {
            while (mes_to_val > mes_from_val) {
                mesObra = mes_from_val.toString();
                if (mes_from_val == 1 || mes_from_val == 3 || mes_from_val == 5 ||
                        mes_from_val == 7 || mes_from_val == 8 || mes_from_val == 10 ||
                        mes_from_val == 12) {
                    calcul_dies(dia_from_val, 31);
                    dia_from_val = 1;
                    mes_from_val++;
                } else if (mes_from_val == 2) {
                    calcul_dies(dia_from_val, 28);
                    dia_from_val = 1;
                    mes_from_val++;
                } else {
                    calcul_dies(dia_from_val, 30);
                    dia_from_val = 1;
                    mes_from_val++;
                }
                mesObra = mes_from_val.toString();
            }
            calcul_dies(dia_from_val, dia_to_val);
            ok = true;
        } else if (dia_to_val >= dia_from_val) {
            calcul_dies(dia_from_val, dia_to_val);
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
            String dataObra = i + "/" + mesObra + "/" + anyObra;
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
            values.put(dbHelper.CN_TITOL, bundle.getString("Nom"));
            values.put(dbHelper.CN_DESCRIPCIO, bundle.getString("Descripcio"));
            values.put(dbHelper.CN_DURADA, bundle.getString("Durada"));
            values.put(dbHelper.CN_PREU, String.valueOf(bundle.getString("Preu")));
            values.put(dbHelper.CN_DATA, dataObra.toString());
            values.put(dbHelper.CN_BUTAQUES, places);
            values.put(dbHelper.CN_MILIS, String.valueOf(milliseconds));
            values.put(dbHelper.CN_PLACES_LLIURES, 40);
            values.put(dbHelper.CN_COMPRADORS, "^");
            values.put(dbHelper.CN_DIA_SETMANA, dia_setmana);


            dbHelper.newObra(values, dbHelper.OBRA_TABLE);
            ++cont;
        }
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
            case R.id.bt_guardar_dates_obra:
                if (tvDia1.getText().toString().equals("Seleccionar data") ||
                        tvDia2.getText().toString().equals("Seleccionar data")) {
                    Toast.makeText(getApplicationContext(), "Has de seleccionar data d'inici" +
                                    " i data de fi",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                guardarObra();
                if (ok) {
                    Toast.makeText(getApplicationContext(), "S'han afegit " + String.valueOf(cont) + " dates",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
