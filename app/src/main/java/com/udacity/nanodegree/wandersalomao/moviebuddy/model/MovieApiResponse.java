
package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used to process the results of the MovieAPI
 * @author Wander Salomao
 */

public class MovieApiResponse {

    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

}
