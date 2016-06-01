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
    public static final String CN_DIA_SETMANA = "dia_setmana";


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
            CN_DIA_SETMANA + " TEXT, " +
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
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
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
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
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
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
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
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
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
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
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
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
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

    //Obtenir una funció d'una obra a una data determinada
    public Cursor getObresDiaSetmana(String nom, String dia) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NOM,CN_DESCRIPCIO,CN_DATA,CN_DURADA,CN_PREU,CN_BUTAQUES,
                CN_PLACES_LLIURES, CN_MILIS, CN_COMPRADORS, CN_DIA_SETMANA};
        String[] where = {nom, dia};
        Cursor c = db.query(
                OBRA_TABLE,          // The table to query
                columns,            // The columns to return
                "nom=?" + " and " + "dia_setmana=?",               // The columns for the WHERE clause
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
        String dataObra = "03/05/16";
        String places = "-";
        String dia_setmana = "";
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

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
        java.util.Date d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("c");
        dia_setmana = formatter.format(new Date(milliseconds));
        ContentValues values = new ContentValues();
        values.put(this.CN_NOM, "El rey leon");
        values.put(this.CN_DESCRIPCIO, "Gracias al genio, visión artística y creativa " +
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
                "al respeto y al amor por la naturaleza.");
        values.put(this.CN_DURADA, String.valueOf(120));
        values.put(this.CN_PREU, String.valueOf(60));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_COMPRADORS, usuaris);
        values.put(this.CN_DIA_SETMANA, dia_setmana);

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
        dataObra = "03/05/16";
        d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();
        dia_setmana = formatter.format(new Date(milliseconds));
        values = new ContentValues();
        values.put(this.CN_NOM, "Mamma Mia");
        values.put(this.CN_DESCRIPCIO, "En una idíl•lica illa grega dies abans de " +
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
                "Et resistiràs?");
        values.put(this.CN_DURADA, String.valueOf(90));
        values.put(this.CN_PREU, String.valueOf(45));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_COMPRADORS, usuaris);
        values.put(this.CN_DIA_SETMANA, dia_setmana);


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
        dataObra = "05/05/16";
        f = new SimpleDateFormat("dd/MM/yy");
        d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();
        dia_setmana = formatter.format(new Date(milliseconds));
        values = new ContentValues();
        values.put(this.CN_NOM, "Queen");
        values.put(this.CN_DESCRIPCIO, "La formación original con Brian May y Roger " +
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
                "Adam Lambert. ");
        values.put(this.CN_DURADA, String.valueOf(120));
        values.put(this.CN_PREU, String.valueOf(60));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_COMPRADORS, usuaris);
        values.put(this.CN_DIA_SETMANA, dia_setmana);


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
        dataObra = "03/05/16";
        f = new SimpleDateFormat("dd/MM/yy");
        d = null;
        try {
            d = f.parse(dataObra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        milliseconds = d.getTime();
        dia_setmana = formatter.format(new Date(milliseconds));
        values = new ContentValues();
        values.put(this.CN_NOM, "Queen");
        values.put(this.CN_DESCRIPCIO, "La formación original con Brian May y Roger " +
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
                "Adam Lambert. ");
        values.put(this.CN_DURADA, String.valueOf(120));
        values.put(this.CN_PREU, String.valueOf(60));
        values.put(this.CN_DATA, dataObra);
        values.put(this.CN_BUTAQUES, places.toString());
        values.put(this.CN_MILIS, milliseconds);
        values.put(this.CN_PLACES_LLIURES, p);
        values.put(this.CN_COMPRADORS, usuaris);
        values.put(this.CN_DIA_SETMANA, dia_setmana);


        this.newObra(values, this.OBRA_TABLE);
    }
}