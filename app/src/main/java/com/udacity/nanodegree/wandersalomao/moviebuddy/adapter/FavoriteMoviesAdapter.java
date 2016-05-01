package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.ImageLoaderCallback;
import com.udacity.nanodegree.wandersalomao.moviebuddy.database.MoviesContract;
import com.udacity.nanodegree.wandersalomao.moviebuddy.listener.IMovieItemSelectedListener;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    // Because RecyclerView.Adapter in its current form doesn't natively support cursors,
    // we wrap a CursorAdapter that will do all the job for us.
    //CursorAdapter mCursorAdapter;

    private Cursor mCursor;
    private Context mContext;
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

        if (mCursor != null) {
            mCursor.moveToPosition(position);

            final String movieId = mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.ID));
            final String title = mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.TITLE));
            final String posterPath = mCursor.getString(mCursor.getColumnIndexOrThrow(MoviesContract.POSTER));

            movieViewHolder.getTitleView().setText( title );

            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mAdapterCallback.onMovieSelected(movieId);
                }
            });

            Picasso.with(mContext)
                    .load(Constants.POSTER_BASE_URL + posterPath)
                    .into(movieViewHolder.getImageView(), new ImageLoaderCallback(mContext, "MoviePoster"));
        }
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public void setmAdapterCallback(IMovieItemSelectedListener mAdapterCallback) {
        this.mAdapterCallback = mAdapterCallback;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    @Nullable
    private Cursor swapCursor(Cursor cursor) {

        // if the cursor is the same we do nothing
        if (mCursor == cursor) {
            return null;
        }

        Cursor oldCursor = mCursor;
        mCursor = cursor;

        //if (cursor != null) {
        this.notifyDataSetChanged();
        //}

        return oldCursor;
    }

    /*
    static class FavoriteMoviesCursorAdapter extends CursorAdapter {

        private IMovieItemSelectedListener mAdapterCallback;

        public FavoriteMoviesCursorAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, 0);
        }

        public void setmAdapterCallback(IMovieItemSelectedListener mAdapterCallback) {
            this.mAdapterCallback = mAdapterCallback;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
            MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setTag(viewHolder);

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            MovieViewHolder viewHolder = (MovieViewHolder) view.getTag();

            final String movieId = cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.ID));
            final String title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.TITLE));
            final String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesContract.POSTER));

            viewHolder.getTitleView().setText( title );

            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mAdapterCallback.onMovieSelected(movieId);
                }
            });

            Picasso.with(context)
                    .load(Constants.POSTER_BASE_URL + posterPath)
                    .into(viewHolder.getImageView(), new ImageLoaderCallback(context, "MoviePoster"));
        }
    }
    */
}

