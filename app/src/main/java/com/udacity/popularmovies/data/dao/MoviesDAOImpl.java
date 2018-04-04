package com.udacity.popularmovies.data.dao;

import android.content.ContentValues;
import android.content.Context;

import android.net.Uri;
import com.udacity.popularmovies.Category;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.data.PopularMoviesContract.MovieEntry;

import org.chalup.microorm.MicroOrm;

import java.util.*;

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
    public int insert(List<Movie> list) {
        List<ContentValues> cvs = new ArrayList<>();
        for (Movie movie : list) {
            Movie old = findByServerId(movie.getServerId());
            if (old == null) {
                ContentValues cv = mMicroOrm.toContentValues(movie);
                cv.remove(ID_KEY);
                cvs.add(cv);
            } else {
                movie.set_id(old.get_id());
                update(movie);
            }
        }
        return mContext.getContentResolver().bulkInsert(getUri(), cvs.toArray(new ContentValues[0]));
    }

    @Override
    protected Class<Movie> getEntityClass() {
        return Movie.class;
    }

    @Override
    protected Uri getUri() {
        return MovieEntry.CONTENT_URI;
    }
}
