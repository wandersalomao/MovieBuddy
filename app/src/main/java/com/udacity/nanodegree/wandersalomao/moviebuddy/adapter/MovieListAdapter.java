package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.listener.IMovieItemSelectedListener;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Movie;

import java.util.ArrayList;

/**
 * This is an Adapter class for the Popular Movies Fragment.
 * @author Wander Salomao
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    // the list of popular movies
    private ArrayList<Movie> mMovieList = new ArrayList<>();

    // the layout inflater
    private LayoutInflater mInflater;

    // the context
    private Context mContext;

    // the callback used to delegate the action when a movie is selected
    private IMovieItemSelectedListener mAdapterCallback;

    public MovieListAdapter(ArrayList<Movie> movieList, Activity activity) {
        mMovieList = movieList;

        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Sets the callback
     */
    public void setmAdapterCallback(IMovieItemSelectedListener mAdapterCallback) {
        this.mAdapterCallback = mAdapterCallback;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.movie_list_item, parent, false);
        mContext = parent.getContext();
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {

        // get the selected
        final Movie selectedMovie = mMovieList.get(position);

        // the the movie title
        movieViewHolder.getTitleView().setText(selectedMovie.getTitle());

        // when a movie is selected we delegate the action to the callback
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mAdapterCallback.onMovieSelected(selectedMovie.getId());
            }
        });

        // load the movie poster
        Picasso.with(mContext)
                .load(selectedMovie.getPosterPath())
                .into(movieViewHolder.getImageView(), new Callback.EmptyCallback());
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

}
