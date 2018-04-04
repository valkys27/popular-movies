package com.udacity.popularmovies.network;

import android.support.annotation.NonNull;
import com.udacity.popularmovies.*;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.pojo.*;
import okhttp3.OkHttpClient;
import retrofit2.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by tomas on 26.02.2018.
 */

@PerApplication
public class Network {

    private final TheMovieDbService service;
    private final OkHttpClient client;

    @Inject
    public Network(TheMovieDbService service, OkHttpClient client) {
        this.service = service;
        this.client = client;
    }

    public void getMovieListByCategory(Category category, final OnLoadingHandler<List<Movie>> handler) {
        Call<List<Movie>> repos = service.getMovieList(category.name().toLowerCase(), BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public void findMovieById(String id, final OnLoadingHandler<Movie> handler) {
        Call<Movie> repos = service.findMovieById(id, BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public void getTrailersByMovie(String movieId, final OnLoadingHandler<List<Trailer>> handler) {
        Call<List<Trailer>> repos = service.getTrailerList(movieId, BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public void getReviewsByMovie(String movieId, final OnLoadingHandler<List<Review>> handler) {
        Call<List<Review>> repos = service.getReviewList(movieId, BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public void cancelAll() {
        client.dispatcher().cancelAll();
    }

    public interface OnLoadingHandler<T> {
        void onLoadingSuccess(T data);
        void onLoadingFailure(String errorMessage);
    }

    private static class NetworkCallback<T> implements Callback<T> {

        private OnLoadingHandler<T> handler;

        NetworkCallback(OnLoadingHandler<T> handler) {
            this.handler = handler;
        }

        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
            if (response.isSuccessful()) {
                handler.onLoadingSuccess(response.body());
            } else {
                try {
                    handler.onLoadingFailure(response.errorBody().string());
                } catch (IOException e) {
                    throw new RuntimeException("Unknown error");
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            handler.onLoadingFailure(t.getMessage());
        }
    }
}