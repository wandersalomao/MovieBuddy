package com.udacity.nanodegree.wandersalomao.moviebuddy.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;

public class MoviesContentProvider extends ProviGenProvider {

    private static Class[] contracts = new Class[]{MoviesContract.class};

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper(getContext(), "moviesDB", null, 1, contracts);
    }

    @Override
    public Class[] contractClasses() {
        return contracts;
    }

}
