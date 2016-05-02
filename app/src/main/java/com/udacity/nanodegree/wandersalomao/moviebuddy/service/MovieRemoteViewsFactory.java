package com.udacity.nanodegree.wandersalomao.moviebuddy.service;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.database.MovieDAO;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;

import java.util.List;

/**
 * Factory class used to process and return the widgets view items
 * @author Wander Salomao
 */
public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    // the context
    private Context mContext;

    // list of movies available on the widget
    private List<MovieDetails> mMovies;

    public MovieRemoteViewsFactory(Context context, Intent intent) {

        mContext = context;
    }

    public void onCreate() {
        // Since we reload the cursor in onDataSetChanged() which gets called immediately after
        // onCreate(), we do nothing here.
    }

    public void onDestroy() {
    }

    public int getCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public RemoteViews getViewAt(int position) {

        // get the widget item
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item);

        // get the movie details
        MovieDetails movieDetails = mMovies.get(position);

        // sets the movie title
        rv.setTextViewText(R.id.widget_movie_title, movieDetails.getTitle());

        return rv;
    }

    public RemoteViews getLoadingView() {
        // We aren't going to return a default loading view in this sample
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {

        // get my favorite movies
        mMovies = MovieDAO.getMyFavoriteMovies(mContext);
    }
}
