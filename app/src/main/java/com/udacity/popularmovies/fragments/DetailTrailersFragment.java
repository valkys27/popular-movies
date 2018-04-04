package com.udacity.popularmovies.fragments;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.*;
import butterknife.BindView;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.TrailerAdapter;
import com.udacity.popularmovies.pojo.*;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.views.DetailTrailersView;

import java.util.List;

public class DetailTrailersFragment extends RecyclerViewFragment<DetailTrailersView, DetailTrailersPresenter> implements DetailTrailersView {

    public static final String TAG = DetailTrailersFragment.class.getName();

    @BindView(R.id.trailerList_rv) RecyclerView mRecyclerView;

    private TrailerAdapter mTrailerAdapter;
    private OnDetailTrailersFragmentListener mListener;

    public interface OnDetailTrailersFragmentListener {
        void setTrailersLoaded();
    }

    @NonNull
    @Override
    public DetailTrailersPresenter createPresenter() {
        return new DetailTrailersPresenter();
    }

    @Override
    public void setData(List<Trailer> trailers) {
        mTrailerAdapter.setData(trailers);
    }

    @Override
    public void setLoaded() {
        mListener.setTrailersLoaded();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailTrailersFragmentListener) {
            mListener = (OnDetailTrailersFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailTrailersFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_detail_trailers;
    }

    @Override
    RecyclerView.Adapter getAdapter() {
        if (mTrailerAdapter == null)
            mTrailerAdapter = new TrailerAdapter(getContext(), getPresenter());
        return mTrailerAdapter;
    }

    @Override
    RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}