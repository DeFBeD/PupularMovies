package com.example.android.popularmovies.Activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.android.popularmovies.Adapters.MovieCursorAdapter;
import com.example.android.popularmovies.Data.MovieDataBaseContract;
import com.example.android.popularmovies.R;

public class FavoriteMovies extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NEW_MOVIE_LOADER_ID = 0;

    private MovieCursorAdapter mAdapter;
    RecyclerView rView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle("Favorite Movies");

        rView = findViewById(R.id.fav_rV);
        rView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        rView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MovieCursorAdapter(this);
        rView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(NEW_MOVIE_LOADER_ID, null, this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(NEW_MOVIE_LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor movieDataCursor = null;

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieDataBaseContract.MovieDatabase.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieDataBaseContract.MovieDatabase.COLUMN_MOVIE_TITLE);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onStartLoading() {
                if (movieDataCursor != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(movieDataCursor);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            public void deliverResult(Cursor data) {
                movieDataCursor = data;
                super.deliverResult(data);
            }

        };

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapMovieCursor(data);

        }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapMovieCursor(null);

        }
}



