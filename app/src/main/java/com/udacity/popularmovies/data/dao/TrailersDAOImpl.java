package com.udacity.popularmovies.data.dao;

import android.content.Context;
import android.net.Uri;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.data.PopularMoviesContract.TrailerEntry;
import com.udacity.popularmovies.pojo.*;
import org.chalup.microorm.MicroOrm;

import javax.inject.Inject;
import java.util.List;

@PerApplication
public class TrailersDAOImpl extends BaseDAO<Trailer> implements TrailersDAO {

    @Inject
    public TrailersDAOImpl(Context context, MicroOrm microOrm) {
        super(context, microOrm);
    }

    @Override
    public List<Trailer> getList(Movie movie) {
        String selection = TrailerEntry.COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = {Integer.toString(movie.get_id())};
        return getList(selection, selectionArgs, null);
    }

    @Override
    protected Class<Trailer> getEntityClass() {
        return Trailer.class;
    }

    @Override
    protected Uri getUri() {
        return TrailerEntry.CONTENT_URI;
    }
}
