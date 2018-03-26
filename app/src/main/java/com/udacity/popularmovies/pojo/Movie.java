package com.udacity.popularmovies.pojo;

import android.os.*;

import com.google.gson.annotations.*;
import com.udacity.popularmovies.data.PopularMoviesContract.MovieEntry;

import org.chalup.microorm.annotations.Column;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by tomas on 26.02.2018.
 */

public class Movie extends Pojo implements Parcelable {

    @Column(MovieEntry.COLUMN_TITLE)
    @Expose
    @SerializedName(MovieEntry.COLUMN_TITLE)
    private String title;

    @Column(MovieEntry.COLUMN_RELEASE_DATE)
    @Expose
    @SerializedName(MovieEntry.COLUMN_RELEASE_DATE)
    private LocalDate releaseDate;

    @Column(MovieEntry.COLUMN_RUNTIME)
    @Expose
    @SerializedName(MovieEntry.COLUMN_RUNTIME)
    private int runtime;

    @Column(MovieEntry.COLUMN_OVERVIEW)
    @Expose
    @SerializedName(MovieEntry.COLUMN_OVERVIEW)
    private String overview;

    @Column(MovieEntry.COLUMN_MARKED_AS_FAVOURITE)
    private boolean markedAsFavourite;

    @Column(MovieEntry.COLUMN_VOTE_AVERAGE)
    @Expose
    @SerializedName(MovieEntry.COLUMN_VOTE_AVERAGE)
    private double voteAverage;

    @Column(MovieEntry.COLUMN_POPULARITY)
    @Expose
    @SerializedName(MovieEntry.COLUMN_POPULARITY)
    private double popularity;

    @Column(MovieEntry.COLUMN_POSTER_PATH)
    @Expose
    @SerializedName(MovieEntry.COLUMN_POSTER_PATH)
    private String posterPath;

    public Movie(int serverId, String title, LocalDate releaseDate, int runtime, String overview, boolean markedAsFavourite, double voteAverage, double popularity, String posterPath) {
        super(serverId);
        this.title = title;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.overview = overview;
        this.markedAsFavourite = markedAsFavourite;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
    }

    private Movie(Parcel in) {
        super(in.readInt());
        this._id = in.readInt();
        this.title = in.readString();
        this.releaseDate = LocalDate.parse(in.readString(), DateTimeFormatter.ISO_DATE);
        this.runtime = in.readInt();
        this.overview = in.readString();
        this.markedAsFavourite = in.readInt() == 1;
        this.voteAverage = in.readDouble();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serverId);
        dest.writeInt(_id);
        dest.writeString(title);
        dest.writeString(releaseDate.format(DateTimeFormatter.ISO_DATE));
        dest.writeInt(runtime);
        dest.writeString(overview);
        dest.writeInt(markedAsFavourite ? 1 : 0);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isMarkedAsFavourite() {
        return markedAsFavourite;
    }

    public void setMarkedAsFavourite(boolean markedAsFavourite) {
        this.markedAsFavourite = markedAsFavourite;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
