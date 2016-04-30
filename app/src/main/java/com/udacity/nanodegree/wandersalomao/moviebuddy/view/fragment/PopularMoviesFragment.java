package com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment;

import android.os.Bundle;
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

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PopularMoviesFragment extends Fragment{

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;

    private static final int NUMBER_GRID_COLUMNS = 2;

    //private int firstVisibleItem, visibleItemCount, totalItemCount;
    //private int previousTotal = 0;
    //private int pageCount = 1;
    //private boolean loading = true;
    //private int visibleThreshold = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_popular_movies_list, container, false);
        mAdapter = new MovieListAdapter(mMovieList, getActivity());
        mGridLayoutManager = new GridLayoutManager(getActivity(), NUMBER_GRID_COLUMNS);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        RestClient.getInstance().getMovies(popularMoviesCallback);

        return mRecyclerView;
    }

    private final Callback popularMoviesCallback = new Callback<MovieApiResponse>() {

        @Override
        public void success(MovieApiResponse apiResponse, Response response) {

            if (!apiResponse.getResults().isEmpty()) {
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
