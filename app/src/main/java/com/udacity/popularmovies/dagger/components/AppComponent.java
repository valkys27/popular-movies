package com.udacity.popularmovies.dagger.components;

import com.udacity.popularmovies.dagger.modules.*;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.data.contentProviders.BaseContentProvider;
import com.udacity.popularmovies.network.JsonDeserializers;
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
    void injectDetailInfoPresenter(DetailInfoPresenter presenter);
    void injectDetailTrailersPresenter(DetailTrailersPresenter presenter);
    void injectDetailReviewsPresenter(DetailReviewsPresenter presenter);
    void injectContentProvider(BaseContentProvider contentProvider);
    void injectMovieListJsonDeserializer(JsonDeserializers.MovieListJsonDeserializer jsonDeserializer);
    void injectTrailerListJsonDeserializer(JsonDeserializers.TrailerListJsonDeserializer jsonDeserializer);
    void injectReviewListJsonDeserializer(JsonDeserializers.ReviewListJsonDeserializer jsonDeserializer);
}
