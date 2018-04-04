package com.udacity.popularmovies.fragments;

import android.support.v7.widget.*;
import com.udacity.popularmovies.presenters.BasePresenter;
import com.udacity.popularmovies.views.BaseView;

abstract class RecyclerViewFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment<V, P>
        implements BaseView {

    abstract RecyclerView.Adapter getAdapter();
    abstract RecyclerView getRecyclerView();

    @Override
    void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        getRecyclerView().setLayoutManager(layoutManager);
        getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        getRecyclerView().setAdapter(getAdapter());
    }
}
