package com.udacity.popularmovies.pojo;

/**
 * Created by tomas on 28.02.2018.
 */

public class Trailer extends Pojo {
    private int movieId;
    private String key;

    public Trailer(int serverId, int movieId, String key) {
        super(serverId);
        this.movieId = movieId;
        this.key = key;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
