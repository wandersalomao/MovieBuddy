package com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.adapter.MovieDetailsAdapter;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.ImageLoaderCallback;
import com.udacity.nanodegree.wandersalomao.moviebuddy.database.MovieDAO;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Trailer;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.TrailerApiResponse;
import com.udacity.nanodegree.wandersalomao.moviebuddy.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetailFragment extends Fragment {

    @InjectView(R.id.recycler_movie_details) RecyclerView mRecyclerView;
    @InjectView(R.id.collapsing_toolbar_layout_movie_details) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.toolbar_movie_details) Toolbar mToolbar;
    @InjectView(R.id.backdrop) ImageView mBackdrop;
    @InjectView(R.id.fab) FloatingActionButton fab;

    private RecyclerView.LayoutManager mLayoutManager;
    private MovieDetailsAdapter mAdapter;

    private String movieId;
    private MovieDetails mMovieDetails;
    private List<Trailer> mTrailers;
    private boolean isFavoriteMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.inject(this, v);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mMovieDetails = new MovieDetails();
        mTrailers = new ArrayList<>();

        movieId = getActivity().getIntent().getStringExtra("movieId");
        isFavoriteMovie = MovieDAO.isFavoriteMovie(getActivity(), movieId);

        mCollapsingToolbarLayout.setExpandedTitleColor( getResources().getColor(android.R.color.transparent));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        if (isFavoriteMovie) {
            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like_outline));
        }

        fab.show();
        fab.setOnClickListener(onClickFavoriteListener);

        RestClient.getInstance().getDetailMovie(movieId, movieDetailsCallback);

        return v;
    }

    private final View.OnClickListener onClickFavoriteListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            if (isFavoriteMovie) {

                MovieDAO.unsetMovieAsFavorite(getActivity(), movieId);
                Snackbar.make(view, getResources().getString(R.string.removed_from_favorites), Snackbar.LENGTH_LONG)
                        .show();
                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like_outline));
                isFavoriteMovie = false;

            } else {

                MovieDAO.setMovieAsFavorite(getActivity(), mMovieDetails);

                Snackbar.make(view, getResources().getString(R.string.added_to_favorites), Snackbar.LENGTH_LONG)
                        .show();

                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like));

                isFavoriteMovie = true;
            }
        }
    };

    private final Callback movieDetailsCallback = new Callback<MovieDetails>() {

        @Override
        public void success(MovieDetails movieDetails, Response response) {

            Picasso.with(mBackdrop.getContext())
                    .load(Constants.POSTER_BASE_URL_780 + movieDetails.getBackdropPath())
                    .into(mBackdrop, new ImageLoaderCallback(mBackdrop.getContext(), "Backcrop Poster"));

            mCollapsingToolbarLayout.setTitle(movieDetails.getTitle());

            mMovieDetails = movieDetails;
            mAdapter = new MovieDetailsAdapter(mMovieDetails, mTrailers, getActivity());
            mRecyclerView.setAdapter(mAdapter);

            RestClient.getInstance().getTrailers(movieId, trailerDetailsCallback);

        }

        @Override
        public void failure(RetrofitError error) {

            System.out.printf("[DEBUG] RestMovieSource failure (getDetailMovie) - " + error.getMessage());
        }
    };

    private final Callback trailerDetailsCallback = new Callback<TrailerApiResponse>() {

        @Override
        public void success(TrailerApiResponse trailerApiResponse, Response response) {

            mTrailers = trailerApiResponse.getResults();
            mAdapter = new MovieDetailsAdapter(mMovieDetails, mTrailers, getActivity());
            mRecyclerView.setAdapter(mAdapter);

        }

        @Override
        public void failure(RetrofitError error) {

            System.out.printf("[DEBUG] RestMovieSource failure (getTrailers) - " + error.getMessage());
        }
    };

    public void updateContent(String movieId) {
        RestClient.getInstance().getDetailMovie(movieId, movieDetailsCallback);
    }

}
