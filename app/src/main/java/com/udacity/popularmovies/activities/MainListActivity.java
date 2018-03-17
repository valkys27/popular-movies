package com.udacity.popularmovies.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.adapters.MovieAdapter;
import com.udacity.popularmovies.views.MainListView;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.presenters.MainListPresenter;

import java.util.List;

import butterknife.*;

public class MainListActivity extends BaseActivity<MainListView, MainListPresenter> implements MainListView {

    @BindView(R.id.movie_list_rv) RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    @NonNull
    @Override
    public MainListPresenter createPresenter() {
        return new MainListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_main;
    }

    @Override
    protected void setTitle() {
        setTitle(getString(R.string.app_name));
    }

    @Override
    protected void injectPresenter() {
        PopularMoviesApp.getAppComponent().injectMainListPresenter(getPresenter());
    }

    @Override
    protected void init() {
        mMovieAdapter = new MovieAdapter(this, getPresenter().getPosterClickHandler());
        getPresenter().initRecyclerView(mRecyclerView, mMovieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) menuItem.getActionView();
        Context actionBarContext = getSupportActionBar().getThemedContext();
        getPresenter().setCategorySpinner(spinner, actionBarContext);
        return true;
    }

    @Override
    public void setData(List<Movie> movies) {
        mMovieAdapter.setData(movies);
    }
}
