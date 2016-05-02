package com.udacity.nanodegree.wandersalomao.moviebuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Trailer;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * This is an Adapter class for the Movie Details Fragment.
 * @author Wander Salomao
 */
public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MovieDetails movieDetails;
    private List<Trailer> trailerInfo;
    private Activity mActivity;
    private Context mContext;
    private LayoutInflater mInflater;

    public MovieDetailsAdapter(MovieDetails movieDetails, List<Trailer> trailerInfo, Activity mActivity) {
        this.movieDetails = movieDetails;
        this.trailerInfo = trailerInfo;
        this.mActivity = mActivity;

        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        RecyclerView.ViewHolder viewHolder = null;

        // viewType == 0 means we need to inflate the view details
        if (viewType == 0) {
            View view = mInflater.inflate(R.layout.layout_holder_movie_details, parent, false);
            viewHolder = new MovieDetailsViewHolder(view);
        } else if (viewType == 1) { // viewType == 1 means we need to inflate the trailer view
            View view = mInflater.inflate(R.layout.layout_holder_movie_trailer, parent, false);
            viewHolder = new MovieTrailerViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        // position 0 - view details
        if (position == 0)
            return 0;
        if (position > 0) // position greater than 0 - trailer view
            return 1;

        return 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            // if view details
            case 0:

                final MovieDetailsViewHolder detailsViewHolder = (MovieDetailsViewHolder) holder;

                // load the movie poster
                Picasso.with(mContext)
                        .load(movieDetails.getPosterPath())
                        .into(detailsViewHolder.getImageView(), new Callback.EmptyCallback());

                // set the movie title
                detailsViewHolder.getTitleView().setText(movieDetails.getTitle());

                // set the movie tagline is exists
                if (movieDetails.getTagLine() != null && !movieDetails.getTagLine().isEmpty()) {
                    detailsViewHolder.getTaglineView().setText(movieDetails.getTagLine());
                } else {
                    detailsViewHolder.getTaglineView().setVisibility(View.GONE);
                }

                // set the movie date status
                detailsViewHolder.getDateStatusView().setText(
                        mActivity.getString(R.string.movie_date_status,
                                movieDetails.getReleaseDate(),
                                movieDetails.getStatus()));

                // set the movie duration
                detailsViewHolder.getDurationView().setText(
                        mActivity.getString(R.string.movie_duration,
                                movieDetails.getRuntime()));

                // set the movie overview
                detailsViewHolder.getOverviewView().setText(movieDetails.getOverview());

                break;

            // if view trailers
            case 1:

                MovieTrailerViewHolder trailerViewHolder = (MovieTrailerViewHolder) holder;
                final Trailer trailer = trailerInfo.get(position - 1);

                // load the trailer poster
                String youtubeUrl = Constants.YOUTUBE_THUMB + trailer.getKey() + Constants.YOUTUBE_MEDIUM_QUALITY;
                Picasso.with(mContext)
                        .load(youtubeUrl)
                        .into(trailerViewHolder.getTrailerImage(), new Callback.EmptyCallback());

                // set the trailer name
                trailerViewHolder.getTitleView().setText(trailer.getName());

                // set the trailer site
                trailerViewHolder.getSiteView().setText(mActivity.getString(R.string.trailer_site, trailer.getSite()));

                // set the trailer quality
                trailerViewHolder.getQualityView().setText(mActivity.getString(R.string.trailer_quality, trailer.getSize()));

                // once a trailer is clicked we open youtube to watch it
                trailerViewHolder.getRippleLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_URL + trailer.getKey())));
                    }
                });

                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1 + trailerInfo.size();
    }

    /**
     * This is a ViewHolder class for the MovieDetails view
     * @author Wander Salomao
     */
    static class MovieDetailsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image) ImageView imageView;
        @InjectView(R.id.title) TextView titleView;
        @InjectView(R.id.tagline) TextView taglineView;
        @InjectView(R.id.date_status) TextView dateStatusView;
        @InjectView(R.id.duration) TextView durationView;
        @InjectView(R.id.overview) TextView overviewView;

        public MovieDetailsViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getTaglineView() {
            return taglineView;
        }

        public TextView getDateStatusView() {
            return dateStatusView;
        }

        public TextView getDurationView() {
            return durationView;
        }

        public TextView getOverviewView() {
            return overviewView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    /**
     * This is a ViewHolder class for the Movie Trailer view
     * @author Wander Salomao
     */
    static class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.trailer_image) ImageView trailerImage;
        @InjectView(R.id.trailer_title) TextView titleView;
        @InjectView(R.id.trailer_site) TextView siteView;
        @InjectView(R.id.trailer_quality) TextView qualityView;
        @InjectView(R.id.ripple) MaterialRippleLayout rippleLayout;

        public MovieTrailerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public ImageView getTrailerImage() {
            return trailerImage;
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getSiteView() {
            return siteView;
        }

        public TextView getQualityView() {
            return qualityView;
        }

        public MaterialRippleLayout getRippleLayout() {
            return rippleLayout;
        }
    }
}