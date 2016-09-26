package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewShow extends AppCompatActivity implements View.OnClickListener {

    private Button btNew;
    private EditText etTitle, etDescription, etDuration, etPrice;

    String title;

    Bundle bundle;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_show);

        btNew = (Button) findViewById(R.id.btNewShowConfirm);
        etTitle = (EditText) findViewById(R.id.etNewShowTitle);
        etDescription = (EditText) findViewById(R.id.etNewShowDescription);
        etDuration = (EditText) findViewById(R.id.etNewShowDuration);
        etPrice = (EditText) findViewById(R.id.etNewShowPrice);

        btNew.setOnClickListener(this);

        dbHelper = new DbHelper(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            etTitle.setText(bundle.getString(String.valueOf(R.string.bundleTitle)));
            etDescription.setText(bundle.getString(String.valueOf(R.string.bundleDescription)));
            etDuration.setText(bundle.getString(String.valueOf(R.string.bundleDuration)));
            etPrice.setText(bundle.getString(String.valueOf(R.string.bundlePrice)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNewShowConfirm:
                title = etTitle.getText().toString().trim().toUpperCase();
                Cursor c = dbHelper.checkShow(title);
                if (c.moveToFirst()) {
                    Toast.makeText(getApplicationContext(), "Ja existeix una obra amb aquest títol",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                if (etTitle.getText().toString().trim().isEmpty() ||
                        etDescription.getText().toString().isEmpty() ||
                        etPrice.getText().toString().isEmpty() ||
                        etDuration.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Has d'emplenar tots els camps",
                            Toast.LENGTH_SHORT).show();
                    break;
                }

                //EASTER
                if (etPrice.getText().toString().equals("42") ||
                        etDuration.getText().toString().equals("42")) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Don't panic!")
                            .setMessage("El 42 és un nombre màgic que té la resposta al " +
                                    "sentit de la vida, l'univers i tot el demés. Vols continuar?")
                            .setPositiveButton("   Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(String.valueOf(R.string.bundleTitle), title);
                                    bundle.putString(String.valueOf(R.string.bundleDescription),
                                            etDescription.getText().toString());
                                    bundle.putString(String.valueOf(R.string.bundleDuration),
                                            etDuration.getText().toString());
                                    bundle.putString(String.valueOf(R.string.bundlePrice),
                                            etPrice.getText().toString());
                                    Intent intent = new Intent(getApplicationContext(),
                                            NewShowDates.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                    //break;
                                }
                            })
                            .setNegativeButton("Millor el canvío   ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // do nothing
                                }
                            })
                            .setIcon(R.drawable.ic_menu_slideshow)
                            .show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(String.valueOf(R.string.bundleTitle), title);
                    bundle.putString(String.valueOf(R.string.bundleDescription),
                            etDescription.getText().toString());
                    bundle.putString(String.valueOf(R.string.bundleDuration),
                            etDuration.getText().toString());
                    bundle.putString(String.valueOf(R.string.bundlePrice),
                            etPrice.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), NewShowDates.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
                }
            default:
                break;
        }
    }
}