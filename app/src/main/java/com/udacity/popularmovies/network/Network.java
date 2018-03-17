package com.udacity.popularmovies.network;

import com.udacity.popularmovies.*;
import com.udacity.popularmovies.dagger.scope.PerApplication;
import com.udacity.popularmovies.pojo.Movie;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.*;

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

    public void getMovieListBy(Category category, final OnLoadingHandler<List<Movie>> handler) {
        Call<List<Movie>> repos = service.getMovieList(category.name().toLowerCase(), BuildConfig.API_KEY);
        repos.enqueue(new NetworkCallback<>(handler));
    }

    public void findMovieById(int id, final OnLoadingHandler<Movie> handler) {
        Call<Movie> repos = service.findMovieById(id, BuildConfig.API_KEY);
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