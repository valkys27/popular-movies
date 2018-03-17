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

    @Provides
    @PerApplication
    public JsonDeserializer<LocalDate> providesLocalDateAdapter() {
        return new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String date = json.getAsString();
                return LocalDate.parse(date);
            }
        };
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
    public Gson providesGson(GsonBuilder baseGsonBuilder) {
        final Gson baseGson = baseGsonBuilder.create();
        return baseGsonBuilder
                .registerTypeAdapter(new TypeToken<List<Movie>>() {
                }.getType(), new JsonDeserializer<List<Movie>>() {
                    @Override
                    public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonArray array = json.getAsJsonObject().get(JSON_ARRAY_NAME).getAsJsonArray();
                        List<Movie> movies = new ArrayList<>();
                        for (int i = 0; i < MOVIE_COUNT; i++) {
                            JsonObject object = array.get(i).getAsJsonObject();
                            movies.add(baseGson.fromJson(object, Movie.class));
                        }
                        return movies;
                    }
                })
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
