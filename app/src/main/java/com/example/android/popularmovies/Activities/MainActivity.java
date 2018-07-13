package com.example.android.popularmovies.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.Adapters.MoviesAdapter;
import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Data.MovieApiService;
import com.example.android.popularmovies.R;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int SETTINGS_INTENT_REPLY = 1;

    private RecyclerView rView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.rView);

        rView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new MoviesAdapter(this);

        rView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getApplication(), SettingsActivity.class);
            startActivityForResult(settingsIntent, SETTINGS_INTENT_REPLY);
            return true;
        }

        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getApplication(), SettingsActivity.class);
                startActivityForResult(settingsIntent, SETTINGS_INTENT_REPLY);
                return true;
            case R.id.action_fav_movies:
                Intent favMovieIntent = new Intent(getApplication(), FavoriteMovies.class);
                startActivity(favMovieIntent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    ///////Getting intent from settings and launching a query
    @Override
    public void onActivityResult(int settingsRequestCode, int settingsResultcode, Intent resultData) {
        super.onActivityResult(settingsRequestCode, settingsResultcode, resultData);

        if (settingsRequestCode == SETTINGS_INTENT_REPLY) {
            ChooseApi();
        }
    }


    //Helper method to choose which URL to query & check network///////////////////////
    private void ChooseApi() {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {


            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getApplication());
            String orderType = sharedPrefs.getString(
                    getString(R.string.pref_order_key),
                    getString(R.string.pref_most_popular));

            if (orderType.equals(getString(R.string.pref_top_rated))) {
                getTopRatedMovies();
            } else {
                getPopularMovies();
            }
        } else {

            Toast.makeText(getApplicationContext(), R.string.toast_network_error, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        ChooseApi();
    }

    ///////Query Most popular ////////////////////
    private void getPopularMovies() {
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
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    /////////query Top rated//////////////////
    private void getTopRatedMovies() {
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
        service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }



}
