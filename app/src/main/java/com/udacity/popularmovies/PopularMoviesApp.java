package com.udacity.popularmovies;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.picasso.*;

/**
 * Created by tomas on 01.03.2018.
 */

public class PopularMoviesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(this,Integer.MAX_VALUE))
                .build();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        Picasso.setSingletonInstance(picasso);
    }
}
