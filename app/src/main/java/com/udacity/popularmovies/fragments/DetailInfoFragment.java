package com.udacity.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.*;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.utils.PicassoUtils;
import com.udacity.popularmovies.views.DetailInfoView;

import butterknife.*;

public class DetailInfoFragment extends BaseFragment<DetailInfoView, DetailInfoPresenter> implements DetailInfoView {

    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.releaseDate_tv) TextView releaseDate;
    @BindView(R.id.runtime_tv) TextView runtime;
    @BindView(R.id.voteAverage_tv) TextView voteAverage;
    @BindView(R.id.overview_tv) TextView overview;
    @BindView(R.id.detailPoster_iv) ImageView poster;
    @BindView(R.id.markAsFavourite_ib) ImageButton markAsFavourite;

    private OnDetailInfoFragmentListener mListener;

    public interface OnDetailInfoFragmentListener {
        void setFavourite(boolean marked);
    }

    public static DetailInfoFragment newInstance(Movie movie) {
        DetailInfoFragment fragment = new DetailInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailPresenter.MOVIE_KEY, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public DetailInfoPresenter createPresenter() {
        return new DetailInfoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_info;
    }

    @OnClick(R.id.markAsFavourite_ib)
    public void onFavouriteButtonClick() {
        getPresenter().setFavourite();
    }

    @Override
    public void setFavourite(boolean marked) {
        if (marked)
            markAsFavourite.setImageResource(R.drawable.ic_favorite);
        else
            markAsFavourite.setImageResource(R.drawable.ic_favorite_border);
        mListener.setFavourite(marked);
    }

    @Override
    public void setData(Movie movie) {
        setFavourite(movie.isMarkedAsFavourite());
        title.setText(movie.getTitle());
        releaseDate.setText(Integer.toString(movie.getReleaseDate().getYear()));
        String runtime = (movie.getRuntime() == 0) ? "N/A" : movie.getRuntime() + "min";
        this.runtime.setText(runtime);
        voteAverage.setText(Double.toString(movie.getVoteAverage()) + "/10");
        overview.setText(movie.getOverview());
        String path = "w154" + movie.getPosterPath();
        PicassoUtils.loadImage(getActivity(), path, poster);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailInfoFragmentListener) {
            mListener = (OnDetailInfoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailInfoFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}