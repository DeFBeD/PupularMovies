package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.Data.MovieDataBaseContract.MovieDatabase;

public class MoviesDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "favoriteMovies.db";

    //the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE " + MovieDatabase.TABLE_NAME +"(" +
                MovieDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieDatabase.COLUMN_UNIQUE_ID + " TEXT NOT NULL," +
                MovieDatabase.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MovieDatabase.COLUMN_MOVIE_RELEASE + " TEXT NOT NULL," +
                MovieDatabase.COLUMN_MOVIE_RATING + " TEXT NOT NULL," +
                MovieDatabase.COLUMN_MOVIE_PLOT + " TEXT NOT NULL," +
                MovieDatabase.COLUMN_MOVIE_IMAGE + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDatabase.TABLE_NAME);
        onCreate(db);

    }
}
