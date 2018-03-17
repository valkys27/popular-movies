package com.udacity.popularmovies.dagger.components;

import com.udacity.popularmovies.dagger.modules.*;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.data.contentProviders.BaseContentProvider;
import com.udacity.popularmovies.presenters.*;

import dagger.Component;

/**
 * Created by tomas on 17.03.2018.
 */
@PerApplication
@Component(modules = {AppModule.class, DbModule.class, NetworkModule.class})
public interface AppComponent {
    void injectMainListPresenter(MainListPresenter presenter);
    void injectDetailPresenter(DetailPresenter presenter);
    void injectContentProvider(BaseContentProvider contentProvider);
}
