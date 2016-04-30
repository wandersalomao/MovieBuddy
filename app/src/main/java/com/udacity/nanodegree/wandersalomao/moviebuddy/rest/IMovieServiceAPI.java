package com.udacity.nanodegree.wandersalomao.moviebuddy.rest;

import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieApiResponse;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.TrailerApiResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Interface representing the IMovieServiceAPI endpoints
 * used by retrofit
 */
public interface IMovieServiceAPI {

    @GET("/movie/popular")
    void getPopularMovies(
            @Query("api_key") String apiKey,
            Callback<MovieApiResponse> callback);

    @GET("/movie/{id}")
    void getMovieDetail(
            @Query("api_key") String apiKey,
            @Path("id") String id,
            Callback<MovieDetails> callback);

    @GET("/movie/{id}/videos")
    void getTrailerDetail(
            @Query("api_key") String apiKey,
            @Path("id") String id,
            Callback<TrailerApiResponse> callback);

}

