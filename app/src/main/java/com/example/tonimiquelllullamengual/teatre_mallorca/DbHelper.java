package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    //Variables de generació d'obres automàtiques
    String titol, descripcio, usuaris, usuari, dataObra, places, dia_setmana, durada, preu;
    int p;
    long milliseconds;

    //Versió de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Nom de la base de datos
    public static final String DATABASE_NAME = "database.sqlite";

    //Nom de la taula
    public static final String OBRA_TABLE = "Obra";

    public static final String CN_TITOL = "titol";
    public static final String CN_DESCRIPCIO = "descripcio";
    public static final String CN_DATA = "data";
    public static final String CN_DURADA = "durada";
    public static final String CN_PREU = "preu";
    public static final String CN_BUTAQUES = "butaques";
    public static final String CN_PLACES_LLIURES = "places_lliures";
    public static final String CN_MILIS = "milis";
    public static final String CN_COMPRADORS = "compradors";
    public static final String CN_DIA_SETMANA = "dia_setmana";


    public static final String OBRA_TABLE_CREATE = "CREATE TABLE " + OBRA_TABLE + "( " +
            CN_TITOL + " TEXT, " +
            CN_DESCRIPCIO + " TEXT, " +
            CN_DATA + " TEXT, " +
            CN_DURADA + " INTEGER, " +
            CN_PREU + " INTEGER, " +
            CN_BUTAQUES + " TEXT, " +
            CN_PLACES_LLIURES + " INTEGER, " +
            CN_MILIS + " TEXT, " +
            CN_COMPRADORS + " TEXT, " +
            CN_DIA_SETMANA + " TEXT, " +
            "PRIMARY KEY (titol,data));";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void newObra(ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(
                tableName,
                null,
                values);
    }

    //Obtenir una obra
    public Cursor getObra(String titol) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {titol};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                    // The columns to return
                "titol=?",                                   // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    //Obtenir una sessió en concret
    public Cursor getObra(String titol, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {titol, data};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                    // The columns to return
                "titol=?" + " and " + "data=?",                                   // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    //Obtenir totes les obres amb repetició
    public Cursor getAllObres() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        //Cursor c = db.rawQuery( "SELECT DISTINCT nom FROM Obra", null);
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_TITOL + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir totes les obres ordenades pel nom, sense repetir
    public Cursor getAllObresDistinct() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {CN_TITOL};
        Cursor c = db.query(true,
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                CN_TITOL,             // don't group the rows
                null,               // don't filter by row groups
                CN_TITOL + " ASC",    // The sort order
                null
        );
        return c;
    }

    //Obtenir totes les dates d'una obra
    public Cursor getDatesObra(String titol) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {titol};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "titol=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir una funció d'una obra a una data determinada
    public Cursor getObraData(String titol, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {titol, data};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "titol=?" + " and " + "data=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_DATA + " ASC"                // The sort order
        );
        return c;
    }

    //Compta quantes sessions hi ha programades per una obra
    public int comptarSessions(String titol) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] where = {titol};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                null,            // The columns to return
                "titol=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        int cnt = c.getCount();
        return cnt;
    }

    //Retorna un array amb tots els usuaris per separat d'una sessió
    public String[] consultarUsuaris(String titol, String data) {
        Cursor c = this.getUsuaris(titol, data);
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

    //Obtenir els usuaris d'una obra a una data determinada
    public Cursor getUsuaris(String titol, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {titol, data};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "titol=?" + " and " + "data=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        return c;
    }

    //Obtenir totes les dates d'una obra
    public Cursor getDies(String dia) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL, CN_DESCRIPCIO, CN_DATA, CN_DURADA, CN_PREU, CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {dia};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "dia_setmana=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir les entrades venudes en un mateix dia de la setmana (dilluns, dimarts...)
    public int getEntrades(String dia) {
        Cursor c = this.getDies(dia);
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                int lliures = c.getInt(c.getColumnIndex(CN_PLACES_LLIURES));
                int aux = 40 - lliures;
                cont += aux;
            } while (c.moveToNext());
        }
        return cont;
    }

    //Obtenir el total d'entrades venudes en tot el teatre
    public int getTotalEntrades() {
        Cursor c = this.getAllObres();
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                int lliures = c.getInt(c.getColumnIndex(CN_PLACES_LLIURES));
                int aux = 40 - lliures;
                cont += aux;
            } while (c.moveToNext());
        }
        return cont;
    }

    //Obtenir el total de la recaptació de tot el teatre
    public int getTotalVentes() {
        Cursor c = this.getAllObres();
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                if (c.getInt(c.getColumnIndex(CN_PLACES_LLIURES)) != 0) {
                    int lliures = c.getInt(c.getColumnIndex(CN_PLACES_LLIURES));
                    int aux = 40 - lliures;
                    cont += aux * c.getInt(c.getColumnIndex(CN_PREU));
                }
            } while (c.moveToNext());
        }
        return cont;
    }

    //Obtenir la recaptació d'un mateix dia de la setmana (dilluns, dimarts...)
    public int getRecaptacio(String dia) {
        Cursor c = this.getDies(dia);
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                if (c.getInt(c.getColumnIndex(CN_PLACES_LLIURES)) != 0) {
                    int lliures = c.getInt(c.getColumnIndex(CN_PLACES_LLIURES));
                    int aux = 40 - lliures;
                    cont += aux * c.getInt(c.getColumnIndex(CN_PREU));
                }
            } while (c.moveToNext());
        }
        return cont;
    }

    //Actualitzar butaques ocupades
    public void updateOcupacio(String titol, String data, String ocupacio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_BUTAQUES, ocupacio);
        db.update(OBRA_TABLE, values, CN_TITOL + "=?" + " and " + "data=?", new String[]{titol, data});
    }

    //Actualitzar places lliures
    public void updatePlacesLliures(String titol, String data, int places) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_PLACES_LLIURES, places);
        db.update(OBRA_TABLE, values, CN_TITOL + "=?" + " and " + "data=?", new String[]{titol, data});
    }

    //Actualitzar llista de compradors
    public void updateCompradors(String titol, String data, String compradors) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_COMPRADORS, compradors);
        db.update(OBRA_TABLE, values, CN_TITOL + "=?" + " and " + "data=?", new String[]{titol, data});
    }

    //Eliminar una obra amb totes les seves sessions
    public void deteleObra(String titol) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, CN_TITOL + "=?", new String[]{titol});
    }

    //Eliminar una funció d'una obra
    public void deteleFuncio(String titol, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, CN_TITOL + "=?" + " and " + "data=?", new String[]{titol, data});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OBRA_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Esborrar BD
    public void resetAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, null, null);
    }

    ////////////////Inicialitzem la BD amb 3 obres///////////////////////////////
    public void initData() {
        ///////////////////////////////Obra 1//////////////////////////////////
        titol = "El rey leon";
        descripcio = "Gracias al genio, visión artística y creativa " +
                "de su directora, Julie Taymor, el género musical da un paso adelante. " +
                "Con su sorprendente y colorida puesta en escena, EL REY LEÓN transporta " +
                "al espectador al exotismo africano, con evocadoras músicas, " +
                "constituyendo un nuevo hito en el mundo del espectáculo, un punto " +
                "de inflexión en el diseño artístico, y en general, en el género " +
                "musical, que a nadie deja indiferente. Un genial equipo creativo " +
                "para un musical inolvidable.\n" +
                "\n" +
                "EL REY LEÓN es un musical excepcional, fruto de la unión de " +
                "reconocidos talentos musicales y teatrales a nivel mundial y " +
                "de la fusión de las más sofisticadas disciplinas de las artes " +
                "escénicas africanas, occidentales y asiáticas. \n\n" +
                "Un espectáculo único cargado de valores familiares, que demuestra " +
                "la vinculación de cada uno de nosotros con nuestras raíces. " +
                "EL REY LEÓN hace que el espectador recapacite sobre la importancia " +
                "de cada una de nuestras acciones y el efecto que causan en nuestro " +
                "entorno, así como la importancia de sentir que pertenecemos a un " +
                "grupo, y como todo ello conforma nuestro destino. Además un canto " +
                "al respeto y al amor por la naturaleza.";
        durada = "120";
        preu = "60";
        calcul_dies(1, 27);

        /////////////////////////////////Obra2//////////////////////////////////
        titol = "Mamma mia";
        descripcio = "En una idíl•lica illa grega dies abans de " +
                "contreure matrimoni, una jove decideix convidar al seu pare al casament.\n" +
                "Però, qui serà realment? Quin dels tres homes que van passar per " +
                "la vida de la seva mare ja fa 20 anys hauria de portar-la a l'altar? \n" +
                "\n" +
                "Així comença Mamma Mia! La deliciosa comèdia musical que, construïda " +
                "al voltant de les enganxoses i inoblidables cançons d’ABBA, ha " +
                "aconseguit que 54 milions d'espectadors a tot el món s'hagin enamorat " +
                "dels seus personatges, de la seva música, i d'aquesta encantadora " +
                "història d'amor, d'humor i d'amistat. \n" +
                "\n" +
                "Una invitació a cantar, a ballar i a lliurar-se a l'espectacle des " +
                "del mateix instant en què s'aixeca el teló, gràcies a aquesta injecció " +
                "d'energia positiva que torna a Espanya per fer-nos viatjar de nou al " +
                "costat més optimista i reconfortant de la vida.\n" +
                "\n" +
                "Et resistiràs?";
        generar_places();
        calcul_dies(10, 23);

        ///////////////////////////////////Obra3///////////////////////////////////////////
        titol = "Queen";
        descripcio = "La formación original con Brian May y Roger " +
                "Taylor se une al cantante Adam Lambert para hacernos revivir todo el " +
                "sonido y espectacularidad de uno de los grupos de nuestra vida.\n" +
                "Queen + Adam Lambert hicieron la mayor parte de su gira por el " +
                "hemisferio sur y reservaron una gira de seis fechas en tres países " +
                "latinoamericanos: Brasil, Argentina y Chile, tocando en seis estadios. " +
                "La gira agotó las entradas en un tiempo récord y Queen – esta vez liderado " +
                "por Adam Lambert- hicieron un retorno triunfal al continente, el cual " +
                "en los ochenta les proporcionó el concierto más multitudinario jamás " +
                "logrado: sus dos actuaciones en el Rock in Río de 1985 tuvieron una " +
                "audiencia total de más de medio millón de personas, la audiencia " +
                "pagada más grande de la historia.\n" +
                "\n" +
                "No te lo pierdas y consigue las entradas para el concierto Queen + " +
                "Adam Lambert. ";
        generar_places();
        calcul_dies(5, 28);
    }

    public void calcul_dies(int dia_from_val, int dia_to_val) {
        for (int i = dia_from_val; i <= dia_to_val; ++i) {
            dataObra = i + "/" + 06 + "/" + 16;
            places = "-";
            dia_setmana = "";
            for (int j = 1; j < 41; ++j) {
                //Plaça lliure indicat amb un 1
                places = places + "1";
            }
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
            java.util.Date d = null;
            try {
                d = f.parse(dataObra);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            milliseconds = d.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("c");
            dia_setmana = formatter.format(new java.sql.Date(milliseconds));
            generar_places();
            ContentValues values = new ContentValues();
            values.put(this.CN_TITOL, titol.toUpperCase());
            values.put(this.CN_DESCRIPCIO, descripcio);
            values.put(this.CN_DURADA, durada);
            values.put(this.CN_PREU, preu);
            values.put(this.CN_DATA, dataObra.toString());
            values.put(this.CN_BUTAQUES, places);
            values.put(this.CN_MILIS, String.valueOf(milliseconds));
            values.put(this.CN_PLACES_LLIURES, p);
            values.put(this.CN_COMPRADORS, usuaris);
            values.put(this.CN_DIA_SETMANA, dia_setmana);
            this.newObra(values, this.OBRA_TABLE);
        }
    }

    public void generar_places() {
        usuaris = "^";
        usuari = "usuari@prova.com";
        places = "-";
        p = 0;
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(200);
            if (n % 2 == 0) {
                places = places + "0";
                usuaris = i + usuari + "^" + usuaris;
            } else {
                places = places + "1";
                p++;
            }
        }
    }
}