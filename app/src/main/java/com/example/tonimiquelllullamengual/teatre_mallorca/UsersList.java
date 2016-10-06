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
    ListView lvUsers;
    String title, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        lvUsers = (ListView) findViewById(R.id.lsUsers);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(String.valueOf(R.string.bundleTitle));
            date = bundle.getString(String.valueOf(R.string.bundleDate));
        }

        dbHelper = new DbHelper(this);
        List<String> llista = new ArrayList<String>();
        String[] usuaris = dbHelper.consultUsers(title, date);
        if (usuaris != null) {
            for (int i = 0; i < usuaris.length; ++i) {
                if (!usuaris[i].isEmpty())
                    llista.add(usuaris[i]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, usuaris);
            lvUsers.setAdapter(adapter);
        }
    }
}
