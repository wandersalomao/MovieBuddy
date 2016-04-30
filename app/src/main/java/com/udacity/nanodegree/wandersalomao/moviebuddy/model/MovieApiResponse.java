
package com.udacity.nanodegree.wandersalomao.moviebuddy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {

    @SerializedName("page")
    private Number page;

    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("total_pages")
    private Number totalPages;

    @SerializedName("total_results")
    private Number totalResults;

    public Number getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public Number getTotalPages() {
        return totalPages;
    }

    public Number getTotalResults() {
        return totalResults;
    }
}
