package com.example.android.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews implements Parcelable {

    @SerializedName("content")
    private String reviewContent;
    @SerializedName("author")
    private String reviewAuthor;
    @SerializedName("id")
    private String uniqueId;

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        reviewContent = in.readString();
        reviewAuthor = in.readString();
        uniqueId = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel rPar, int r) {
        rPar.writeString(reviewContent);
        rPar.writeString(reviewAuthor);
        rPar.writeString(uniqueId);
    }

    public static class ReviewResult {
        private List<Reviews> results;


        public List<Reviews> getReviewResults() {
            return results;
        }
    }
}
