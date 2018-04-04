package com.udacity.popularmovies.presenters;

import android.os.Bundle;
import android.util.Log;
import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.data.dao.ReviewsDAO;
import com.udacity.popularmovies.network.Network;
import com.udacity.popularmovies.pojo.*;
import com.udacity.popularmovies.views.DetailReviewsView;

import javax.inject.Inject;
import java.util.*;

public class DetailReviewsPresenter extends BasePresenter<DetailReviewsView>  {

    private static final String REVIEW_LIST_KEY = "reviews";

    @Inject
    ReviewsDAO reviewsDAO;

    private Movie movie;
    private List<Review> reviewList;

    public DetailReviewsPresenter() {
        PopularMoviesApp.getAppComponent().injectDetailReviewsPresenter(this);
    }

    @Override
    public void writeToBundle(Bundle bundle) {
        bundle.putParcelableArrayList(REVIEW_LIST_KEY, (ArrayList<Review>) reviewList);
    }

    @Override
    public void readFromBundle(Bundle bundle) {
        if (bundle != null && movie == null) {
            movie = bundle.getParcelable(DetailPresenter.MOVIE_KEY);
            reviewList = bundle.getParcelableArrayList(REVIEW_LIST_KEY);
        }
    }

    @Override
    public void setData() {
        if (movie.isReviewsLoaded())
        {
            if (reviewList == null || reviewList.isEmpty())
                reviewList = reviewsDAO.getList(movie);
            if (view != null)
                view.setData(reviewList);
        } else
            network.getReviewsByMovie(movie.getServerId(), new OnLoadingHandler());
    }

    private class OnLoadingHandler implements Network.OnLoadingHandler<List<Review>> {
        @Override
        public void onLoadingSuccess(List<Review> data) {
            for (Review review : data)
                review.setMovieId(movie.get_id());
            reviewsDAO.insert(data);
            reviewList = reviewsDAO.getList(movie);
            movie.setReviewsLoaded(true);
            if (view != null) {
                view.setLoaded();
                view.setData(reviewList);
            }
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                if (movie == null || !movie.isReviewsLoaded())
                    view.showMessage("Server isn't available.");
                else if (reviewList != null)
                    view.setData(reviewList);
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}