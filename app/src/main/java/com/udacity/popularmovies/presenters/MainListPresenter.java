package com.udacity.popularmovies.presenters;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.udacity.popularmovies.Category;
import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.activities.DetailActivity;
import com.udacity.popularmovies.adapters.MovieAdapter;
import com.udacity.popularmovies.network.Network;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.views.MainListView;

import java.util.*;

/**
 * Created by tomas on 28.02.2018.
 */

public class MainListPresenter extends BasePresenter<MainListView> {

    public static final String POSITION_KEY = "position";

    private static final int REQUEST_CODE = 100;
    private static final String CATEGORY_KEY = "category";
    private static final String MOVIE_LIST_KEY = "movieList";
    private static final String POPULAR_LOADED_KEY = "popularLoaded";
    private static final String TOP_RATED_LOADED_KEY = "topRatedLoaded";

    private List<Movie> movieList = null;
    private Category category = Category.POPULAR;
    private boolean popularLoaded = false;
    private boolean topRatedLoaded = false;
    private AdapterView.OnItemSelectedListener onCategorySelectedListener;

    public MainListPresenter() {
        PopularMoviesApp.getAppComponent().injectMainListPresenter(this);
        this.onCategorySelectedListener = new OnCategorySelectedListener();
    }

    public void initRecyclerView(RecyclerView recyclerView, MovieAdapter adapter) {
        if (view != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setCategorySpinner(Spinner spinner, Context actionBarContext) {
        spinner.setAdapter(getSpinnerAdapter(actionBarContext));
        spinner.setSelection(category.ordinal());
        spinner.setOnItemSelectedListener(onCategorySelectedListener);
    }

    private SpinnerAdapter getSpinnerAdapter(Context actionBarContext) {
        return ArrayAdapter.createFromResource(
                actionBarContext, R.array.category_menu, android.R.layout.simple_spinner_dropdown_item);
    }

    public void writeToBundle(Bundle bundle) {
        bundle.putInt(CATEGORY_KEY, category.ordinal());
        bundle.putBoolean(POPULAR_LOADED_KEY, popularLoaded);
        bundle.putBoolean(TOP_RATED_LOADED_KEY, topRatedLoaded);
        bundle.putParcelableArrayList(MOVIE_LIST_KEY, (ArrayList<Movie>) movieList);
    }

    public void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            int categoryOrdinal = bundle.getInt(CATEGORY_KEY, 0);
            category = Category.values()[categoryOrdinal];
            popularLoaded = bundle.getBoolean(POPULAR_LOADED_KEY);
            topRatedLoaded = bundle.getBoolean(TOP_RATED_LOADED_KEY);
            movieList = bundle.getParcelableArrayList(MOVIE_LIST_KEY);
        }
    }

    @Override
    public void setData() {
        if (isLoaded()) {
            movieList = moviesDAO.getList(category);
            if (view != null)
                view.setData(movieList, category);
        }
        else
            network.getMovieListBy(category, new OnLoadingHandler());
    }

    private boolean isLoaded() {
        return movieList != null && movieList.size() > 0 && (
                        (popularLoaded && category.equals(Category.POPULAR)) ||
                        (topRatedLoaded && category.equals(Category.TOP_RATED))
                || category.equals(Category.FAVOURITE));
    }

    public MovieAdapter.OnMovieAdapterClickHandler getPosterClickHandler() {
        return new OnPosterClickHandler();
    }

    private class OnCategorySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (movieList != null) {
                category = Category.values()[position];
                setData();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    private class OnPosterClickHandler implements MovieAdapter.OnMovieAdapterClickHandler {
        @Override
        public void onPosterClick(int position, Movie movie) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DetailPresenter.MOVIE_KEY, movie);
            intent.putExtra(POSITION_KEY, position);
            ((Activity)view.getContext()).startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private class OnLoadingHandler implements Network.OnLoadingHandler<List<Movie>> {
        @Override
        public void onLoadingSuccess(List<Movie> data) {
            if (view != null) {
                moviesDAO.insert(data);
                movieList = moviesDAO.getList(category);
                view.setData(movieList, category);
                if (category.equals(Category.POPULAR))
                    popularLoaded = true;
                else if (category.equals(Category.TOP_RATED))
                    topRatedLoaded = true;
            }
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                List<Movie> movies = moviesDAO.getList(category);
                view.setData(movies, category);
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}