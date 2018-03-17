package com.udacity.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.*;
import android.view.MenuItem;
import android.widget.*;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.presenters.*;
import com.udacity.popularmovies.utils.PicassoUtils;
import com.udacity.popularmovies.views.DetailView;

import butterknife.BindView;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.releaseDate_tv) TextView releaseDate;
    @BindView(R.id.runtime_tv) TextView runtime;
    @BindView(R.id.voteAverage_tv) TextView voteAverage;
    @BindView(R.id.overview_tv) TextView overview;
    @BindView(R.id.detailPoster_iv) ImageView poster;

    @NonNull
    @Override
    public DetailPresenter createPresenter() {
        return new DetailPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(getIntent().getExtras());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(getIntent().getExtras());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setTitle() {
        setTitle("Movie Detail");
    }

    @Override
    public void setData(Movie movie) {
        title.setText(movie.getTitle());
        releaseDate.setText(Integer.toString(movie.getReleaseDate().getYear()));
        String runtime = (movie.getRuntime() == 0) ? "N/A" : movie.getRuntime() + "min";
        this.runtime.setText(runtime);
        voteAverage.setText(Double.toString(movie.getVoteAverage()) + "/10");
        overview.setText(movie.getOverview());
        String path = "w154" + movie.getPosterPath();
        PicassoUtils.loadImage(this, path, poster);

    }
}
