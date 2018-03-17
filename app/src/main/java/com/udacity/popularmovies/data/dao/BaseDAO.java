package com.udacity.popularmovies.data.dao;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.popularmovies.data.MicroOrmTypeAdapters;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.pojo.Pojo;
import com.udacity.popularmovies.data.PopularMoviesContract;

import org.chalup.microorm.MicroOrm;
import org.threeten.bp.LocalDate;

import java.util.List;

/**
 * Created by tomas on 28.02.2018.
 */

public abstract class BaseDAO<T extends Pojo> implements DAO<T> {

    private static final String ID_KEY = "_id";

    private Context mContext;

    public BaseDAO(Context context) {
        mContext = context;
    }

    abstract protected String getPath();
    abstract protected Class<T> getEntityClass();

    @Override
    public List<T> getList(String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = mContext.getContentResolver().query(
                getUri(),
                null,
                selection,
                selectionArgs,
                sortOrder
        );
        MicroOrm microOrm = new MicroOrm.Builder()
                .registerTypeAdapter(LocalDate.class, new MicroOrmTypeAdapters.LocalDateAdapter())
                .build();
        return microOrm.listFromCursor(cursor, getEntityClass());
    }

    private Uri getUri() {
        return Uri.parse("content://" + PopularMoviesContract.CONTENT_AUTHORITY + "/" + getPath());
    }

    @Override
    public T findById(int id) {
        String selection = ID_KEY + " = ?";
        String[] selectionArgs = {Integer.toString(id)};
        Cursor cursor = mContext.getContentResolver().query(getUri(), null, selection, selectionArgs, null);
        MicroOrm microOrm = new MicroOrm.Builder()
                .registerTypeAdapter(LocalDate.class, new MicroOrmTypeAdapters.LocalDateAdapter())
                .build();
        return (cursor.getCount() == 0) ? null : microOrm.fromCursor(cursor, getEntityClass());
    }

    @Override
    public int insert(List<T> list) {
        MicroOrm microOrm = new MicroOrm.Builder()
                .registerTypeAdapter(LocalDate.class, new MicroOrmTypeAdapters.LocalDateAdapter())
                .build();
        ContentValues[] cv = new ContentValues[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cv[i] = microOrm.toContentValues(list.get(i));
            cv[i].remove(ID_KEY);
        }
        return mContext.getContentResolver().bulkInsert(getUri(), cv);
    }

    @Override
    public boolean update(T element) {
        MicroOrm microOrm = new MicroOrm.Builder()
                .registerTypeAdapter(LocalDate.class, new MicroOrmTypeAdapters.LocalDateAdapter())
                .build();
        ContentValues cv = microOrm.toContentValues(element);
        String selection = ID_KEY + " = ?";
        String[] selectionArgs = {Integer.toString(element.get_id())};
        return mContext.getContentResolver().update(getUri(), cv, selection, selectionArgs) == 1;
    }
}
