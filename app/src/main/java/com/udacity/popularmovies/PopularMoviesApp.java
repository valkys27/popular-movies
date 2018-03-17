package com.udacity.popularmovies;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.picasso.*;
import com.udacity.popularmovies.dagger.components.AppComponent;
import com.udacity.popularmovies.dagger.components.DaggerAppComponent;
import com.udacity.popularmovies.dagger.modules.AppModule;
import com.udacity.popularmovies.dagger.modules.DbModule;

/**
 * Created by tomas on 01.03.2018.
 */

public class PopularMoviesApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        AndroidThreeTen.init(this);
        super.onCreate();
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(this,Integer.MAX_VALUE))
                .build();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        Picasso.setSingletonInstance(picasso);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        setAppComponent();
    }

    private void setAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
