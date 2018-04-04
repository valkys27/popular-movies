package com.udacity.popularmovies.network;

import com.google.gson.*;
import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.pojo.*;
import org.threeten.bp.LocalDate;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.*;

public class JsonDeserializers {

    private static final String JSON_ARRAY_NAME = "results";

    public static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String date = json.getAsString();
            return LocalDate.parse(date);
        }
    }

    public static class MovieListJsonDeserializer implements JsonDeserializer<List<Movie>> {

        private static final int MOVIE_COUNT = 20;

        @Inject GsonBuilder baseGsonBuilder;

        public MovieListJsonDeserializer() {
            PopularMoviesApp.getAppComponent().injectMovieListJsonDeserializer(this);
        }

        @Override
        public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray array = json.getAsJsonObject().get(JSON_ARRAY_NAME).getAsJsonArray();
            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < MOVIE_COUNT; i++) {
                JsonObject object = array.get(i).getAsJsonObject();
                movies.add(baseGsonBuilder.create().fromJson(object, Movie.class));
            }
            return movies;
        }
    }

    public static class TrailerListJsonDeserializer implements JsonDeserializer<List<Trailer>> {

        @Inject GsonBuilder baseGsonBuilder;

        public TrailerListJsonDeserializer() {
            PopularMoviesApp.getAppComponent().injectTrailerListJsonDeserializer(this);
        }

        @Override
        public List<Trailer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray array = json.getAsJsonObject().get(JSON_ARRAY_NAME).getAsJsonArray();
            List<Trailer> trailers = new ArrayList<>();
            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                trailers.add(baseGsonBuilder.create().fromJson(object, Trailer.class));
            }
            return trailers;
        }
    }

    public static class ReviewListJsonDeserializer implements JsonDeserializer<List<Review>> {

        @Inject GsonBuilder baseGsonBuilder;

        public ReviewListJsonDeserializer() {
            PopularMoviesApp.getAppComponent().injectReviewListJsonDeserializer(this);
        }

        @Override
        public List<Review> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray array = json.getAsJsonObject().get(JSON_ARRAY_NAME).getAsJsonArray();
            List<Review> reviews = new ArrayList<>();
            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                reviews.add(baseGsonBuilder.create().fromJson(object, Review.class));
            }
            return reviews;
        }
    }
}