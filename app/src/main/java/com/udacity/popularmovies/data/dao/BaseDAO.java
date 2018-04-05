package com.udacity.popularmovies.data.dao;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.popularmovies.pojo.Pojo;
import com.udacity.popularmovies.data.*;

import org.chalup.microorm.MicroOrm;

import java.util.*;

/**
 * Created by tomas on 28.02.2018.
 */

public abstract class BaseDAO<T extends Pojo> implements DAO<T> {

    static final String ID_KEY = PopularMoviesContract.MovieEntry._ID;
    static final String SERVER_ID_KEY = PopularMoviesContract.MovieEntry.COLUMN_MOVIE_ID;

    final Context mContext;
    final MicroOrm mMicroOrm;

    BaseDAO(Context context, MicroOrm microOrm) {
        mContext = context;
        mMicroOrm = microOrm;
    }

    abstract protected Class<T> getEntityClass();
    abstract protected Uri getUri();

    @Override
    public List<T> getList(String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = mContext.getContentResolver().query(
                getUri(),
                null,
                selection,
                selectionArgs,
                sortOrder
        );
        return mMicroOrm.listFromCursor(cursor, getEntityClass());
    }

    @Override
    public T findById(int id) {
        return find(ID_KEY, Integer.toString(id));
    }

    private T find(String keyName, String id) {
        String selection = keyName + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = mContext.getContentResolver().query(getUri(), null, selection, selectionArgs, null);
        return (cursor == null || !cursor.moveToFirst()) ? null : mMicroOrm.fromCursor(cursor, getEntityClass());
    }

    @Override
    public T findByServerId(String serverId) {
        return find(SERVER_ID_KEY, serverId);
    }

    @Override
    public int insert(List<T> list) {
        List<ContentValues> cvs = new ArrayList<>();
        for (T item : list) {
            ContentValues cv = mMicroOrm.toContentValues(item);
            cv.remove(ID_KEY);
            cvs.add(cv);
        }
        return mContext.getContentResolver().bulkInsert(getUri(), cvs.toArray(new ContentValues[cvs.size()]));
    }

    @Override
    public boolean update(T element) {
        ContentValues cv = mMicroOrm.toContentValues(element);
        return update(cv);
    }

    boolean update(ContentValues cv) {
        String selection = ID_KEY + " = ?";
        String[] selectionArgs = {Integer.toString(cv.getAsInteger(ID_KEY))};
        return mContext.getContentResolver().update(getUri(), cv, selection, selectionArgs) == 1;
    }
}
