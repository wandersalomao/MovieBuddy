package com.udacity.nanodegree.wandersalomao.moviebuddy.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

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

    public static List<MovieDetails> getMyFavoriteMovies(Context context) {

        List<MovieDetails> movies = new ArrayList<>();

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

    public static int unsetMovieAsFavorite(Activity mActivity, String movieId) {

        return mActivity.getContentResolver().delete(
                MoviesContract.CONTENT_URI,
                MoviesContract.ID + " = ? ",    // selection clause
                new String[]{ movieId });       // selection args
    }

    public static void setMovieAsFavorite(Activity mActivity, MovieDetails movieDetails) {

        ContentValues values = new ContentValues();
        values.put(MoviesContract.ID, movieDetails.getId());
        values.put(MoviesContract.TITLE, movieDetails.getTitle());
        values.put(MoviesContract.RATING, movieDetails.getRating());
        values.put(MoviesContract.GENRE, movieDetails.getGenre());
        values.put(MoviesContract.DATE, movieDetails.getReleaseDate());
        values.put(MoviesContract.STATUS, movieDetails.getStatus());
        values.put(MoviesContract.OVERVIEW, movieDetails.getOverview());
        values.put(MoviesContract.BACKDROP, movieDetails.getBackdropPath());
        values.put(MoviesContract.VOTE_COUNT, movieDetails.getVoteCount());
        values.put(MoviesContract.TAGLINE, movieDetails.getTagLine());
        values.put(MoviesContract.RUNTIME, movieDetails.getRuntime());
        values.put(MoviesContract.LANGUAGE, movieDetails.getOriginalLanguage());
        values.put(MoviesContract.POPULARITY, movieDetails.getPopularity());
        values.put(MoviesContract.POSTER, movieDetails.getPosterPath());

        mActivity.getContentResolver(). insert(
                MoviesContract.CONTENT_URI, values);
    }

}
