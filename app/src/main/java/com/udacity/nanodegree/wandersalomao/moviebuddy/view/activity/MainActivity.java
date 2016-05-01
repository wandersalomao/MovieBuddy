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

public class MainActivity extends AppCompatActivity implements IMovieItemSelectedListener {

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

        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PopularMoviesFragment(), getString(R.string.popular_movies));
        adapter.addFragment(new FavoriteMoviesFragment(), getString(R.string.favorites));
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
    }

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

    @Override
    public void onMovieSelected(String movieId) {

        MovieDetailFragment fragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_movie_detail);

        if (fragment == null) {
            // fragment is not in the layout so start Activity and pass it the info about the selected item
            Intent mMovieDetailIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
            mMovieDetailIntent.putExtra("movieId", movieId);
            startActivity(mMovieDetailIntent);
        } else {
            // DisplayFragment (Fragment B) is in the layout (tablet layout),
            // so tell the fragment to update
            fragment.updateContent(movieId);
        }
    }

}
