package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {

    DbHelper dbHelper;
    Bundle bundle;
    ListView lvUsuaris;
    String titol, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistar_usuaris);

        lvUsuaris = (ListView) findViewById(R.id.lv_usuaris);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            titol = bundle.getString(String.valueOf(R.string.bundleTitle));
            data = bundle.getString(String.valueOf(R.string.bundleDate));
        }

        dbHelper = new DbHelper(this);
        List<String> llista = new ArrayList<String>();
        String[] usuaris = dbHelper.consultUsers(titol, data);
        if (usuaris != null) {
            for (int i = 0; i < usuaris.length; ++i) {
                if (!usuaris[i].isEmpty())
                    llista.add(usuaris[i]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, usuaris);
            lvUsuaris.setAdapter(adapter);
        }
    }
}
