package com.udacity.popularmovies.presenters;

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

import java.util.List;

/**
 * Created by tomas on 28.02.2018.
 */

public class MainListPresenter extends BasePresenter<MainListView> {

    private static final String CATEGORY_KEY = "category";
    private static final String LOADED_POPULAR_KEY = "loaded_popular";
    private static final String LOADED_TOP_RATED_KEY = "loaded_top_rated";

    private boolean loadedPopular = false;
    private boolean loadedTopRated = false;
    private int category = 0;
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
        spinner.setSelection(category);
        spinner.setOnItemSelectedListener(onCategorySelectedListener);
    }

    private SpinnerAdapter getSpinnerAdapter(Context actionBarContext) {
        return ArrayAdapter.createFromResource(
                actionBarContext, R.array.category_menu, android.R.layout.simple_spinner_dropdown_item);
    }

    public void writeToBundle(Bundle bundle) {
        bundle.putInt(CATEGORY_KEY, category);
        bundle.putBoolean(LOADED_POPULAR_KEY, loadedPopular);
        bundle.putBoolean(LOADED_TOP_RATED_KEY, loadedTopRated);
    }

    public void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            category = bundle.getInt(CATEGORY_KEY, 0);
            loadedPopular = bundle.getBoolean(LOADED_POPULAR_KEY, false);
            loadedTopRated = bundle.getBoolean(LOADED_TOP_RATED_KEY, false);
        }
    }

    public void loadData() {
        if (view != null && isLoaded())
            loadMovies();
        else
            network.getMovieListBy(Category.values()[category], new OnLoadingHandler());
    }

    private boolean isLoaded() {
        return (category == 0 && loadedPopular) || (category == 1 && loadedTopRated);
    }

    private void loadMovies() {
        this.view.setData(moviesDAO.getList(Category.values()[category]));
    }

    public MovieAdapter.OnMovieAdapterClickHandler getPosterClickHandler() {
        return new OnPosterClickHandler();
    }

    private class OnCategorySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            category = position;
            loadData();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    private class OnPosterClickHandler implements MovieAdapter.OnMovieAdapterClickHandler {
        @Override
        public void onPosterClick(int _id, int serverId) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DetailPresenter.ID_KEY, _id);
            intent.putExtra(DetailPresenter.SERVER_ID_KEY, serverId);
            view.getContext().startActivity(intent);
        }
    }

    private class OnLoadingHandler implements Network.OnLoadingHandler<List<Movie>> {
        @Override
        public void onLoadingSuccess(List<Movie> data) {
            if (view != null) {
                moviesDAO.insert(data);
                view.setData(moviesDAO.getList(Category.values()[category]));
                setLoaded();
            }
        }

        private void setLoaded() {
            if (category == 0)
                loadedPopular = true;
            else if (category == 1)
                loadedTopRated = true;
        }

        @Override
        public void onLoadingFailure(String errorMessage) {
            if (view != null) {
                List<Movie> movies = moviesDAO.getList(Category.values()[category]);
                view.setData(movies);
                if (movies.size()== 20)
                    setLoaded();
            }
            Log.e("Retrofit", "Loading error");
        }
    }
}
