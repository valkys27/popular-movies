package com.udacity.popularmovies.views;

import com.udacity.popularmovies.pojo.Movie;

/**
 * Created by tomas on 05.03.2018.
 */

public interface DetailView extends BaseView {
    void setData(Movie movie);
    void setFavourite(boolean marked);
}
