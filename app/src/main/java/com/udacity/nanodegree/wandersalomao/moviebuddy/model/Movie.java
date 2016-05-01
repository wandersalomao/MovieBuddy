package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;

import org.parceler.Parcel;

@Parcel
public class Movie {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String date;

    @SerializedName("overview")
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return Constants.POSTER_BASE_URL + posterPath;
    }

    public String getDate() {
        return date;
    }

    public String getOverview() {
        return overview;
    }

    public String getId() {
        return id;
    }
}
