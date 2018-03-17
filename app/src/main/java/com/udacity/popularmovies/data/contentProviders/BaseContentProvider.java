package com.udacity.popularmovies.data.contentProviders;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.*;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.data.*;

import javax.inject.Inject;

/**
 * Created by tomas on 28.02.2018.
 */

public abstract class BaseContentProvider extends ContentProvider {

    protected UriMatcher mUriMatcher;

    @Inject
    DbProvider mDbProvider;

    abstract protected void setURIs(UriMatcher uriMatcher);
    abstract protected String getTableName();

    public UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        setURIs(uriMatcher);
        return uriMatcher;
    }

    protected String getAuthority() {
        return PopularMoviesContract.CONTENT_AUTHORITY;
    }

    @Override
    public boolean onCreate() {
        mUriMatcher = buildUriMatcher();
        PopularMoviesApp.getAppComponent().injectContentProvider(this);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    protected Cursor queryWithId(@NonNull Uri uri, @Nullable String[] projection, @Nullable String sortOrder) {
        int id = Integer.parseInt(uri.getLastPathSegment());
        String[] selectionArguments = new String[]{Integer.toString(id)};
        return mDbProvider.getReadableDatabase().query(
                getTableName(),
                projection,
                "_id = ? ",
                selectionArguments,
                null,
                null,
                sortOrder);
    }

    protected Cursor query(@Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return mDbProvider.getReadableDatabase().query(
                getTableName(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder,
                "20");
    }

    protected int bulkInsertWithCorrectUri(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDbProvider.getWritableDatabase();
        db.beginTransaction();
        int rowsInserted = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(getTableName(), null, value);
                if (_id != -1)
                    rowsInserted++;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if (rowsInserted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsInserted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbProvider.getWritableDatabase();
        return db.update(getTableName(), values, selection, selectionArgs);
    }
}
