package com.udacity.popularmovies.views;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by tomas on 06.03.2018.
 */

public interface BaseView extends MvpView {
    void showMessage(String message);
    Context getContext();
}
