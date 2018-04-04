package com.udacity.popularmovies.data.dao;

import com.udacity.popularmovies.Category;
import com.udacity.popularmovies.pojo.Movie;

import java.util.List;

/**
 * Created by tomas on 01.03.2018.
 */

public interface MoviesDAO extends DAO<Movie> {
    List<Movie> getList(Category category);
}
