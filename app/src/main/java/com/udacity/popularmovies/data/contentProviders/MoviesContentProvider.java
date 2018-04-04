package com.udacity.popularmovies.data.contentProviders;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.*;

import com.udacity.popularmovies.data.PopularMoviesContract.MovieEntry;

/**
 * Created by tomas on 28.02.2018.
 */

public class MoviesContentProvider extends BaseContentProvider {

    private static final int CODE_MOVIES = 100;
    private static final int CODE_MOVIES_WITH_ID = 101;

    @Override
    protected void setURIs(UriMatcher uriMatcher) {
        uriMatcher.addURI(MovieEntry.CONTENT_AUTHORITY, MovieEntry.PATH, CODE_MOVIES);
        uriMatcher.addURI(MovieEntry.CONTENT_AUTHORITY, MovieEntry.PATH + "/#", CODE_MOVIES_WITH_ID);
    }

    @Override
    protected String getTableName() {
        return MovieEntry.TABLE_NAME;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case CODE_MOVIES_WITH_ID: {
                cursor = queryWithId(uri, projection, sortOrder);
                break;
            }
            case CODE_MOVIES: {
                cursor = query(projection, selection, selectionArgs, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (mUriMatcher.match(uri)) {
            case CODE_MOVIES:
                return bulkInsertWithCorrectUri(uri, values);
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
