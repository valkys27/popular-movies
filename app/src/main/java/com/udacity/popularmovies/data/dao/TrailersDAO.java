package com.udacity.popularmovies.data.dao;

import com.udacity.popularmovies.pojo.*;

import java.util.List;

public interface TrailersDAO extends DAO<Trailer> {
    List<Trailer> getList(Movie movie);
}
