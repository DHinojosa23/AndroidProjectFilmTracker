package com.example.myfilmsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.myfilmsapp.Film;
import com.example.myfilmsapp.data.FilmContract.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FilmDBHelper extends SQLiteOpenHelper {


    /* Nombre de la BBDD*/
    private static final String DATABASE_NAME = "films.db";

    /*Actualizacion de la BDD, si cambiamos el esquema tendremos que cambiar este numero*/
    private static final int DATABASE_VERSION = 1;


    public FilmDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*Creación de la base de datos y tabla films*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_FILMS_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME +
                "("
                + FilmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FilmEntry.COLUMN_FILM_TITLE + " TEXT NOT NULL, "
                + FilmEntry.COLUMN_FILM_YEAR + " INTEGER , "
                + FilmEntry.COLUMN_FILM_COUNTRY + " TEXT , "
                + FilmEntry.COLUMN_FILM_DIRECTOR + " TEXT , "
                + FilmEntry.COLUMN_FILM_GENRE + " TEXT , "
                + FilmEntry.COLUMN_FILM_RATING + " FLOAT , "
                + FilmEntry.COLUMN_FILM_DESCRIPTION + " TEXT , "
                + FilmEntry.COLUMN_FILM_DATEWATCHED + " TEXT , "
                + FilmEntry.COLUMN_FILM_TYPE + " TEXT  ,"
                + FilmEntry.COLUMN_FILM_WATCHED + " INTEGER , "
                + FilmEntry.COLUMN_FILM_PENDING + " INTEGER ,"
                + FilmEntry.COLUMN_FILM_TRAILER+ " TEXT  "
                + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_FILMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //inserta nueva tupla vista
    public long insertFilmWatched(Film film) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(FilmEntry.COLUMN_FILM_TITLE, film.getTitle());
        contentvalues.put(FilmEntry.COLUMN_FILM_YEAR, film.getYear());
        contentvalues.put(FilmEntry.COLUMN_FILM_COUNTRY, film.getCountry());
        contentvalues.put(FilmEntry.COLUMN_FILM_DIRECTOR, film.getDirector());
        contentvalues.put(FilmEntry.COLUMN_FILM_GENRE, film.getGenre());
        contentvalues.put(FilmEntry.COLUMN_FILM_RATING, film.getRating());
        contentvalues.put(FilmEntry.COLUMN_FILM_DESCRIPTION, film.getDescription());
        contentvalues.put(FilmEntry.COLUMN_FILM_DATEWATCHED, getDateTime());
        contentvalues.put(FilmEntry.COLUMN_FILM_TYPE, "");
        contentvalues.put(FilmEntry.COLUMN_FILM_WATCHED, 1);
        contentvalues.put(FilmEntry.COLUMN_FILM_PENDING, 0);

        long newRowId = db.insert(FilmEntry.TABLE_NAME, null, contentvalues);

        return newRowId;
    }

    //inserta nueva tupla pendiente
    public long insertFilmPending(Film film) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(FilmEntry.COLUMN_FILM_TITLE, film.getTitle());
        contentvalues.put(FilmEntry.COLUMN_FILM_YEAR, film.getYear());
        contentvalues.put(FilmEntry.COLUMN_FILM_COUNTRY, film.getCountry());
        contentvalues.put(FilmEntry.COLUMN_FILM_DIRECTOR, film.getDirector());
        contentvalues.put(FilmEntry.COLUMN_FILM_GENRE, film.getGenre());
        contentvalues.put(FilmEntry.COLUMN_FILM_RATING, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_DESCRIPTION, film.getDescription());
        contentvalues.put(FilmEntry.COLUMN_FILM_TYPE, "");
        contentvalues.put(FilmEntry.COLUMN_FILM_WATCHED, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_PENDING, 1);

        long newRowId = db.insert(FilmEntry.TABLE_NAME, null, contentvalues);

        return newRowId;
    }

    //inserta nueva tupla estática
    public long insertFilmStatic(Film film) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(FilmEntry.COLUMN_FILM_TITLE, film.getTitle());
        contentvalues.put(FilmEntry.COLUMN_FILM_YEAR, film.getYear());
        contentvalues.put(FilmEntry.COLUMN_FILM_COUNTRY, film.getCountry());
        contentvalues.put(FilmEntry.COLUMN_FILM_DIRECTOR, film.getDirector());
        contentvalues.put(FilmEntry.COLUMN_FILM_GENRE, film.getGenre());
        contentvalues.put(FilmEntry.COLUMN_FILM_RATING, film.getRating());
        contentvalues.put(FilmEntry.COLUMN_FILM_DESCRIPTION, film.getDescription());
        contentvalues.put(FilmEntry.COLUMN_FILM_TYPE, film.getType());
        contentvalues.put(FilmEntry.COLUMN_FILM_WATCHED, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_PENDING, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_TRAILER,film.getYoutubeUrl());

        long newRowId = db.insert(FilmEntry.TABLE_NAME, null, contentvalues);

        return newRowId;
    }

    public int watched(int id) {
        int watched = 0;
        String POSTS_SELECT_QUERY =
                String.format("SELECT watched FROM " + FilmEntry.TABLE_NAME + "WHERE id = " + id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                watched = (Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));

            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return watched;


    }

    public long updateFilmRating(int id, float rating) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();

        contentvalues.put(FilmEntry.COLUMN_FILM_RATING, rating);

        long newRowId = db.update(FilmEntry.TABLE_NAME, contentvalues, "_ID = ?", new String[]{Integer.toString(id)});

        return newRowId;
    }


    public long updateFilmToWatched(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();

        contentvalues.put(FilmEntry.COLUMN_FILM_WATCHED, 1);
        contentvalues.put(FilmEntry.COLUMN_FILM_PENDING, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_DATEWATCHED, getDateTime());


        long newRowId = db.update(FilmEntry.TABLE_NAME, contentvalues, "_ID = ?", new String[]{Integer.toString(id)});

        return newRowId;
    }

    public long updateFilmToPending(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();

        contentvalues.put(FilmEntry.COLUMN_FILM_WATCHED, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_PENDING, 1);
        contentvalues.put(FilmEntry.COLUMN_FILM_DATEWATCHED, "");

        long newRowId = db.update(FilmEntry.TABLE_NAME, contentvalues, "_ID = ?", new String[]{Integer.toString(id)});

        return newRowId;
    }

    public long updateFilmToNotPendingNotWatched(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();

        contentvalues.put(FilmEntry.COLUMN_FILM_WATCHED, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_PENDING, 0);
        contentvalues.put(FilmEntry.COLUMN_FILM_DATEWATCHED, "");

        long newRowId = db.update(FilmEntry.TABLE_NAME, contentvalues, "_ID = ?", new String[]{Integer.toString(id)});

        return newRowId;
    }

    public long updateFilm(Film film, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(FilmEntry.COLUMN_FILM_TITLE, film.getTitle());
        contentvalues.put(FilmEntry.COLUMN_FILM_YEAR, film.getYear());
        contentvalues.put(FilmEntry.COLUMN_FILM_COUNTRY, film.getCountry());
        contentvalues.put(FilmEntry.COLUMN_FILM_DIRECTOR, film.getDirector());
        contentvalues.put(FilmEntry.COLUMN_FILM_GENRE, film.getGenre());
        contentvalues.put(FilmEntry.COLUMN_FILM_RATING, film.getRating());
        contentvalues.put(FilmEntry.COLUMN_FILM_DESCRIPTION, film.getDescription());

        long newRowId = db.update(FilmEntry.TABLE_NAME, contentvalues, "_ID = ?", new String[]{Integer.toString(id)});

        return newRowId;
    }

    //devuelve número de tuplas en nuestra tabla pets
    public int TableRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + FilmEntry.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount;
    }

    //obtener listado con todas las peliculas método útil para un futuro de la pelicula en el que añadir un boton con todas las peliculas, vistas, recomendadas y pendientes
    public List<Film> getFilms() {
        List<Film> filmList = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM " + FilmEntry.TABLE_NAME);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry._ID))));
                film.setTitle(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TITLE)));
                film.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_YEAR))));
                film.setCountry(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_COUNTRY)));
                film.setDirector(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DIRECTOR)));
                film.setGenre(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_GENRE)));
                film.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_RATING))));
                film.setDescription(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DESCRIPTION)));
                film.setType(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TYPE)));
                film.setWatched(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));
                film.setPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_PENDING))));

                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return filmList;
    }

    //obtener listado con las peliculas vistas
    public List<Film> getFilmsWatched() {
        List<Film> filmList = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM " + FilmEntry.TABLE_NAME + " WHERE " + FilmEntry.COLUMN_FILM_WATCHED + " = 1 ORDER BY DATEWATCHED DESC");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry._ID))));
                film.setTitle(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TITLE)));
                film.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_YEAR))));
                film.setCountry(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_COUNTRY)));
                film.setDirector(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DIRECTOR)));
                film.setGenre(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_GENRE)));
                film.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_RATING))));
                film.setDescription(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DESCRIPTION)));
                film.setDateWatched(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DATEWATCHED)));
                film.setType(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TYPE)));
                film.setWatched(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));
                film.setPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_PENDING))));

                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return filmList;
    }

    //obtener listado con las peliculas pendientes
    public List<Film> getFilmsPending() {
        List<Film> filmList = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM " + FilmEntry.TABLE_NAME + " WHERE " + FilmEntry.COLUMN_FILM_PENDING + " = 1 ORDER BY _ID DESC");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry._ID))));
                film.setTitle(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TITLE)));
                film.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_YEAR))));
                film.setCountry(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_COUNTRY)));
                film.setDirector(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DIRECTOR)));
                film.setGenre(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_GENRE)));
                film.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_RATING))));
                film.setDescription(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DESCRIPTION)));
                film.setDateWatched(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DATEWATCHED)));
                film.setType(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TYPE)));
                film.setWatched(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));
                film.setPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_PENDING))));

                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return filmList;
    }

    //obtener listado con las peliculas recomendadas
    public List<Film> getFilmsRecommended() {
        List<Film> filmList = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM " + FilmEntry.TABLE_NAME + " WHERE " + FilmEntry.COLUMN_FILM_TYPE + " = 'recommended'");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry._ID))));
                film.setTitle(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TITLE)));
                film.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_YEAR))));
                film.setCountry(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_COUNTRY)));
                film.setDirector(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DIRECTOR)));
                film.setGenre(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_GENRE)));
                film.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_RATING))));
                film.setDescription(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DESCRIPTION)));
                film.setDateWatched(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DATEWATCHED)));
                film.setType(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TYPE)));
                film.setWatched(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));
                film.setPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_PENDING))));
                film.setYoutubeUrl(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TRAILER)));

                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return filmList;
    }

    //obtener listado con todas las peliculas con oscar
    public List<Film> getFilmsOscar() {
        List<Film> filmList = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM " + FilmEntry.TABLE_NAME + " WHERE " + FilmEntry.COLUMN_FILM_TYPE + " = 'oscar'");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry._ID))));
                film.setTitle(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TITLE)));
                film.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_YEAR))));
                film.setCountry(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_COUNTRY)));
                film.setDirector(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DIRECTOR)));
                film.setGenre(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_GENRE)));
                film.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_RATING))));
                film.setDescription(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DESCRIPTION)));
                film.setDateWatched(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DATEWATCHED)));
                film.setType(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TYPE)));
                film.setWatched(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));
                film.setPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_PENDING))));
                film.setYoutubeUrl(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TRAILER)));

                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return filmList;
    }

    //obtener listado con todas las peliculas de culto
    public List<Film> getFilmsCult() {
        List<Film> filmList = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM " + FilmEntry.TABLE_NAME + " WHERE " + FilmEntry.COLUMN_FILM_TYPE + " = 'cult'");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry._ID))));
                film.setTitle(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TITLE)));
                film.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_YEAR))));
                film.setCountry(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_COUNTRY)));
                film.setDirector(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DIRECTOR)));
                film.setGenre(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_GENRE)));
                film.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_RATING))));
                film.setDescription(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DESCRIPTION)));
                film.setDateWatched(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_DATEWATCHED)));
                film.setType(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TYPE)));
                film.setWatched(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_WATCHED))));
                film.setPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_PENDING))));
                film.setYoutubeUrl(cursor.getString(cursor.getColumnIndex(FilmEntry.COLUMN_FILM_TRAILER)));

                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return filmList;
    }


    public Integer deleteData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FilmEntry.TABLE_NAME, "_ID = ?", new String[]{Integer.toString(id)});

    }

    //get current date in String format
    private String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
