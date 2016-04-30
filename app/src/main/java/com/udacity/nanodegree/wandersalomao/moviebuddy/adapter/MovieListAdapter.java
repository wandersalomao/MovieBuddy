package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Movie;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private Activity mActivity;
    private LayoutInflater mInflater;
    private Context mContext;

    public MovieListAdapter(ArrayList<Movie> movieList, Activity activity) {
        mMovieList = movieList;
        mActivity = activity;

        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.movie_list_item, parent, false);
        mContext = parent.getContext();
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        Movie selectedMovie = mMovieList.get(position);

        movieViewHolder.getTitleView().setText(selectedMovie.getTitle());

        String posterURL = Constants.POSTER_BASE_URL + selectedMovie.getPosterPath();

        Picasso.with(mContext)
                .load(posterURL)
                .into(movieViewHolder.getImageView(), new Callback() {

                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image) ImageView imageView;
        @InjectView(R.id.title) TextView titleView;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTitleView() {
            return titleView;
        }
    }
}
