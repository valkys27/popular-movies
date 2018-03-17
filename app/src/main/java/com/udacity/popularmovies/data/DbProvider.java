package com.udacity.popularmovies.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tomas on 28.02.2018.
 */

public interface DbProvider {
    SQLiteDatabase getWritableDatabase();
    SQLiteDatabase getReadableDatabase();
    void close();
}
