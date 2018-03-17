package com.udacity.popularmovies.dagger.modules;

import android.content.Context;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.dagger.scope.PerApplication;

import dagger.*;

/**
 * Created by tomas on 17.03.2018.
 */

@Module
public class AppModule {

    private final PopularMoviesApp app;

    public AppModule(PopularMoviesApp app) {
        this.app = app;
    }

    @Provides @PerApplication
    public PopularMoviesApp providesApp() {
        return app;
    }

    @Provides @PerApplication
    public Context providesAppContext() {
        return app.getApplicationContext();
    }
}
