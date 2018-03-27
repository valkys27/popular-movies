package com.udacity.popularmovies.presenters;

import android.os.Bundle;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.views.DetailInfoView;

/**
 * Created by tomas on 26.03.2018.
 */

public class DetailInfoPresenter extends BasePresenter<DetailInfoView> {

    private Movie movie;

    public DetailInfoPresenter() {
        PopularMoviesApp.getAppComponent().injectDetailInfoPresenter(this);
    }

    @Override
    public void writeToBundle(Bundle bundle) {}

    @Override
    public void readFromBundle(Bundle bundle) {
        movie = bundle.getParcelable(DetailPresenter.MOVIE_KEY);
    }

    @Override
    public void setData() {
        if (view != null)
            view.setData(movie);
    }

    public void setFavourite() {
        movie.setMarkedAsFavourite(!movie.isMarkedAsFavourite());
        if (view != null)
            view.setFavourite(movie.isMarkedAsFavourite());
        moviesDAO.update(movie);
    }

    public Movie getMovie() {
        return movie;
    }
}