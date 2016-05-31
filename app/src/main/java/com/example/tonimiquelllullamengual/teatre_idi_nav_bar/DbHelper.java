package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    //Versió de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Nom de la base de datos
    public static final String DATABASE_NAME = "database.sqlite";

    //Nom de la taula
    public static final String OBRA_TABLE ="Obra";

    public static final String CN_NOM = "nom";
    public static final String CN_DESCRIPCIO = "descripcio";
    public static final String CN_DATA = "data";
    public static final String CN_DURADA = "durada";
    public static final String CN_PREU = "preu";
    public static final String CN_BUTAQUES = "butaques";
    public static final String CN_PLACES_LLIURES = "places_lliures";
    public static final String CN_MILIS = "milis";
    public static final String CN_COMPRADORS = "compradors";


    public static final String OBRA_TABLE_CREATE = "CREATE TABLE " + OBRA_TABLE + "( " +
            CN_NOM + " TEXT, " +
            CN_DESCRIPCIO + " TEXT, " +
            CN_DATA + " TEXT, " +
            CN_DURADA + " INTEGER, " +
            CN_PREU + " INTEGER, " +
            CN_BUTAQUES + " TEXT, " +
            CN_PLACES_LLIURES + " INTEGER, " +
            CN_MILIS + " TEXT, " +
            CN_COMPRADORS + " TEXT, " +
            "PRIMARY KEY (nom,data));";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void newObra (ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(
                tableName,
                null,
                values);
    }

    //Obtenir una obra
    public Cursor getObra (String obra) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS};
        String[] where = {obra};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                    // The columns to return
                "nom=?",                                   // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    //Obtenir totes les obres ordenades pel nom
    public Cursor getAllObres() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS};
        //Cursor c = db.rawQuery( "SELECT DISTINCT nom FROM Obra", null);
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_NOM + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir totes les obres ordenades pel nom
    public Cursor getAllObresDistinct() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS};
        String[] where = {CN_NOM};
        Cursor c = db.query(true,
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                CN_NOM,             // don't group the rows
                null,               // don't filter by row groups
                CN_NOM + " ASC",    // The sort order
                null
        );
        return c;
    }


    //Obtenir totes les dates d'una obra
    public Cursor getAllObresData(String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS};
        String[] where = {nom};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "nom=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir una funció d'una obra a una data determinada
    public Cursor getObraData(String nom, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS};
        String[] where = {nom, data};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "nom=?" + " and " + "data=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_DATA + " ASC"                // The sort order
        );
        return c;
    }

    public int comptarSessions(String nom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] where = {nom};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                null,            // The columns to return
                "nom=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        int cnt = c.getCount();
        //c.close();
        return cnt;
    }

    public String[] consultarUsuaris(String nom, String data) {
        Cursor c = this.getUsuaris(nom, data);
        String[] usuaris;
        String s_usuaris = new String();
        if (c.moveToFirst()) {
            s_usuaris = c.getString(c.getColumnIndex(CN_COMPRADORS));
        }
        if (s_usuaris != null) {
            usuaris = s_usuaris.split("\\^");
            return usuaris;
        }
        return null;
    }

    //Obtenir una funció d'una obra a una data determinada
    public Cursor getUsuaris(String nom, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS};
        String[] where = {nom, data};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "nom=?" + " and " + "data=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        return c;
    }

    public void updateData (String nom, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_DATA, data);
        db.update(OBRA_TABLE, values, CN_NOM + "=?", new String[]{nom});
    }

    public void updateOcupacio(String nom, String data, String ocupacio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_BUTAQUES, ocupacio);
        db.update(OBRA_TABLE, values, CN_NOM + "=?" + " and " + "data=?", new String[]{nom, data});
    }

    public void updatePlacesLliures(String nom, String data, int places) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_PLACES_LLIURES, places);
        db.update(OBRA_TABLE, values, CN_NOM + "=?" + " and " + "data=?", new String[]{nom, data});
    }

    public void updateDescripcio(String nom, String ocupacio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_BUTAQUES, ocupacio);
        db.update(OBRA_TABLE, values, CN_NOM + "=?", new String[]{nom});
    }

    public void updateCompradors(String nom, String data, String compradors) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_COMPRADORS, compradors);
        db.update(OBRA_TABLE, values, CN_NOM + "=?" + " and " + "data=?", new String[]{nom, data});
    }

    //
    public void deteleObra (String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, CN_NOM + "=?", new String[]{nom});
    }

    public void deteleFuncio (String nom, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, CN_NOM + "=?" + " and " + "data=?", new String[]{nom, data});
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OBRA_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void resetAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, null, null);
    }

    public void initData() {
        String usuaris = "^";
        String usuari = "usuari@prova.com";
        String dataObra = "03-05-16";
        String places = "-";
        int p = 0;
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) {
                places = places + "0";
                usuaris = i+usuari+"^"+usuaris;
            }
            else {
                places = places + "1";
                p++;
            }
        }

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy");
        java.util.Date d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        ContentValues values = new ContentValues();
        values.put(this.CN_NOM, "El rey leon");
        values.put(this.CN_DESCRIPCIO, "Mor un lleó. Fin");
        values.put(this.CN_DURADA, String.valueOf(120));
        values.put(this.CN_PREU, String.valueOf(60));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_COMPRADORS, usuaris);

        this.newObra(values, this.OBRA_TABLE);

        usuaris = "^";
        p = 0;
        places = "-";
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) {
                places = places + "0";
                usuaris = i+usuari+"^"+usuaris;
            }
            else {
                places = places + "1";
                p++;
            }
        }
        dataObra = "03-05-16";
        f = new SimpleDateFormat("dd-MM-yy");
        d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();
        values = new ContentValues();
        values.put(this.CN_NOM, "Mamma Mia");
        values.put(this.CN_DESCRIPCIO, "Cuando serás mia");
        values.put(this.CN_DURADA, String.valueOf(90));
        values.put(this.CN_PREU, String.valueOf(45));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_COMPRADORS, usuaris);


        this.newObra(values, this.OBRA_TABLE);

        usuaris = "^";
        p = 0;
        places = "-";
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) {
                places = places + "0";
                usuaris = i+usuari+"^"+usuaris;
            }
            else {
                places = places + "1";
                p++;
            }
        }
        dataObra = "05-05-16";
        f = new SimpleDateFormat("dd-MM-yy");
        d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();
        values = new ContentValues();
        values.put(this.CN_NOM, "Queen");
        values.put(this.CN_DESCRIPCIO, "Freddy for president");
        values.put(this.CN_DURADA, String.valueOf(120));
        values.put(this.CN_PREU, String.valueOf(60));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_COMPRADORS, usuaris);

        this.newObra(values, this.OBRA_TABLE);

        usuaris = "^";
        p = 0;
        places = "-";
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) {
                places = places + "0";
                usuaris = i+usuari+"^"+usuaris;
            }
            else {
                places = places + "1";
                p++;
            }
        }
        dataObra = "03-05-16";
        f = new SimpleDateFormat("dd-MM-yy");
        d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();
        values = new ContentValues();
        values.put(this.CN_NOM, "Queen");
        values.put(this.CN_DESCRIPCIO, "Freddy for president");
        values.put(this.CN_DURADA, String.valueOf(120));
        values.put(this.CN_PREU, String.valueOf(60));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_COMPRADORS, usuaris);

        this.newObra(values, this.OBRA_TABLE);
    }
}