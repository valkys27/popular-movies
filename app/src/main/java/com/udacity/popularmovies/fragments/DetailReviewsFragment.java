package com.udacity.popularmovies.fragments;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.ReviewAdapter;
import com.udacity.popularmovies.pojo.Review;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.views.DetailReviewsView;

import java.util.List;

public class DetailReviewsFragment extends RecyclerViewFragment<DetailReviewsView, DetailReviewsPresenter> implements DetailReviewsView {

    public static final String TAG = DetailReviewsFragment.class.getName();

    @BindView(R.id.reviewList_rv) RecyclerView mRecyclerView;

    private ReviewAdapter mReviewAdapter;
    private OnDetailReviewsFragmentListener mListener;

    public interface OnDetailReviewsFragmentListener {
        void setReviewsLoaded();
    }

    @NonNull
    @Override
    public DetailReviewsPresenter createPresenter() {
        return new DetailReviewsPresenter();
    }

    @Override
    public void setData(List<Review> reviews) {
        mReviewAdapter.setData(reviews);
    }

    @Override
    public void setLoaded() {
        mListener.setReviewsLoaded();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailReviewsFragmentListener) {
            mListener = (OnDetailReviewsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailReviewsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_detail_reviews;
    }

    @Override
    RecyclerView.Adapter getAdapter() {
        if (mReviewAdapter == null)
            mReviewAdapter = new ReviewAdapter(getContext());
        return mReviewAdapter;
    }

    @Override
    RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}