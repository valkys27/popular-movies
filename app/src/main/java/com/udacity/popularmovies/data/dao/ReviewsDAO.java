package com.udacity.popularmovies.data.dao;

import com.udacity.popularmovies.pojo.*;

import java.util.List;

public interface ReviewsDAO extends DAO<Review> {
    List<Review> getList(Movie movie);
}
