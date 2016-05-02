package com.udacity.nanodegree.wandersalomao.moviebuddy.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Service class used to load data for the app widget
 * @author Wander Salomao
 */
public class MovieWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
