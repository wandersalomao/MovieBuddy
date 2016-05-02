package com.udacity.nanodegree.wandersalomao.moviebuddy.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.nanodegree.wandersalomao.moviebuddy.R;
import com.udacity.nanodegree.wandersalomao.moviebuddy.listener.IMovieItemSelectedListener;
import com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment.FavoriteMoviesFragment;
import com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment.MovieDetailFragment;
import com.udacity.nanodegree.wandersalomao.moviebuddy.view.fragment.PopularMoviesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Main activity that shows the list of popular movies
 * @author Wander Salomao
 */
public class MainActivity extends AppCompatActivity implements IMovieItemSelectedListener {

    // injects the view objects
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.viewpager) ViewPager mViewPager;
    @InjectView(R.id.tabs) TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        // setting up the action bar
        setSupportActionBar(mToolbar);

        // setup the viewpager
        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Method that will configure the viewPager object
     */
    private void setupViewPager() {
        // create the adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the first Tab - Popular Movies
        adapter.addFragment(new PopularMoviesFragment(), getString(R.string.popular_movies));

        // add the second Tab - Favorites
        adapter.addFragment(new FavoriteMoviesFragment(), getString(R.string.favorites));

        // sets the adapter to the view pager
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
    }

    /**
     * Method called when the user selects a movie
     *
     * @param movieId - The ID of the selected movie
     */
    @Override
    public void onMovieSelected(String movieId) {

        // gets the detail fragment
        MovieDetailFragment fragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_movie_detail);

        // if the fragment is null it means we are using a phone
        if (fragment == null) {

            // fragment is not in the layout so start Activity and pass it the info about the selected item
            Intent mMovieDetailIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
            mMovieDetailIntent.putExtra("movieId", movieId);
            startActivity(mMovieDetailIntent);

        } else { // otherwise we are using a Tablet

            // update the existing fragment
            fragment.updateContent(movieId);
        }
    }

    /**
     * Adapter class used on the ViewPager view
     * @author Wander Salomao
     */
    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
