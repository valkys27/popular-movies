package com.udacity.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.*;
import android.view.*;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.views.*;

import butterknife.ButterKnife;

/**
 * Created by tomas on 26.03.2018.
 */

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends MvpFragment<V, P> {

    abstract int getLayoutId();

    public static BaseFragment newInstance(BaseFragment fragment, Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(DetailPresenter.MOVIE_KEY, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        if (getArguments() != null)
            getPresenter().readFromBundle(getArguments());
        getPresenter().setData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getPresenter().writeToBundle(outState);
    }

    void init() {}

    public void showMessage(String message) {
        ((DetailView)getActivity()).showMessage(message);
    }

    public Context getContext() {
        return getActivity();
    }
}
