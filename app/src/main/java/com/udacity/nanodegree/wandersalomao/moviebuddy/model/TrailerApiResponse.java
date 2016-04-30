package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerApiResponse {

    @SerializedName("results")
    private List<Trailer> results;

    public List<Trailer> getResults() {
        return results;
    }
}
