package com.udacity.popularmovies.data.dao;

import android.content.Context;
import android.net.Uri;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.data.PopularMoviesContract.ReviewEntry;
import com.udacity.popularmovies.pojo.*;
import org.chalup.microorm.MicroOrm;

import javax.inject.Inject;
import java.util.List;

@PerApplication
public class ReviewsDAOImpl extends BaseDAO<Review> implements ReviewsDAO {

    @Inject
    public ReviewsDAOImpl(Context context, MicroOrm microOrm) {
        super(context, microOrm);
    }

    @Override
    public List<Review> getList(Movie movie) {
        String selection = ReviewEntry.COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = {Integer.toString(movie.get_id())};
        return getList(selection, selectionArgs, null);
    }

    @Override
    protected Class<Review> getEntityClass() {
        return Review.class;
    }

    @Override
    protected Uri getUri() {
        return ReviewEntry.CONTENT_URI;
    }
}
