package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class MovieDetails {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("genres")
    private String[] genres;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("status")
    private String status;

    @SerializedName("overview")
    private String overview;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_count")
    private String voteCount;

    @SerializedName("tagline")
    private String tagLine;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("poster_path")
    private String posterPath;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String[] getGenres() {
        return genres;
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

    public String getVoteCount() {
        return voteCount;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
