package com.udacity.nanodegree.wandersalomao.moviebuddy.rest;

import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieApiResponse;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.TrailerApiResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Interface representing the IMovieServiceAPI endpoints used by retrofit
 * @author Wander Salomao
 */
public interface IMovieServiceAPI {

    /**
     * Method used to get the most popular movies
     * @param apiKey - the service api key
     * @param callback - the callback function used to process the results
     */
    @GET("/movie/popular")
    void getPopularMovies(
            @Query("api_key") String apiKey,
            Callback<MovieApiResponse> callback);

    /**
     * Method used to get the details of the given movie
     * @param apiKey - the service api key
     * @param id - the movie id
     * @param callback - the callback function used to process the results
     */
    @GET("/movie/{id}")
    void getMovieDetail(
            @Query("api_key") String apiKey,
            @Path("id") String id,
            Callback<MovieDetails> callback);

    /**
     * Method used to get the Trailer details of the given movie
     * @param apiKey - the service api key
     * @param id - the movie id
     * @param callback - the callback function used to process the results
     */
    @GET("/movie/{id}/videos")
    void getTrailerDetail(
            @Query("api_key") String apiKey,
            @Path("id") String id,
            Callback<TrailerApiResponse> callback);

}

