package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    //Variables de generació d'shows automàtiques
    String title, description, users, user, showDate, places, dayOfTheWeek, duration, price;
    int p;
    long milliseconds;

    Calendar calendar = new GregorianCalendar();

    //Versió de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Nom de la base de datos
    public static final String DATABASE_NAME = "database.sqlite";

    //Nom de la taula
    public static final String SHOW_TABLE = "Show";

    public static final String CN_TITLE = "title";
    public static final String CN_DESCRIPTION = "description";
    public static final String CN_DATE = "date";
    public static final String CN_DURATION = "duration";
    public static final String CN_PRICE = "price";
    public static final String CN_SEATS = "seats";
    public static final String CN_FREE_SEATS = "freePlaces";
    public static final String CN_MILIS = "milis";
    public static final String CN_CLIENTS = "clients";
    public static final String CN_DAY_OF_THE_WEEK = "dayOfTheWeek";


    public static final String SHOW_TABLE_CREATE = "CREATE TABLE " + SHOW_TABLE + "( " +
            CN_TITLE + " TEXT, " +
            CN_DESCRIPTION + " TEXT, " +
            CN_DATE + " TEXT, " +
            CN_DURATION + " INTEGER, " +
            CN_PRICE + " INTEGER, " +
            CN_SEATS + " TEXT, " +
            CN_FREE_SEATS + " INTEGER, " +
            CN_MILIS + " TEXT, " +
            CN_CLIENTS + " TEXT, " +
            CN_DAY_OF_THE_WEEK + " TEXT, " +
            "PRIMARY KEY (title,date));";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void newShow(ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(
                tableName,
                null,
                values);
    }

    //Comprovar si l'obra existeix
    public Cursor checkShow(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {title};
        Cursor c = db.query(
                SHOW_TABLE,  // The table to query
                columns,                                    // The columns to return
                "title=?",                                   // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    //Obtenir una sessió en concret
    public Cursor getShow(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {title, date};
        Cursor c = db.query(
                SHOW_TABLE,  // The table to query
                columns,                                    // The columns to return
                "title=?" + " and " + "date=?",                                   // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    //Obtenir totes les shows amb repetició
    public Cursor getAllShows() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        //Cursor c = db.rawQuery( "SELECT DISTINCT name FROM Show", null);
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_TITLE + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir totes les shows ordenades pel name, sense repetir
    public Cursor getAllShowsDistinct() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {CN_TITLE};
        Cursor c = db.query(true,
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                CN_TITLE,             // don't group the rows
                null,               // don't filter by row groups
                CN_TITLE + " ASC",    // The sort order
                null
        );
        return c;
    }

    //Obtenir totes les dates d'una obra
    public Cursor getDatesShow(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {title};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                "title=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir totes les dates d'una obra
    public Cursor getDatesShowDesc(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {title};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                "title=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " DESC"                // The sort order
        );
        return c;
    }

    //Obtenir una funció d'una obra a una date determinada
    public Cursor getShowDate(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {title, date};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                "title=?" + " and " + "date=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_DATE + " ASC"                // The sort order
        );
        return c;
    }

    //Compta quantes sessions hi ha programades per una obra
    public int sessionsCounter(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] where = {title};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                null,            // The columns to return
                "title=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        int cnt = c.getCount();
        return cnt;
    }

    //Retorna un array amb tots els users per separat d'una sessió
    public String[] consultUsers(String title, String date) {
        Cursor c = this.getUsers(title, date);
        String[] users;
        String s_users = new String();
        if (c.moveToFirst()) {
            s_users = c.getString(c.getColumnIndex(CN_CLIENTS));
        }
        if (s_users != null) {
            users = s_users.split("\\^");
            return users;
        }
        return null;
    }

    //Obtenir els users d'una obra a una date determinada
    public Cursor getUsers(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {title, date};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                "title=?" + " and " + "date=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
        return c;
    }

    //Obtenir totes les dates d'una obra
    public Cursor getDay(String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {day};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                "dayOfTheWeek=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " ASC"                // The sort order
        );
        return c;
    }

    //Obtenir totes les dates d'una obra
    /*public Cursor getDies(String day, String dia2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITLE, CN_DESCRIPTION, CN_DATE, CN_DURATION, CN_PRICE, CN_SEATS,
                CN_FREE_SEATS, CN_MILIS, CN_CLIENTS, CN_DAY_OF_THE_WEEK};
        String[] where = {day, dia2};
        Cursor c = db.query(
                SHOW_TABLE,          // The table to query
                columns,            // The columns to return
                "dayOfTheWeek=?" + " or " + "dayOfTheWeek=?",               // The columns for the WHERE clause
                where,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                CN_MILIS + " ASC"                // The sort order
        );
        return c;
    }*/

    //Obtenir les tickets venudes en un mateix day de la setmana (dilluns, dimarts...)
    public int getEntries(String day) {
        Cursor c = this.getDay(day);
        //Cursor c = this.getDies(day, dia2);
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                int free = c.getInt(c.getColumnIndex(CN_FREE_SEATS));
                int aux = 40 - free;
                cont += aux;
            } while (c.moveToNext());
        }
        return cont;
    }

    //Obtenir les tickets venudes en un mateix day de la setmana (dilluns, dimarts...)
    /*public int getEntries(String day, String dia2) {
        Cursor c = this.getDies(day, dia2);
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                int lliures = c.getInt(c.getColumnIndex(CN_FREE_SEATS));
                int aux = 40 - lliures;
                cont += aux;
            } while (c.moveToNext());
        }
        return cont;
    }*/

    //Obtenir el total d'tickets venudes en tot el teatre
    public int getTotalEntries() {
        Cursor c = this.getAllShows();
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                int free = c.getInt(c.getColumnIndex(CN_FREE_SEATS));
                int aux = 40 - free;
                cont += aux;
            } while (c.moveToNext());
        }
        return cont;
    }

    //Obtenir el total de la recaptació de tot el teatre
    public int getTotalSells() {
        Cursor c = this.getAllShows();
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                //if (c.getInt(c.getColumnIndex(CN_FREE_SEATS)) != 0) {
                    int free = c.getInt(c.getColumnIndex(CN_FREE_SEATS));
                    int aux = 40 - free;
                    cont += aux * c.getInt(c.getColumnIndex(CN_PRICE));
                //}
            } while (c.moveToNext());
        }
        return cont;
    }

    //Obtenir la recaptació d'un mateix day de la setmana (dilluns, dimarts...)
    public int getIncome(String day) {
        Cursor c = this.getDay(day);
        //Cursor c = this.getDies(day, dia2);
        int cont = 0;
        if (c.moveToFirst()) {
            do {
                //if (c.getInt(c.getColumnIndex(CN_FREE_SEATS)) != 0) {
                    int free = c.getInt(c.getColumnIndex(CN_FREE_SEATS));
                    int aux = 40 - free;
                    cont += aux * c.getInt(c.getColumnIndex(CN_PRICE));
                //}
            } while (c.moveToNext());
        }
        return cont;
    }

    //Actualitzar seats ocupades
    public void updatePlaces(String title, String date, String occupation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_SEATS, occupation);
        db.update(SHOW_TABLE, values, CN_TITLE + "=?" + " and " + "date=?", new String[]{title, date});
    }

    //Actualitzar places lliures
    public void updateFreePlaces(String title, String date, int places) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_FREE_SEATS, places);
        db.update(SHOW_TABLE, values, CN_TITLE + "=?" + " and " + "date=?", new String[]{title, date});
    }

    //Actualitzar llista de compradors
    public void updateClients(String title, String date, String clients) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CN_CLIENTS, clients);
        db.update(SHOW_TABLE, values, CN_TITLE + "=?" + " and " + "date=?", new String[]{title, date});
    }

    //Eliminar una obra amb totes les seves sessions
    public void deteleShow(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SHOW_TABLE, CN_TITLE + "=?", new String[]{title});
    }

    //Eliminar una funció d'una obra
    public void deteleSession(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SHOW_TABLE, CN_TITLE + "=?" + " and " + "date=?", new String[]{title, date});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SHOW_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Esborrar BD
    public void resetAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SHOW_TABLE, null, null);
    }

    ////////////////Inicialitzem la BD amb 3 shows///////////////////////////////
    public void initData() {
        //Eliminem la BD actual i la tornem a generar
        this.resetAll();
        ///////////////////////////////Show 1//////////////////////////////////
        title = "El rey leon";
        description = "Gracias al genio, visión artística y creativa " +
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
        duration = "120";
        price = "50";
        generate_places();
        daysCalculator(1, 30);

        /////////////////////////////////Obra2//////////////////////////////////
        title = "Mamma mia";
        description = "En una idíl•lica illa grega days abans de " +
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
        duration = "90";
        price = "20";
        //generate_places();
        daysCalculator(1, 30);

        ///////////////////////////////////Obra3///////////////////////////////////////////
        title = "Queen";
        description = "La formación original con Brian May y Roger " +
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
        duration = "110";
        price = "40";
        //generate_places();
        daysCalculator(1, 30);
    }

    public void daysCalculator(int day_from_val, int day_to_val) {


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

        int month = calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)+1;

        //formatter = new SimpleDateFormat("YY");

        int year = calendar.get(Calendar.YEAR);

        /*String days[] = new String[7];

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 7; ++i) {
            days[i] = formatter.format(calendar.getTime());
            calendar.add(calendar.DAY_OF_WEEK, 1);
        }*/

        for (int i = day_from_val; i <= day_to_val; ++i) {
            showDate = i + "/" + month + "/" + year;
            places = "-";
            dayOfTheWeek = "";
            for (int j = 1; j < 41; ++j) {
                //Plaça lliure indicat amb un 1
                places = places + "1";
            }
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
            java.util.Date d = null;
            try {
                d = f.parse(showDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            milliseconds = d.getTime();
            formatter = new SimpleDateFormat("c");
            dayOfTheWeek = formatter.format(new java.sql.Date(milliseconds));
            generate_places();
            Random rand = new Random();
            int n = rand.nextInt(500);
            if (n % 2 == 0) {
                ContentValues values = new ContentValues();
                values.put(this.CN_TITLE, title.toUpperCase());
                values.put(this.CN_DESCRIPTION, description);
                values.put(this.CN_DURATION, duration);
                values.put(this.CN_PRICE, price);
                values.put(this.CN_DATE, showDate.toString());
                values.put(this.CN_SEATS, places);
                values.put(this.CN_MILIS, String.valueOf(milliseconds));
                values.put(this.CN_FREE_SEATS, p);
                values.put(this.CN_CLIENTS, users);
                values.put(this.CN_DAY_OF_THE_WEEK, dayOfTheWeek);
                this.newShow(values, this.SHOW_TABLE);

            }
        }
    }

    public void generate_places() {
        users = "^";
        user = "user@test.com";
        places = "-";
        p = 0;
        for (int i = 1; i < 41; ++i) {
            Random rand = new Random();
            int n = rand.nextInt(500);
            if (n % 3 == 0) {
                places = places + "0";
                users = i + user + "^" + users;
            } else {
                places = places + "1";
                p++;
            }
        }
    }
}