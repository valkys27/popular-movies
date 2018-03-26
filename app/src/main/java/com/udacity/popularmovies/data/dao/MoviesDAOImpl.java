package com.udacity.popularmovies.data.dao;

import android.content.Context;

import com.udacity.popularmovies.Category;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.data.PopularMoviesContract;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.data.PopularMoviesContract.MovieEntry;

import org.chalup.microorm.MicroOrm;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tomas on 01.03.2018.
 */

@PerApplication
public class MoviesDAOImpl extends BaseDAO<Movie> implements MoviesDAO {

    @Inject
    public MoviesDAOImpl(Context context, MicroOrm microOrm) {
        super(context, microOrm);
    }

    @Override
    public List<Movie> getList(Category category) {
        String selection = null;
        String orderBy = (category.equals(Category.POPULAR)) ?
                MovieEntry.COLUMN_POPULARITY + " DESC" :
                MovieEntry.COLUMN_VOTE_AVERAGE + " DESC";
        if (category.equals(Category.FAVOURITE))
            selection = MovieEntry.COLUMN_MARKED_AS_FAVOURITE + " = 1";
        return getList(selection, null, orderBy);
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
