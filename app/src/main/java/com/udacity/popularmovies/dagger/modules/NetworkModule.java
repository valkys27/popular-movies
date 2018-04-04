package com.udacity.popularmovies.dagger.modules;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.network.*;
import com.udacity.popularmovies.pojo.Movie;

import com.udacity.popularmovies.pojo.Review;
import com.udacity.popularmovies.pojo.Trailer;
import org.threeten.bp.LocalDate;

import java.lang.reflect.Type;
import java.util.*;

import dagger.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tomas on 17.03.2018.
 */

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://api.themoviedb.org/";

    @Provides
    @PerApplication
    public JsonDeserializer<LocalDate> providesLocalDateJsonDeserializer() {
        return new JsonDeserializers.LocalDateDeserializer();
    }

    @Provides @PerApplication
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides @PerApplication
    public GsonBuilder providesBaseGsonBuilder(JsonDeserializer<LocalDate> localDateJsonDeserializer) {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, localDateJsonDeserializer);
    }

    @Provides @PerApplication
    public JsonDeserializer<List<Movie>> providesMovieListJsonDeserializer() {
        return new JsonDeserializers.MovieListJsonDeserializer();
    }

    @Provides @PerApplication
    public JsonDeserializer<List<Trailer>> providesTrailerListJsonDeserializer() {
        return new JsonDeserializers.TrailerListJsonDeserializer();
    }

    @Provides @PerApplication
    public JsonDeserializer<List<Review>> providesReviewListJsonDeserializer() {
        return new JsonDeserializers.ReviewListJsonDeserializer();
    }

    @Provides @PerApplication
    public Gson providesGson(GsonBuilder baseGsonBuilder, JsonDeserializer<List<Movie>> movieListDeserializer,
                             JsonDeserializer<List<Trailer>> trailerListDeserializer, JsonDeserializer<List<Review>> reviewListDeserializer) {
        return baseGsonBuilder
                .registerTypeAdapter(new TypeToken<List<Movie>>() {}.getType(), movieListDeserializer)
                .registerTypeAdapter(new TypeToken<List<Trailer>>() {}.getType(), trailerListDeserializer)
                .registerTypeAdapter(new TypeToken<List<Review>>() {}.getType(), reviewListDeserializer)
                .create();
    }

    @Provides @PerApplication
    public TheMovieDbService providesTheMovieDbService(Retrofit retrofit) {
        return retrofit.create(TheMovieDbService.class);
    }

    @Provides @PerApplication
    public Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}
