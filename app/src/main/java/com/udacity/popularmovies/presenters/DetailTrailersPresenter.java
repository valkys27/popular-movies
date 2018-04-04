package com.udacity.popularmovies.presenters;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.adapters.TrailerAdapter;
import com.udacity.popularmovies.data.dao.TrailersDAO;
import com.udacity.popularmovies.network.Network;
import com.udacity.popularmovies.pojo.*;
import com.udacity.popularmovies.views.DetailTrailersView;

import javax.inject.Inject;
import java.util.*;

public class DetailTrailersPresenter extends BasePresenter<DetailTrailersView> implements TrailerAdapter.OnTrailerAdapterClickHandler {

    private static final String TRAILERS_LIST_KEY = "trailers";

    @Inject
    TrailersDAO trailersDAO;

    private Movie movie;
    private List<Trailer> trailerList;

    public DetailTrailersPresenter() {
        PopularMoviesApp.getAppComponent().injectDetailTrailersPresenter(this);
    }

    @Override
    public void writeToBundle(Bundle bundle) {
        bundle.putParcelableArrayList(TRAILERS_LIST_KEY, (ArrayList<Trailer>) trailerList);
    }

    @Override
    public void readFromBundle(Bundle bundle) {
        if (bundle != null && movie == null) {
            movie = bundle.getParcelable(DetailPresenter.MOVIE_KEY);
            trailerList = bundle.getParcelableArrayList(TRAILERS_LIST_KEY);
        }
    }

    @Override
    public void setData() {
        if (movie.isTrailersLoaded())
        {
            if (trailerList == null || trailerList.isEmpty())
                trailerList = trailersDAO.getList(movie);
            if (view != null)
                view.setData(trailerList);
        } else
            network.getTrailersByMovie(movie.getServerId(), new OnLoadingHandler());
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
        try {
            if (view != null)
                view.getContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
            if (view != null)
                view.getContext().startActivity(webIntent);
        }
    }

    private class OnLoadingHandler implements Network.OnLoadingHandler<List<Trailer>> {
        @Override
        public void onLoadingSuccess(List<Trailer> data) {
            for (Trailer trailer : data)
                trailer.setMovieId(movie.get_id());
            trailersDAO.insert(data);
            trailerList = trailersDAO.getList(movie);
            movie.setTrailersLoaded(true);
            if (view != null) {
                view.setLoaded();
                view.setData(trailerList);
            }
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                if (movie == null || !movie.isTrailersLoaded())
                    view.showMessage("Server isn't available.");
                else if (trailerList != null)
                    view.setData(trailerList);
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}