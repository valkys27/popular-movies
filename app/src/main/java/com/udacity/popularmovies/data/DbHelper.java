package com.udacity.popularmovies.data;

import android.content.Context;
import android.database.sqlite.*;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.dagger.scope.PerApplication;

import javax.inject.Inject;

/**
 * Created by tomas on 28.02.2018.
 */
@PerApplication
public class DbHelper extends SQLiteOpenHelper implements DbProvider {

    public static final String DATABASE_NAME = "popular_movies.db";

    private static final int DATABASE_VERSION = 1;
    private static final int CREATE_SCRIPT_ID = R.raw.create_db;
    private static final int DROP_SCRIPT_ID = R.raw.drop_db;

    private final SqlParser mSqlParser;

    @Inject
    public DbHelper(Context context, SqlParser sqlParser) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mSqlParser = sqlParser;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        execSqlScript(CREATE_SCRIPT_ID, db);
    }

    private void execSqlScript(int scriptName, SQLiteDatabase db) {
        for (String statement : mSqlParser.parseSqlFileToStatementArray(scriptName))
            db.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        execSqlScript(DROP_SCRIPT_ID, db);
        onCreate(db);
    }
}
