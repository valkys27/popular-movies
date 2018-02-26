package com.udacity.popularmovies.popularmovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by tomas on 26.02.2018.
 */

public interface TheMovieDbService {

    @GET("/3/movie/{category}/")
    Call<List<Movie>> getMovieList(@Path("category") String category, @Query("api_key") String key);

    @GET("/3/movie/{id}/")
    Call<Movie> findMovie(@Path("id") int id, @Query("api_key") String key);
}
