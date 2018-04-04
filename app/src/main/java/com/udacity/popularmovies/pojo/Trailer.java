package com.udacity.popularmovies.pojo;

import android.os.*;
import com.google.gson.annotations.Expose;
import com.udacity.popularmovies.data.PopularMoviesContract.TrailerEntry;
import org.chalup.microorm.annotations.Column;

/**
 * Created by tomas on 28.02.2018.
 */

public class Trailer extends Pojo implements Parcelable {

    @Column(TrailerEntry.COLUMN_MOVIE_ID)
    @Expose
    private int movieId;

    @Column(TrailerEntry.COLUMN_NAME)
    @Expose
    private String name;

    @Column(TrailerEntry.COLUMN_KEY)
    @Expose
    private String key;

    public Trailer(String serverId, int movieId, String name, String key) {
        super(serverId);
        this.movieId = movieId;
        this.name = name;
        this.key = key;
    }

    private Trailer(Parcel in) {
        super(in);
        this.movieId = in.readInt();
        this.name = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR
            = new Parcelable.Creator<Trailer>() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(movieId);
        dest.writeString(name);
        dest.writeString(key);
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
