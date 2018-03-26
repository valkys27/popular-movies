package com.udacity.popularmovies.views;

import com.udacity.popularmovies.Category;
import com.udacity.popularmovies.pojo.Movie;

import java.util.List;

/**
 * Created by tomas on 28.02.2018.
 */

public interface MainListView extends BaseView {

    void setData(List<Movie> movies, Category category);
}
