package com.example.myfilmsapp.data;


import android.provider.BaseColumns;

public class FilmContract {

    private FilmContract() {

    }

    public static final class FilmEntry implements BaseColumns {

        public static final String TABLE_NAME = "films";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FILM_TITLE = "title";
        public static final String COLUMN_FILM_YEAR = "year";
        public static final String COLUMN_FILM_COUNTRY = "country";
        public static final String COLUMN_FILM_DIRECTOR = "director";
        public static final String COLUMN_FILM_GENRE = "genre";
        public static final String COLUMN_FILM_RATING = "rating";
        public static final String COLUMN_FILM_DESCRIPTION = "description";
        public static final String COLUMN_FILM_DATEWATCHED = "datewatched";
        public static final String COLUMN_FILM_TYPE = "type";
        public static final String COLUMN_FILM_WATCHED = "watched";
        public static final String COLUMN_FILM_PENDING = "pending";
        public static final String COLUMN_FILM_TRAILER = "trailer";

    }


}
