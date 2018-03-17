package com.udacity.popularmovies.dagger.modules;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.network.TheMovieDbService;
import com.udacity.popularmovies.pojo.Movie;

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

    private static final int MOVIE_COUNT = 20;
    private static final String JSON_ARRAY_NAME = "results";
    private static final String BASE_URL = "https://api.themoviedb.org/";

    @Provides @PerApplication
    public TheMovieDbService providesTheMovieDbService() {
        return providesRetrofit().create(TheMovieDbService.class);
    }

    @Provides @PerApplication
    private Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(providesGson()))
                .client(providesOkHttpClient())
                .build();
    }

    @Provides @PerApplication
    private Gson providesGson() {
        return providesBaseGsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Movie>>() {
                }.getType(), new JsonDeserializer<List<Movie>>() {
                    @Override
                    public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonArray array = json.getAsJsonObject().get(JSON_ARRAY_NAME).getAsJsonArray();
                        List<Movie> movies = new ArrayList<>();
                        for (int i = 0; i < MOVIE_COUNT; i++) {
                            JsonObject object = array.get(i).getAsJsonObject();
                            movies.add(providesBaseGson().fromJson(object, Movie.class));
                        }
                        return movies;
                    }
                })
                .create();
    }

    @Provides @PerApplication
    private GsonBuilder providesBaseGsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, providesLocalDateAdapter());
    }

    @Provides
    @PerApplication
    private JsonDeserializer<LocalDate> providesLocalDateAdapter() {
        return new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String date = json.getAsString();
                return LocalDate.parse(date);
            }
        };
    }

    @Provides @PerApplication
    private Gson providesBaseGson() {
        return providesBaseGsonBuilder().create();
    }

    @Provides @PerApplication
    private OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }
}
