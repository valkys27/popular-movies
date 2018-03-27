package com.udacity.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.*;
import android.view.*;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.udacity.popularmovies.presenters.BasePresenter;
import com.udacity.popularmovies.views.*;

import butterknife.ButterKnife;

/**
 * Created by tomas on 26.03.2018.
 */

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends MvpFragment<V, P> {

    abstract protected int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (getArguments() != null)
            getPresenter().readFromBundle(getArguments());
        init();
        getPresenter().setData();
    }

    protected void init() {}

    public void showMessage(String message) {
        assert getActivity() != null;
        ((DetailView)getActivity()).showMessage(message);
    }

    public Context getContext() {
        return getActivity();
    }
}
