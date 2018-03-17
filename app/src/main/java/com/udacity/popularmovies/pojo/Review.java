package com.udacity.popularmovies.pojo;

/**
 * Created by tomas on 28.02.2018.
 */

public class Review extends Pojo {

    private int movieId;
    private String author;
    private String content;

    public Review(int serverId, int movieId, String author, String content) {
        super(serverId);
        this.movieId = movieId;
        this.author = author;
        this.content = content;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
