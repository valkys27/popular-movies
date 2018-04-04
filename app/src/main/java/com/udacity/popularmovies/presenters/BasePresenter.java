package com.udacity.popularmovies.presenters;

import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.*;
import com.udacity.popularmovies.network.Network;

import javax.inject.Inject;

/**
 * Created by tomas on 05.03.2018.
 */

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    protected V view;

    @Inject
    Network network;

    public abstract void writeToBundle(Bundle bundle);
    public abstract void readFromBundle(Bundle bundle);
    public abstract void setData();

    @Override
    public void attachView(V view) {
        super.attachView(view);
        this.view = view;
    }

    @Override
    public void detachView() {
        super.detachView();
        this.view = null;
        if (network != null)
            network.cancelAll();
    }
}
