package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.ImageLoaderCallback;
import com.udacity.nanodegree.wandersalomao.moviebuddy.listener.IMovieItemSelectedListener;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Movie;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private Activity mActivity;
    private LayoutInflater mInflater;
    private Context mContext;
    private IMovieItemSelectedListener mAdapterCallback;

    public MovieListAdapter(ArrayList<Movie> movieList, Activity activity) {
        mMovieList = movieList;
        mActivity = activity;

        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
    public void onBindViewHolder(MovieViewHolder movieViewHolder, final int position) {

        Movie selectedMovie = mMovieList.get(position);

        movieViewHolder.getTitleView().setText(selectedMovie.getTitle());

        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mAdapterCallback.onMovieSelected(mMovieList.get(position).getId());
            }
        });

        Picasso.with(mContext)
                .load(selectedMovie.getPosterPath())
                .into(movieViewHolder.getImageView(), new ImageLoaderCallback(mContext, "MoviePoster"));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
