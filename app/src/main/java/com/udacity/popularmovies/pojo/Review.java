package com.udacity.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.udacity.popularmovies.data.PopularMoviesContract.ReviewEntry;
import org.chalup.microorm.annotations.Column;

/**
 * Created by tomas on 28.02.2018.
 */

public class Review extends Pojo implements Parcelable {

    @Column(ReviewEntry.COLUMN_MOVIE_ID)
    @Expose
    private int movieId;

    @Column(ReviewEntry.COLUMN_AUTHOR)
    @Expose
    private String author;

    @Column(ReviewEntry.COLUMN_CONTENT)
    @Expose
    private String content;

    public Review(String serverId, int movieId, String author, String content) {
        super(serverId);
        this.movieId = movieId;
        this.author = author;
        this.content = content;
    }

    private Review(Parcel in) {
        super(in);
        this.movieId = in.readInt();
        this.author = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR
            = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(movieId);
        dest.writeString(author);
        dest.writeString(content);
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
