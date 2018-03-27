package com.udacity.popularmovies.views;

import com.udacity.popularmovies.pojo.Movie;

/**
 * Created by tomas on 26.03.2018.
 */

public interface DetailInfoView extends BaseView {
    void setData(Movie movie);
    void setFavourite(boolean marked);
}
