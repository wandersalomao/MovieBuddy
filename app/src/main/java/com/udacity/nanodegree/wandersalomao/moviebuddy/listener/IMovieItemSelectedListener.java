package com.udacity.nanodegree.wandersalomao.moviebuddy.listener;

/**
 * Interface created to delegate the action when the user clicks on a movie. The Activities will
 * implement this interface when they need to open the movie details
 *
 * @author Wander Salomao
 */
public interface IMovieItemSelectedListener {

    /**
     * Method called when the user selects a movie.
     *
     * @param movieId - The ID of the selected movie
     */
    void onMovieSelected(String movieId);
}
