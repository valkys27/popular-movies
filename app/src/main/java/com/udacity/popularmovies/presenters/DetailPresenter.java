package com.udacity.popularmovies.presenters;

import android.os.Bundle;
import android.util.Log;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.network.Network;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.views.DetailView;

/**
 * Created by tomas on 05.03.2018.
 */

public class DetailPresenter extends BasePresenter<DetailView> {

    public static final String SELECTED_MENU_ITEM_KEY = "selectedMenuItemId";
    public static final String MOVIE_KEY = "movie";

    private int selectedMenuItemId;
    private Movie movie;

    public DetailPresenter() {
        PopularMoviesApp.getAppComponent().injectDetailPresenter(this);
    }

    @Override
    public void writeToBundle(Bundle bundle) {
        bundle.putParcelable(MOVIE_KEY, movie);
        bundle.putInt(SELECTED_MENU_ITEM_KEY, selectedMenuItemId);
    }

    @Override
    public void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE_KEY);
            selectedMenuItemId = bundle.getInt(SELECTED_MENU_ITEM_KEY, R.id.navigation_info);
        }
    }

    @Override
    public void setData() {
        if (movie.getRuntime() == 0)
            network.findMovieById(movie.getServerId(), new OnLoadingHandler());
        else if (view != null) {
            view.setData(selectedMenuItemId);
        }
    }

    public void setFavourite(boolean marked) {
        movie.setMarkedAsFavourite(marked);
    }

    public void setSelectedMenuItemId(int menuItemId) {
        this.selectedMenuItemId = menuItemId;
        if (view != null)
            view.setData(menuItemId);
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
                view.setData(selectedMenuItemId);
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                if (movie == null)
                    view.showMessage("Server isn't available.");
                else
                    view.setData(selectedMenuItemId);
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}