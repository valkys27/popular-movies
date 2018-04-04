package com.udacity.popularmovies.network;

import com.udacity.popularmovies.pojo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by tomas on 26.02.2018.
 */

public interface TheMovieDbService {

    @GET("/3/movie/{category}")
    Call<List<Movie>> getMovieList(@Path("category") String category, @Query("api_key") String key);

    @GET("/3/movie/{id}")
    Call<Movie> findMovieById(@Path("id") String id, @Query("api_key") String key);

    @GET("/3/movie/{movie_id}/videos")
    Call<List<Trailer>> getTrailerList(@Path("movie_id") String movie_id, @Query("api_key") String key);

    @GET("/3/movie/{movie_id}/reviews")
    Call<List<Review>> getReviewList(@Path("movie_id") String movie_id, @Query("api_key") String key);
}
