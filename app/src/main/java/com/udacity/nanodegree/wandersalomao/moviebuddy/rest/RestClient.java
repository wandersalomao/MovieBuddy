package com.udacity.nanodegree.wandersalomao.moviebuddy.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieApiResponse;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.TrailerApiResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Service used to process rest calls to the movie service
 * @author Wander Salomao
 */
public class RestClient {

    // this class works as a Singleton
    public static RestClient INSTANCE;

    // uses the apiService defined
    private final IMovieServiceAPI apiService;

    private RestClient() {

        // for converting the data we use the Gson library
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        // Create the service
        RestAdapter movieRestAPI = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.MOVIE_DB_HOST)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = movieRestAPI.create(IMovieServiceAPI.class);
    }

    /**
     * Method to return the only instance of this service
     * @return - Instance of the RestClient service
     */
    public static RestClient getInstance() {

        if (INSTANCE == null)
            INSTANCE = new RestClient();

        return INSTANCE;
    }

    /**
     * Method used to get the most popular movies
     *
     * @param callback - the callback function used to process the results
     */
    public void getMovies(Callback<MovieApiResponse> callback) {
        apiService.getPopularMovies(Constants.API_KEY, callback);
    }

    /**
     * Method used to get the details of the given movie
     *
     * @param callback - the callback function used to process the results
     */
    public void getDetailMovie(String id, Callback<MovieDetails> callback) {
        apiService.getMovieDetail(Constants.API_KEY, id, callback);
    }

    /**
     * Method used to get the Trailer details of the given movie
     *
     * @param callback - the callback function used to process the results
     */
    public void getTrailers(String id, Callback<TrailerApiResponse> callback) {
        apiService.getTrailerDetail(Constants.API_KEY, id, callback);
    }

}
