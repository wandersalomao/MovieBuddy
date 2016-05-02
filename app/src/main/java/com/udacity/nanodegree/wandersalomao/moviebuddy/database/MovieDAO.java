package com.udacity.nanodegree.wandersalomao.moviebuddy.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Data Access Object class used to perform database operations related to the Movie Entity
 * @author Wander Salomao
 */
public class MovieDAO {

    /**
     * Method that checks if given movie is a favorite movie or not
     *
     * @param mActivity - The activity that is calling the method
     * @param movieId - The ID of the movie
     * @return - Returns true if it's a favorite movie or false if not
     */
    public static boolean isFavoriteMovie(Activity mActivity, String movieId) {

        Cursor mCursor = mActivity.getContentResolver().query(
                MoviesContract.CONTENT_URI,
                null,                           // columns
                MoviesContract.ID + " = ? ",    // selection clause
                new String[]{ movieId },        // selection args
                "");                            // sort order

        boolean isFavorite = false;

        if (mCursor != null) {
            mCursor.close();
            isFavorite = mCursor.getCount() >= 1;
        }

        return isFavorite;
    }

    /**
     * Method that queries all the favorite movies from the database
     *
     * @param context - The current context
     * @return - Returns a list of MovieDetails for the favorite movies
     */
    public static List<MovieDetails> getMyFavoriteMovies(Context context) {

        List<MovieDetails> movies = new ArrayList<>();

        // query all the saved movies
        Cursor mCursor = context.getContentResolver().query(
                MoviesContract.CONTENT_URI,
                null,        // columns
                null,        // selection clause
                null,        // selection args
                "");

        if (mCursor != null) {
            try {

                MovieDetails movie;
                while (mCursor.moveToNext()) {

                    // create new movie details
                    movie = new MovieDetails();
                    movie.setId(mCursor.getInt(mCursor.getColumnIndexOrThrow(MoviesContract.ID)));
                    movie.setTitle(mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.TITLE)));
                    movie.setBackdropPath(mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.BACKDROP)));
                    movie.setPosterPath(mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.POSTER)));
                    movies.add(movie);
                }
            } finally {
                mCursor.close();
            }
        }

        return movies;
    }

    /**
     * Method that unmarks the given movie as favorite
     *
     * @param mActivity - The activity that is calling the method
     * @param movieId - The ID of the movie that will be unmarked as favorite
     */
    public static void unsetMovieAsFavorite(Activity mActivity, String movieId) {

        mActivity.getContentResolver().delete(
                MoviesContract.CONTENT_URI,
                MoviesContract.ID + " = ? ",    // selection clause
                new String[]{ movieId });       // selection args
    }

    /**
     * Method that marks the given movie as favorite
     *
     * @param mActivity - The activity that is calling the method
     * @param movieDetails - The movie that will be saved as favorite
     */
    public static void setMovieAsFavorite(Activity mActivity, MovieDetails movieDetails) {

        ContentValues values = new ContentValues();
        values.put(MoviesContract.ID, movieDetails.getId());
        values.put(MoviesContract.TITLE, movieDetails.getTitle());
        values.put(MoviesContract.DATE, movieDetails.getReleaseDate());
        values.put(MoviesContract.STATUS, movieDetails.getStatus());
        values.put(MoviesContract.OVERVIEW, movieDetails.getOverview());
        values.put(MoviesContract.BACKDROP, movieDetails.getBackdropPath());
        values.put(MoviesContract.TAGLINE, movieDetails.getTagLine());
        values.put(MoviesContract.RUNTIME, movieDetails.getRuntime());
        values.put(MoviesContract.POSTER, movieDetails.getPosterPath());

        mActivity.getContentResolver(). insert(
                MoviesContract.CONTENT_URI, values);
    }
}
