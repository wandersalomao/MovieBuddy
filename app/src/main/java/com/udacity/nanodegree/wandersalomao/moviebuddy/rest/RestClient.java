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
 * @author Wander Salomao
 */
public class RestClient {

    public static RestClient INSTANCE;
    private final IMovieServiceAPI apiService;

    private RestClient() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter movieRestAPI = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.MOVIE_DB_HOST)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = movieRestAPI.create(IMovieServiceAPI.class);
    }

    public static RestClient getInstance() {

        if (INSTANCE == null)
            INSTANCE = new RestClient();

        return INSTANCE;
    }

    public void getMovies(Callback<MovieApiResponse> callback) {

        apiService.getPopularMovies(Constants.API_KEY, callback);
    }

    public void getDetailMovie(String id, Callback<MovieDetails> callback) {
        apiService.getMovieDetail(Constants.API_KEY, id, callback);
    }

    public void getTrailers(String id, Callback<TrailerApiResponse> callback) {
        apiService.getTrailerDetail(Constants.API_KEY, id, callback);
    }

}
