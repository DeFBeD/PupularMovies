package com.example.android.popularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieDataBaseContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_FAVORITE_MOVIES = "favoriteMovies";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class MovieDatabase implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

        // Task table and column names
        public static final String TABLE_NAME = "favoriteMovies";

        public static final String COLUMN_UNIQUE_ID = "uniqueId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_RELEASE = "movieRelease";
        public static final String COLUMN_MOVIE_RATING = "movieRating";
        public static final String COLUMN_MOVIE_PLOT = "moviePlot";
        public static final String COLUMN_MOVIE_IMAGE = "movieImage";

    }

}
