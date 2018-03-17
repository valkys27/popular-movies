package com.udacity.popularmovies.data.contentProviders;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.*;

import com.udacity.popularmovies.data.PopularMoviesContract;
import com.udacity.popularmovies.data.PopularMoviesContract.TrailerEntry;

/**
 * Created by tomas on 28.02.2018.
 */

public class TrailersContentProvider extends BaseContentProvider {

    public static final int CODE_TRAILERS = 300;
    public static final int CODE_TRAILERS_WITH_ID = 301;

    @Override
    protected void setURIs(UriMatcher uriMatcher) {
        uriMatcher.addURI(getAuthority(), PopularMoviesContract.PATH_TRAILER, CODE_TRAILERS);
        uriMatcher.addURI(getAuthority(), PopularMoviesContract.PATH_TRAILER + "/#", CODE_TRAILERS_WITH_ID);
    }

    @Override
    protected String getTableName() {
        return TrailerEntry.TABLE_NAME;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case CODE_TRAILERS_WITH_ID: {
                cursor = queryWithId(uri, projection, sortOrder);
                break;
            }
            case CODE_TRAILERS: {
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
            case CODE_TRAILERS:
                return bulkInsertWithCorrectUri(uri, values);
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
