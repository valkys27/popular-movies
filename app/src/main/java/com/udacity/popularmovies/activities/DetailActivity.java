package com.udacity.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.*;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.*;
import android.view.MenuItem;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.fragments.*;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.views.DetailView;

import butterknife.BindView;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

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
        getIntent().putExtra(DetailPresenter.MOVIE_KEY, getPresenter().getMovie());
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = (savedInstanceState == null) ? getIntent().getExtras() : savedInstanceState;
        super.onCreate(bundle);
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
        Fragment fragment;
        switch (menuItemId) {
            case R.id.navigation_info:
                fragment = DetailInfoFragment.newInstance(getPresenter().getMovie());
                break;
            case R.id.navigation_trailers:
                fragment = new DetailTrailersFragment();
                break;
            case R.id.navigation_reviews:
                fragment = new DetailReviewsFragment();
                break;
            default:
                throw new IllegalArgumentException();
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
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
}