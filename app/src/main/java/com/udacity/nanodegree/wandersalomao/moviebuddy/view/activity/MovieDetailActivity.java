package com.udacity.nanodegree.wandersalomao.moviebuddy.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.nanodegree.wandersalomao.moviebuddy.R;

/**
 * Activity for the Movie Details screen
 * @author Wander Salomao
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
    }

}
