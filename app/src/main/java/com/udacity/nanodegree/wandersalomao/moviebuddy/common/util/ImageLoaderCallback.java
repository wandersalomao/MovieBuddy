package com.udacity.nanodegree.wandersalomao.moviebuddy.common.util;

import android.content.Context;
import android.widget.Toast;

import com.squareup.picasso.Callback;

public class ImageLoaderCallback implements Callback {

    private String imageName;
    private Context context;

    public ImageLoaderCallback(Context context, String imageName) {
        this.imageName = imageName;
        this.context = context;
    }

    @Override
    public void onSuccess() {}

    @Override
    public void onError() {
        Toast.makeText(context,"Error loading the image " + imageName, Toast.LENGTH_SHORT).show();
    }

}
