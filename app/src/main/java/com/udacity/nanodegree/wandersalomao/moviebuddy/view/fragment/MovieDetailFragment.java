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

/**
 * Fragment class responsible for the Movie Details screen
 * @author Wander Salomao
 */
public class MovieDetailFragment extends Fragment {

    // injects the views
    @InjectView(R.id.recycler_movie_details) RecyclerView mRecyclerView;
    @InjectView(R.id.collapsing_toolbar_layout_movie_details) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.toolbar_movie_details) Toolbar mToolbar;
    @InjectView(R.id.backdrop) ImageView mBackdrop;
    @InjectView(R.id.fab) FloatingActionButton fab;

    // the adapter
    private MovieDetailsAdapter mAdapter;

    // the selected movie ID
    private String mMovieId;

    // the movie details of the selected movie
    private MovieDetails mMovieDetails;

    // the trailers associated to the selected movie
    private List<Trailer> mTrailers;

    // indicates if the selected movie is already saved as favorite or not
    private boolean isFavoriteMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflates the detail view
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.inject(this, v);

        // creates the layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        // the movie details is an empty object at the beginning
        mMovieDetails = new MovieDetails();

        // the trailers are also empty
        mTrailers = new ArrayList<>();

        // get the movie ID passed in the Intent
        mMovieId = getActivity().getIntent().getStringExtra("movieId");

        // if the movie ID is not null we check if the movie is saved as favorite
        isFavoriteMovie = mMovieId != null && MovieDAO.isFavoriteMovie(getActivity(), mMovieId);

        // configure the toolbar
        mCollapsingToolbarLayout.setExpandedTitleColor( getResources().getColor(android.R.color.transparent));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));

        // configure the recycle view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // if the device is a tablet the movieID will not be passed the first time this fragment is
        // created so we don't need to add the navigation icon and do the remote call
        if (mMovieId != null) {

            // just added if it' a phone
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            RestClient.getInstance().getDetailMovie(mMovieId, movieDetailsCallback);
        }

        return v;
    }

    /**
     * Listener object that will be responsible for processing actions when the user clicks on the
     * Favorite button
     */
    private final View.OnClickListener onClickFavoriteListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            if (isFavoriteMovie) {

                // if the Movie is saved as favorite we need to remove it
                MovieDAO.unsetMovieAsFavorite(getActivity(), mMovieId);

                //show the snack bar with a message to the user
                Snackbar.make(view, getResources().getString(R.string.removed_from_favorites), Snackbar.LENGTH_LONG)
                        .show();

                // updates the fab icon
                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like_outline));

                // updates the flag
                isFavoriteMovie = false;

            } else {

                // if the movie is not saved yet, we need to add it to the favorites
                MovieDAO.setMovieAsFavorite(getActivity(), mMovieDetails);

                //show the snack bar with a message to the user
                Snackbar.make(view, getResources().getString(R.string.added_to_favorites), Snackbar.LENGTH_LONG)
                        .show();

                // updates the fab icon
                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like));

                // updates the flag
                isFavoriteMovie = true;
            }
        }
    };

    /**
     * Callback object responsible for processing the results os the movie api call to get the
     * movie details
     */
    private final Callback movieDetailsCallback = new Callback<MovieDetails>() {

        @Override
        public void success(MovieDetails movieDetails, Response response) {

            // updates the favorite button icon depending on whether or not the movie is favorite
            if (isFavoriteMovie) {
                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like));
            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like_outline));
            }

            // show the favorite button and set its listener
            fab.show();
            fab.setOnClickListener(onClickFavoriteListener);

            // load the big poster image
            Picasso.with(mBackdrop.getContext())
                    .load(Constants.POSTER_BASE_URL_780 + movieDetails.getBackdropPath())
                    .into(mBackdrop, new com.squareup.picasso.Callback.EmptyCallback());

            // set the toolbar title
            mCollapsingToolbarLayout.setTitle(movieDetails.getTitle());

            // set the movieDetails object and the adapter
            mMovieDetails = movieDetails;
            mAdapter = new MovieDetailsAdapter(mMovieDetails, mTrailers, getActivity());
            mRecyclerView.setAdapter(mAdapter);

            // call the api to get the trailer
            RestClient.getInstance().getTrailers(mMovieId, trailerDetailsCallback);
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.printf("[DEBUG] RestMovieSource failure (getDetailMovie) - " + error.getMessage());
        }
    };

    /**
     * Callback object responsible for processing the results os the movie api call to get the
     * trailer details
     */
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

    /**
     * Method used to update the fragment contents when the user is using a Tablet and selects a
     * movie
     * @param movieId - The ID of the selected movie
     */
    public void updateContent(String movieId) {
        mMovieId = movieId;
        isFavoriteMovie = mMovieId != null && MovieDAO.isFavoriteMovie(getActivity(), mMovieId);
        RestClient.getInstance().getDetailMovie(movieId, movieDetailsCallback);
    }
}
