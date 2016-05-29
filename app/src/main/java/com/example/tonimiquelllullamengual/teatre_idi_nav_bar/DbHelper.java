package com.example.tonimiquelllullamengual.teatre_idi_nav_bar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    //sentencia global de cracion de la base de datos
    /*public static final String OBRA_TABLE_CREATE = "CREATE TABLE " + OBRA_TABLE + "( " +
            CN_ID + " integer primary key autoincrement," +
            CN_NOM + " TEXT, " +
            CN_DESCRIPCIO + " TEXT, " +
            CN_DATA + " TEXT, " +
            CN_DURADA + " INTEGER, " +
            CN_PREU + " INTEGER, " +
            CN_BUTAQUES + " TEXT, " +
            CN_PLACES_LLIURES + " INTEGER);";*/

    public static final String OBRA_TABLE_CREATE = "CREATE TABLE " + OBRA_TABLE + "( " +
            CN_NOM + " TEXT, " +
            CN_DESCRIPCIO + " TEXT, " +
            CN_DATA + " TEXT, " +
            CN_DURADA + " INTEGER, " +
            CN_PREU + " INTEGER, " +
            CN_BUTAQUES + " TEXT, " +
            CN_PLACES_LLIURES + " INTEGER, " +
            CN_MILIS + " TEXT, " +
            "PRIMARY KEY (nom,data));";

    //"create table obrasdia ( obra foreign key (Obra.nom), dia INT, ocupados blob) Primary key(obra, dia);"


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
                CN_PLACES_LLIURES, CN_MILIS};
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
                CN_PLACES_LLIURES, CN_MILIS};
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
                CN_PLACES_LLIURES, CN_MILIS};
        String[] where = {CN_NOM};
        //Cursor c = db.rawQuery( "SELECT DISTINCT nom FROM Obra", null);
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


    //
    public Cursor getAllObresData(String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS};
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

    //Obtenir només el nom de totes les obres
    public Cursor getObraData(String nom, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS};
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

    public void updateMasCosas(String nom, String ocupacio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_BUTAQUES, ocupacio);
        db.update(OBRA_TABLE, values, CN_NOM + "=?", new String[]{nom});
    }

    //
    public void deteleObra (String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, CN_NOM + "=?", new String[]{nom});
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
}