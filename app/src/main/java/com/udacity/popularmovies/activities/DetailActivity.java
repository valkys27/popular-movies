package com.udacity.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.*;
import android.view.MenuItem;

import android.widget.TextView;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.fragments.*;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.views.DetailView;

import butterknife.BindView;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.navigation) BottomNavigationView navigationView;

    @Override
    protected void init() {
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @NonNull
    @Override
    public DetailPresenter createPresenter() {
        return new DetailPresenter();
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent(getIntent());
        data.putExtra(DetailPresenter.MOVIE_KEY, getPresenter().getMovie());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = (savedInstanceState == null) ? getIntent().getExtras() : savedInstanceState;
        super.onCreate(bundle);
    }

    @Override
    public void setMovieTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setData(int menuItemId) {
        setFragment(menuItemId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getPresenter().setSelectedMenuItemId(item.getItemId());
        return true;
    }

    private void setFragment(int menuItemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Movie data = getPresenter().getMovie();
        Fragment fragment;
        String tag;
        switch (menuItemId) {
            case R.id.navigation_info:
                tag = DetailInfoFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null)
                    fragment = BaseFragment.newInstance(new DetailInfoFragment(), data);
                break;
            case R.id.navigation_trailers:
                tag = DetailTrailersFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null)
                    fragment = BaseFragment.newInstance(new DetailTrailersFragment(), data);
                break;
            case R.id.navigation_reviews:
                tag = DetailReviewsFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null)
                    fragment = BaseFragment.newInstance(new DetailReviewsFragment(), data);
                break;
            default:
                throw new IllegalArgumentException();
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setTitle() {
        setTitle("Movie Detail");
    }

    @Override
    public void setFavourite(boolean marked) {
        getPresenter().setFavourite(marked);
    }

    @Override
    public void setTrailersLoaded() {
        getPresenter().setTrailersLoaded();
    }

    @Override
    public void setReviewsLoaded() {
        getPresenter().setReviewsLoaded();
    }
}