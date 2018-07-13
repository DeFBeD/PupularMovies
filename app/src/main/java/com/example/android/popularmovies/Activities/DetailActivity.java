package com.example.android.popularmovies.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Adapters.ReviewAdapter;
import com.example.android.popularmovies.Adapters.TrailerAdapter;
import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.Data.MovieApiService;
import com.example.android.popularmovies.Data.MovieDataBaseContract;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.Trailers;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private final String KEY_RECYCLER_STATE = "recycler_state";

    private Movie movie;
    String id;
    ImageView backdrop;
    ImageView poster;
    TextView rating;
    TextView releaseDate;
    TextView plot;
    FloatingActionButton fab;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private RecyclerView rView;
    private RecyclerView tview;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager trailerManager;
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(movie.getTitle());

        reviewAdapter = new ReviewAdapter(this);
        trailerAdapter = new TrailerAdapter(this);

        rView = findViewById(R.id.review_rc);
        tview = findViewById(R.id.trailer_rc);
        tview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        rView.setHasFixedSize(true);
        tview.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        trailerManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rView.setLayoutManager(mLayoutManager);
        tview.setLayoutManager(trailerManager);

        id = movie.getUniqueId();

        rView.setAdapter(reviewAdapter);
        tview.setAdapter(trailerAdapter);

        rating = findViewById(R.id.user_rating);
        rating.setText(movie.getRating());


        releaseDate = findViewById(R.id.release_date);
        releaseDate.setText(movie.getReleaseDate());

        plot = findViewById(R.id.plot);
        plot.setText(movie.getDescription());

        backdrop = findViewById(R.id.backdrop);
        Picasso.with(this)
                .load(movie.getBackdrop())
                .into(backdrop);

        poster = findViewById(R.id.poster);
        Picasso.with(this)
                .load(movie.getPoster())
                .into(poster);

        /** example of error handling with Picasso
         * Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(imageView);
         **/

        fab = findViewById(R.id.fab);

        if (movieIsFavorite()) {
            fab.setImageResource(R.drawable.ic_favorite);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_off);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (movieIsFavorite()) {

                    removeFromFavorite();

                    Snackbar.make(view, R.string.movie_removed_favorites, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    fab.setImageResource(R.drawable.ic_favorite_off);
                } else {

                    // Insert new task data via a ContentResolver
                    // Create new empty ContentValues object
                    ContentValues movieValues = new ContentValues();
                    // Put the task description and selected mPriority into the ContentValues
                    movieValues.put(MovieDataBaseContract.MovieDatabase.COLUMN_UNIQUE_ID, movie.getUniqueId());
                    movieValues.put(MovieDataBaseContract.MovieDatabase.COLUMN_MOVIE_TITLE, movie.getTitle());
                    movieValues.put(MovieDataBaseContract.MovieDatabase.COLUMN_MOVIE_RATING, movie.getRating());
                    movieValues.put(MovieDataBaseContract.MovieDatabase.COLUMN_MOVIE_RELEASE, movie.getReleaseDate());
                    movieValues.put(MovieDataBaseContract.MovieDatabase.COLUMN_MOVIE_PLOT, movie.getDescription());
                    movieValues.put(MovieDataBaseContract.MovieDatabase.COLUMN_MOVIE_IMAGE, movie.getPoster());

                    // Insert the content values via a ContentResolver
                     getApplicationContext().getContentResolver().insert(MovieDataBaseContract.MovieDatabase.CONTENT_URI, movieValues);

                    Snackbar.make(view, R.string.snackbar_fav_message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    fab.setImageResource(R.drawable.ic_favorite);
                }


            }
        });


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getMovieReviews();
        getMovieTrailer();
    }


    public void removeFromFavorite() {

        String uriMovieStringId = movie.getUniqueId();


        Uri uri = MovieDataBaseContract.MovieDatabase.CONTENT_URI;

        uri = uri.buildUpon().appendPath(uriMovieStringId).build();

        // delete a row by its id with the resolver
        getContentResolver().delete(uri, null, null);
    }

    private boolean movieIsFavorite() {
        Cursor favoriteMovieCursor = getApplicationContext().getContentResolver().query(
                MovieDataBaseContract.MovieDatabase.CONTENT_URI,
                new String[]{MovieDataBaseContract.MovieDatabase.COLUMN_UNIQUE_ID},
                MovieDataBaseContract.MovieDatabase.COLUMN_UNIQUE_ID + " = " + movie.getUniqueId(),
                null,
                null);

        if (favoriteMovieCursor != null && favoriteMovieCursor.moveToFirst()) {
            favoriteMovieCursor.close();
            return true;
        } else {
            return false;
        }
    }


    private void getMovieReviews() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.OPEN_MOVIE_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieApiService service = restAdapter.create(MovieApiService.class);
        service.getMovieReviews(id, new Callback<Reviews.ReviewResult>() {
            @Override
            public void success(Reviews.ReviewResult reviewResults, Response response) {
                reviewAdapter.setReviewsList(reviewResults.getReviewResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }


    private void getMovieTrailer() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.OPEN_MOVIE_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieApiService service = restAdapter.create(MovieApiService.class);
        service.getMovieTrailer(id, new Callback<Trailers.TrailerResult>() {
            @Override
            public void success(Trailers.TrailerResult trailerResults, Response response) {
                trailerAdapter.setTrailersList(trailerResults.getTrailerResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}




