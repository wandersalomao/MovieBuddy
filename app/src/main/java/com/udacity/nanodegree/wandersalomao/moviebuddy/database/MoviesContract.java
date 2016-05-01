package com.udacity.nanodegree.wandersalomao.moviebuddy.database;

import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

public interface MoviesContract extends ProviGenBaseContract {

    @Column(Column.Type.INTEGER)
    String ID = "id";

    @Column(Column.Type.TEXT)
    String TITLE = "title";

    @Column(Column.Type.TEXT)
    String RATING = "rating";

    @Column(Column.Type.TEXT)
    String GENRE = "genre";

    @Column(Column.Type.TEXT)
    String DATE = "release_date";

    @Column(Column.Type.TEXT)
    String STATUS = "status";

    @Column(Column.Type.TEXT)
    String OVERVIEW = "overview";

    @Column(Column.Type.TEXT)
    String BACKDROP = "backdrop";

    @Column(Column.Type.TEXT)
    String VOTE_COUNT = "vote_count";

    @Column(Column.Type.TEXT)
    String TAGLINE = "tag_line";

    @Column(Column.Type.TEXT)
    String RUNTIME = "runtime";

    @Column(Column.Type.TEXT)
    String LANGUAGE = "language";

    @Column(Column.Type.TEXT)
    String POPULARITY = "popularity";

    @Column(Column.Type.TEXT)
    String POSTER = "poster";

    @ContentUri
    Uri CONTENT_URI = Uri.parse("content://com.udacity.nanodegree.wandersalomao.moviebuddy.provider/movies");

}
