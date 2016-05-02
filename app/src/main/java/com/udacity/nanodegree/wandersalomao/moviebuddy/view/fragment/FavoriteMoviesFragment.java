package com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.adapter.FavoriteMoviesAdapter;
import com.udacity.nanodegree.wandersalomao.moviebuddy.database.MoviesContract;
import com.udacity.nanodegree.wandersalomao.moviebuddy.view.activity.MainActivity;

/**
 * Fragment class responsible for the Favorites screen
 * @author Wander Salomao
 */
public class FavoriteMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // the Favorite Movies adapter
    private FavoriteMoviesAdapter mAdapter;

    // the number of columns used on this screen
    private static final int NUMBER_GRID_COLUMNS = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // create the recycle view and the grid layout manager
        RecyclerView mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_movies_list, container, false);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), NUMBER_GRID_COLUMNS);

        // creates the adapter and set the activity as callback
        mAdapter = new FavoriteMoviesAdapter(getContext(), null);
        mAdapter.setmAdapterCallback((MainActivity) this.getActivity());

        // configure the recycle view
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        // Initializes the CursorLoader.
        getLoaderManager().initLoader(0, null, this);

        return mRecyclerView;
    }

    /**
     * Callback that's invoked when the system has initialized the Loader and
     * is ready to start the query. This usually happens when initLoader() is
     * called. The loaderID argument contains the ID value passed to the
     * initLoader() call.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {

        return new CursorLoader(
                getActivity(),                  // Parent activity context
                MoviesContract.CONTENT_URI,     // Table to query
                null,                           // Projection to return
                null,                           // No selection clause
                null,                           // No selection arguments
                null                            // Default sort order
        );
    }

    /**
     * Defines the callback that CursorLoader calls
     * when it's finished its query
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        /**
         * Moves the query results into the adapter
         */
        mAdapter.changeCursor(cursor);
    }

    /**
     * Invoked when the CursorLoader is being reset. For example, this is
     * called if the data in the provider changes and the Cursor becomes stale.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        /**
         * Clears out the adapter's reference to the Cursor.
         * This prevents memory leaks.
         */
        mAdapter.changeCursor(null);

    }
}
