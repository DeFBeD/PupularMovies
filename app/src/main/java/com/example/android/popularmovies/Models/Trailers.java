package com.example.android.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers implements Parcelable {

    @SerializedName("key")
    private String trailerKey;
    @SerializedName("name")
    private String trailerName;
    @SerializedName("id")
    private String uniqueId;

    public Trailers() {
    }

    protected Trailers(Parcel in) {
        trailerKey = in.readString();
        trailerName = in.readString();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel tPar, int t) {
        tPar.writeString(trailerKey);
        tPar.writeString(trailerName);
        tPar.writeString(uniqueId);
    }

    public static class TrailerResult {
        private List<Trailers> results;


        public List<Trailers> getTrailerResults() {
            return results;
        }
    }
}
