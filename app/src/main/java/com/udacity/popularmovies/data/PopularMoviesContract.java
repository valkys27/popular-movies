package com.udacity.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tomas on 28.02.2018.
 */

public class PopularMoviesContract {

    public static final String CONTENT_AUTHORITY = "com.udacity.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_REVIEW = "review";
    public static final String PATH_TRAILER = "trailer";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_MARKED_AS_FAVOURITE = "marked_as_favourite";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static Uri buildMovieUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
    }

    public static final class ReviewEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_REVIEW)
                .build();

        public static final String TABLE_NAME = "reviews";

        public static final String COLUMN_REVIEW_ID = "id";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";

        public static Uri buildReviewUriWithMovieId(int movieId) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(movieId))
                    .build();
        }

        public static String getSqlSelectForMovieOnwards(int movieId) {
            return COLUMN_MOVIE_ID + " = " + movieId;
        }
    }

    public static final class TrailerEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TRAILER)
                .build();

        public static final String TABLE_NAME = "trailers";

        public static final String COLUMN_TRAILER_ID = "id";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_KEY = "key";

        public static Uri buildTrailerUriWithMovieId(int movieId) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(movieId))
                    .build();
        }

        public static String getSqlSelectForMovieOnwards(int movieId) {
            return COLUMN_MOVIE_ID + " = " + movieId;
        }
    }
}
