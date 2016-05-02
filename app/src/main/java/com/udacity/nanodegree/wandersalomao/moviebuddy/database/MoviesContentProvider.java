package com.udacity.nanodegree.wandersalomao.moviebuddy.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;

/**
 * ContentProvider class used to save local data. Used the ProviGen library in order to facilitate
 * the creation of the content provider
 *
 * @author Wander Salomao
 */
public class MoviesContentProvider extends ProviGenProvider {

    // the contracts
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
