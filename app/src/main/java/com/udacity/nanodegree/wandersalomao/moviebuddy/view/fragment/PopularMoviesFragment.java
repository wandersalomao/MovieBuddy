package com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.adapter.MovieListAdapter;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Movie;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieApiResponse;
import com.udacity.nanodegree.wandersalomao.moviebuddy.rest.RestClient;
import com.udacity.nanodegree.wandersalomao.moviebuddy.view.activity.MainActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Fragment class responsible for the Popular Movies screen
 * @author Wander Salomao
 */
public class PopularMoviesFragment extends Fragment {

    // the list of popular movies
    private ArrayList<Movie> mMovieList = new ArrayList<>();

    // the popular movies adapter
    private MovieListAdapter mAdapter;

    // the number of columns used on this screen
    private static final int NUMBER_GRID_COLUMNS = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // create the recycle view and the grid layout manager
        RecyclerView mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_movies_list, container, false);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), NUMBER_GRID_COLUMNS);

        // creates the adapter and set the activity as callback
        mAdapter = new MovieListAdapter(mMovieList, getActivity());
        mAdapter.setmAdapterCallback((MainActivity) this.getActivity());

        // configure the recycle view
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        // call the api to get the list of popular movies
        RestClient.getInstance().getMovies(popularMoviesCallback);

        return mRecyclerView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // gets the movies list from the savedInstance if not null
            mMovieList = Parcels.unwrap(savedInstanceState.getParcelable("mMovieList"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // saves the movie list to be used later
        outState.putParcelable("mMovieList", Parcels.wrap(mMovieList));
    }

    /**
     * Callback object responsible for processing the results os the movie api call to get the
     * popular movies
     */
    private final Callback popularMoviesCallback = new Callback<MovieApiResponse>() {

        @Override
        public void success(MovieApiResponse apiResponse, Response response) {

            // if the results are not empty
            if (!apiResponse.getResults().isEmpty()) {
                // add them all to the movies list
                mMovieList.addAll(apiResponse.getResults());
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.printf("[DEBUG] RestMovieSource failure (getMovies) - " + error.getMessage());
        }
    };

}
