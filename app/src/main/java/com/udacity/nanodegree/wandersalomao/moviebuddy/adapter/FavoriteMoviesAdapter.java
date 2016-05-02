package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.database.MoviesContract;
import com.udacity.nanodegree.wandersalomao.moviebuddy.listener.IMovieItemSelectedListener;

/**
 * This is an Adapter class for the Favorite Movies Fragment. Because the Favorite movies are
 * stored in a local database, this adapter uses a cursor to populate the view
 * @author Wander Salomao
 */
public class FavoriteMoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    // the Cursor containing the data
    private Cursor mCursor;

    // the Context
    private Context mContext;

    // the callback used to delegate the action when a movie is selected
    private IMovieItemSelectedListener mAdapterCallback;

    public FavoriteMoviesAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, final int position) {

        // if the cursor is not null
        if (mCursor != null) {

            // move to the right position
            mCursor.moveToPosition(position);

            // here we get the necessary data
            final String movieId = mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.ID));
            final String title = mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.TITLE));
            final String posterPath = mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.POSTER));

            // use the view holder to set up the view
            movieViewHolder.getTitleView().setText( title );
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    // when a movie is selected we delegate the action to the callback
                    mAdapterCallback.onMovieSelected(movieId);
                }
            });

            // load the movie poster
            Picasso.with(mContext)
                    .load(Constants.POSTER_BASE_URL + posterPath)
                    .into(movieViewHolder.getImageView(), new Callback.EmptyCallback());
        }
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    /**
     * Sets the callback
     */
    public void setmAdapterCallback(IMovieItemSelectedListener mAdapterCallback) {
        this.mAdapterCallback = mAdapterCallback;
    }

    /**
     * Change the current cursor
     *
     * @param cursor - The new cursor
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Private function used to swap the current cursor by the given one.
     * @param cursor - The new cursor
     * @return - Returns the old cursor
     */
    @Nullable
    private Cursor swapCursor(Cursor cursor) {

        // if the cursor is the same we do nothing
        if (mCursor == cursor) {
            return null;
        }

        Cursor oldCursor = mCursor;
        mCursor = cursor;

        this.notifyDataSetChanged();

        return oldCursor;
    }
}

