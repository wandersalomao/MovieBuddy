package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used to process the results of the MovieAPI
 * @author Wander Salomao
 */
public class TrailerApiResponse {

    @SerializedName("results")
    private List<Trailer> results;

    public List<Trailer> getResults() {
        return results;
    }
}
