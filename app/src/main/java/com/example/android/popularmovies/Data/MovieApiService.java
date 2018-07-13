package com.example.android.popularmovies.Data;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.Models.Trailers;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/{uniqueId}/reviews")
    void getMovieReviews(@Path("uniqueId") String uniqueId, Callback<Reviews.ReviewResult> cb);

    @GET("/movie/{uniqueId}/videos")
    void getMovieTrailer(@Path("uniqueId") String uniqueId, Callback<Trailers.TrailerResult> cb);
}
