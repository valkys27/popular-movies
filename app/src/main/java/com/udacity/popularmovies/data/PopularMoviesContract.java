package com.udacity.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tomas on 28.02.2018.
 */

public class PopularMoviesContract {

    private static final String BASE_CONTENT_AUTHORITY = "com.udacity.popularmovies";
    private static final String SCHEME = "content";

    public static final class MovieEntry implements BaseColumns {
        public static final String CONTENT_AUTHORITY = BASE_CONTENT_AUTHORITY + ".movieprovider";
        public static final String PATH = "movie";
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + CONTENT_AUTHORITY).buildUpon()
                .appendPath(PATH)
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
        public static final String CONTENT_AUTHORITY = BASE_CONTENT_AUTHORITY + ".reviewprovider";
        public static final String PATH = "review";
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + CONTENT_AUTHORITY).buildUpon()
                .appendPath(PATH)
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
        public static final String CONTENT_AUTHORITY = BASE_CONTENT_AUTHORITY + ".trailerprovider";
        public static final String PATH = "trailer";
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + CONTENT_AUTHORITY).buildUpon()
                .appendPath(PATH)
                .build();

        public static final String TABLE_NAME = "trailers";

        public static final String COLUMN_TRAILER_ID = "id";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME = "name";
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
