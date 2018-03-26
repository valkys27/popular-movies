package com.udacity.popularmovies.presenters;

import android.os.Bundle;
import android.util.Log;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.network.Network;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.views.DetailView;

/**
 * Created by tomas on 05.03.2018.
 */

public class DetailPresenter extends BasePresenter<DetailView> {

    public static final String MOVIE_KEY = "movie";

    private Movie movie;

    public DetailPresenter() {
        PopularMoviesApp.getAppComponent().injectDetailPresenter(this);
    }

    @Override
    public void writeToBundle(Bundle bundle) {
        bundle.putParcelable(MOVIE_KEY, movie);
    }

    @Override
    public void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE_KEY);
        }
    }

    @Override
    public void setData() {
        if (view != null) {
            if (movie.getRuntime() == 0)
                network.findMovieById(movie.getServerId(), new OnLoadingHandler());
            else
                this.view.setData(movie);
        }
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

    private class OnLoadingHandler implements Network.OnLoadingHandler<Movie> {
        @Override
        public void onLoadingSuccess(Movie data) {
            data.set_id(movie.get_id());
            data.setMarkedAsFavourite(movie.isMarkedAsFavourite());
            movie = data;
            moviesDAO.update(data);
            if (view != null)
                view.setData(data);
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                if (movie == null)
                    view.showMessage("Server isn't available.");
                else
                    view.setData(movie);
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}