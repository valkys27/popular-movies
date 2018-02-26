package com.udacity.popularmovies.popularmovies;

import java.io.IOException;
import java.util.List;

import retrofit2.*;

/**
 * Created by tomas on 26.02.2018.
 */

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();
    private static TheMovieDbService service = retrofit.create(TheMovieDbService.class);

    public static List<Movie> getMovieListBy(Category category) {
        Call<List<Movie>> repos = service.getMovieList(category.name().toLowerCase(), BuildConfig.API_KEY);
        try {
            return repos.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Movie findMovieBy(int id) {
        Call<Movie> repos = service.findMovie(id, BuildConfig.API_KEY);
        try {
            return repos.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
