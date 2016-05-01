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
import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.Constants;
import com.udacity.nanodegree.wandersalomao.moviebuddy.common.util.ImageLoaderCallback;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieDetails;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.MovieGenre;
import com.udacity.nanodegree.wandersalomao.moviebuddy.model.Trailer;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MovieDetails movieDetails;
    private Activity mActivity;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Trailer> trailerInfo;

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

        if (viewType == 0) {
            View view = mInflater.inflate(R.layout.layout_holder_movie_details, parent, false);
            viewHolder = new MovieDetailsViewHolder(view);
        } else if (viewType == 1) {
            View view = mInflater.inflate(R.layout.layout_holder_movie_trailer, parent, false);
            viewHolder = new MovieTrailerViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return 0;
        if (position > 0)
            return 1;

        return 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:

                final MovieDetailsViewHolder detailsViewHolder = (MovieDetailsViewHolder) holder;

                String posterURL = Constants.POSTER_BASE_URL + movieDetails.getPosterPath();

                Picasso.with(mContext)
                        .load(posterURL)
                        .into(detailsViewHolder.getImageView(), new ImageLoaderCallback(mContext, "Movie Poster"));

                int color = mContext.getResources().getColor(R.color.colorAccent);

                //detailsViewHolder.getRatingsBackground().getDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                //detailsViewHolder.getGenreBackground().getDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                //detailsViewHolder.getPopBackground().getDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                //detailsViewHolder.getLangBackground().getDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                detailsViewHolder.getTitleView().setText(movieDetails.getTitle());

                if (movieDetails.getTagLine() != null && !movieDetails.getTagLine().isEmpty()) {
                    detailsViewHolder.getTaglineView().setText("\"" + movieDetails.getTagLine() + "\"");
                } else {
                    detailsViewHolder.getTaglineView().setVisibility(View.GONE);
                }

                detailsViewHolder.getDateStatusView().setText(movieDetails.getReleaseDate()
                        + " (" + movieDetails.getStatus() + ")");

                detailsViewHolder.getDurationView().setText(mActivity.getString(R.string.duration)
                        + movieDetails.getRuntime() + mActivity.getString(R.string.min));

                //detailsViewHolder.getRatingView().setText(movieDetails.getRating());

                String genres = "";
                if (movieDetails.getGenres() != null) {
                    for (MovieGenre genre : movieDetails.getGenres()) {

                        if (!genres.isEmpty()) {
                            genres += ", ";
                        }

                        genres += genre.getName();
                    }
                }

                //detailsViewHolder.getGenreView().setText(genres);

                //if (movieDetails.getPopularity() != null) {
                    //detailsViewHolder.getPopularityView().setText(movieDetails.getPopularity().substring(0, 4));
                //}

                //detailsViewHolder.getLanguageView().setText(movieDetails.getOriginalLanguage());
                detailsViewHolder.getOverviewView().setText(movieDetails.getOverview());
                //detailsViewHolder.getVoteCountView().setText(movieDetails.getVoteCount());

                break;

            case 1:

                MovieTrailerViewHolder trailerViewHolder = (MovieTrailerViewHolder) holder;
                final Trailer trailer = trailerInfo.get(position - 1);

                String youtubeUrl = Constants.YOUTUBE_THUMB + trailer.getKey() + Constants.YOUTUBE_MEDIUM_QUALITY;

                Picasso.with(mContext)
                        .load(youtubeUrl)
                        .into(trailerViewHolder.getTrailerImage(), new ImageLoaderCallback(mContext, "TrailerPoster"));

                trailerViewHolder.getTitleView().setText(trailer.getName());
                trailerViewHolder.getSiteView().setText(mActivity.getString(R.string.site) + trailer.getSite());
                trailerViewHolder.getQualityView().setText(mActivity.getString(R.string.quality) + trailer.getSize() + "p");

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

    static class MovieDetailsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image) ImageView imageView;
        //@InjectView(R.id.ratings_background) ImageView ratingsBackground;
        //@InjectView(R.id.genre_background) ImageView genreBackground;
        //@InjectView(R.id.pop_background) ImageView popBackground;
        //@InjectView(R.id.lang_background) ImageView langBackground;
        @InjectView(R.id.title) TextView titleView;
        @InjectView(R.id.tagline) TextView taglineView;
        @InjectView(R.id.date_status) TextView dateStatusView;
        @InjectView(R.id.duration) TextView durationView;
        //@InjectView(R.id.rating) TextView ratingView;
        //@InjectView(R.id.genre) TextView genreView;
        //@InjectView(R.id.popularity) TextView popularityView;
        //@InjectView(R.id.language) TextView languageView;
        @InjectView(R.id.overview) TextView overviewView;
        //@InjectView(R.id.vote_count) TextView voteCountView;

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
