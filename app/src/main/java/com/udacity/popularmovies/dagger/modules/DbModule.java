package com.udacity.popularmovies.dagger.modules;

import android.content.Context;

import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.data.*;
import com.udacity.popularmovies.data.dao.*;

import org.chalup.microorm.MicroOrm;
import org.threeten.bp.LocalDate;

import dagger.*;

/**
 * Created by tomas on 17.03.2018.
 */

@Module
public class DbModule {

    @Provides
    @PerApplication
    public MicroOrm providesMicroOrm() {
        return new MicroOrm.Builder()
                .registerTypeAdapter(LocalDate.class, new MicroOrmTypeAdapters.LocalDateAdapter())
                .build();
    }

    @Provides
    @PerApplication
    public SqlParser providesSqlParser(Context context) {
        return new SqlParser(context);
    }

    @Provides
    @PerApplication
    public MoviesDAO providesMoviesDAO(Context context, MicroOrm microOrm) {
        return new MoviesDAOImpl(context, microOrm);
    }

    @Provides
    @PerApplication
    public DbProvider providesDbHelper(Context context, SqlParser sqlParser) {
        return new DbHelper(context, sqlParser);
    }
}
