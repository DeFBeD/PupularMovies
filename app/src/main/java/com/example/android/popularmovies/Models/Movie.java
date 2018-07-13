package com.example.android.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {

    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private String uniqueId;

    public Movie() {
    }

    protected Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
        uniqueId = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return "http://image.tmdb.org/t/p/w500" + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);
        parcel.writeString(rating);
        parcel.writeString(releaseDate);
        parcel.writeString(uniqueId);
    }

    public static class MovieResult {
        private List<Movie> results;


        public List<Movie> getResults() {
            return results;
        }
    }
}
