package com.example.android.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.Data.MovieDataBaseContract.MovieDatabase;

public class MovieContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;


    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieDataBaseContract.AUTHORITY, MovieDataBaseContract.PATH_FAVORITE_MOVIES, MOVIES);
        uriMatcher.addURI(MovieDataBaseContract.AUTHORITY, MovieDataBaseContract.PATH_FAVORITE_MOVIES + "/#", MOVIES_WITH_ID);

        return uriMatcher;
    }

    private MoviesDbHelper movieDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        movieDbHelper = new MoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);
        Cursor retCursor;

        //uses a cursor on FavoriteMovies class to query the database <via AsyncTaskLoader>
        switch (match) {

            case MOVIES:
                retCursor = db.query(MovieDatabase.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Uri returnUri;

        //inserts a new Movie from the details fragment FAB button
        switch (match) {
            case MOVIES:

                long id = db.insert(MovieDatabase.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieDatabase.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        int tasksDeleted;
        //delete single movie by its ID
        switch (match) {
            case MOVIES_WITH_ID:

                String id = uri.getPathSegments().get(1);

                //tasksDeleted = db.delete(MovieDatabase.TABLE_NAME, "_id=?", new String[]{id});
                tasksDeleted = db.delete(MovieDatabase.TABLE_NAME, "uniqueId=?", new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //Method not needed for this specific app
        return 0;
    }
}
