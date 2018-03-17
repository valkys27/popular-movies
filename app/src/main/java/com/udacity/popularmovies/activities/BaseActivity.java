package com.udacity.popularmovies.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.*;
import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.dagger.components.AppComponent;
import com.udacity.popularmovies.dagger.modules.AppModule;
import com.udacity.popularmovies.presenters.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by tomas on 05.03.2018.
 */

public abstract class BaseActivity<V extends MvpView, P extends BasePresenter<V>> extends MvpActivity<V,P> {

    @NonNull
    public abstract P createPresenter();
    protected abstract int getLayoutId();
    protected abstract void setTitle();
    protected abstract void injectPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setTitle();
        injectPresenter();
        getPresenter().readFromBundle(savedInstanceState);
        init();
        getPresenter().loadData();
    }

    protected void init() {}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getPresenter().writeToBundle(outState);
    }

    public Context getContext() {
        return this;
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}