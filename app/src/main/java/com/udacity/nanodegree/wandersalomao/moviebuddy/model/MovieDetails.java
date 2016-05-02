package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;

import org.parceler.Parcel;

/**
 * Class that represents the MovieDetails.
 * @author Wander Salomao
 */
@Parcel
public class MovieDetails {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("genres")
    private MovieGenre[] genres;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("status")
    private String status;

    @SerializedName("overview")
    private String overview;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("tagline")
    private String tagLine;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("poster_path")
    private String posterPath;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getPosterPath() {
        return Constants.POSTER_BASE_URL + posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
