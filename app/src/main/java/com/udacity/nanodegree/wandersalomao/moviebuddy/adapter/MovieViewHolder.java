package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.nanodegree.wandersalomao.moviebuddy.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * This is a view holder class used to populate the movie card. This view holder is used by the
 * MovieListAdapter and FavoriteMoviesAdapter
 * @author Wander Salomao
 */
class MovieViewHolder extends RecyclerView.ViewHolder {

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