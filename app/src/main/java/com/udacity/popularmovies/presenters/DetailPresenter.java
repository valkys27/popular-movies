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

    static final String ID_KEY = "_id";
    static final String SERVER_ID_KEY = "serverId";
    private static final String LOADED_KEY = "loaded";

    private int _id;
    private int serverId;
    private boolean loaded = false;

    public DetailPresenter() {
        PopularMoviesApp.getAppComponent().injectDetailPresenter(this);
    }

    @Override
    public void writeToBundle(Bundle bundle) {
        bundle.putBoolean(LOADED_KEY, loaded);
    }

    @Override
    public void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            _id = bundle.getInt(ID_KEY);
            serverId = bundle.getInt(SERVER_ID_KEY);
            loaded = bundle.getBoolean(LOADED_KEY, false);
        }
    }

    @Override
    public void loadData() {
        if (view != null && loaded)
            this.view.setData(moviesDAO.findById(_id));
        else
            network.findMovieById(serverId, new OnLoadingHandler());
    }

    private class OnLoadingHandler implements Network.OnLoadingHandler<Movie> {
        @Override
        public void onLoadingSuccess(Movie data) {
            if (view != null) {
                data.set_id(_id);
                moviesDAO.update(data);
                view.setData(data);
                loaded = true;
            }
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                Movie movie = moviesDAO.findById(_id);
                if (movie == null)
                    loaded = false;
                else
                    view.setData(movie);
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}