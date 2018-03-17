package com.udacity.popularmovies.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.udacity.popularmovies.*;
import com.udacity.popularmovies.pojo.Movie;

import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tomas on 26.02.2018.
 */

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final OkHttpClient client = new OkHttpClient();
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<List<Movie>>() {
            }.getType(), new JsonDeserializer<List<Movie>>() {
                @Override
                public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    JsonArray array = json.getAsJsonObject().get("results").getAsJsonArray();
                    List<Movie> movies = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        JsonObject object = array.get(i).getAsJsonObject();
                        Gson gson1 = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                                    @Override
                                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                        String date = json.getAsString();
                                        return LocalDate.parse(date);
                                    }
                                })
                                .create();
                        movies.add(gson1.fromJson(object, Movie.class));
                    }
                    return movies;
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    String date = json.getAsString();
                    return LocalDate.parse(date);
                }
            })
            .create();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build();
    private static TheMovieDbService service = retrofit.create(TheMovieDbService.class);

    public static void getMovieListBy(Category category, final OnLoadingHandler<List<Movie>> handler) {
        Call<List<Movie>> repos = service.getMovieList(category.name().toLowerCase(), BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public static void findMovieById(int id, final OnLoadingHandler<Movie> handler) {
        Call<Movie> repos = service.findMovieById(id, BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public static void cancelAll() {
        client.dispatcher().cancelAll();
    }

    public interface OnLoadingHandler<T> {
        void onLoadingSuccess(T data);
        void onLoadingFailure(String errorMessage);
    }

    private static class NetworkCallback<T> implements Callback<T> {

        private OnLoadingHandler<T> handler;

        public NetworkCallback(OnLoadingHandler<T> handler) {
            this.handler = handler;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                handler.onLoadingSuccess(response.body());
            } else {
                try {
                    handler.onLoadingFailure(response.errorBody().string());
                } catch (IOException e) {
                    throw new RuntimeException("Uknown error");
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            handler.onLoadingFailure(t.getMessage());
        }
    }
}