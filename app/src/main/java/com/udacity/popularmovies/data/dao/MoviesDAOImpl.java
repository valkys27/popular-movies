package com.udacity.popularmovies.data.dao;

import android.content.Context;

import com.udacity.popularmovies.Category;
import com.udacity.popularmovies.data.PopularMoviesContract;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.data.PopularMoviesContract.MovieEntry;

import java.util.List;

/**
 * Created by tomas on 01.03.2018.
 */

public class MoviesDAOImpl extends BaseDAO<Movie> implements MoviesDAO {

    public MoviesDAOImpl(Context context) {
        super(context);
    }

    @Override
    public List<Movie> getList(Category category) {
        String orderBy = (category.equals(Category.POPULAR)) ?
                MovieEntry.COLUMN_POPULARITY + " DESC" :
                MovieEntry.COLUMN_VOTE_AVERAGE + " DESC";
        return getList(null, null, orderBy);
    }

    @Override
    protected String getPath() {
        return PopularMoviesContract.PATH_MOVIE;
    }

    @Override
    protected Class<Movie> getEntityClass() {
        return Movie.class;
    }
}
