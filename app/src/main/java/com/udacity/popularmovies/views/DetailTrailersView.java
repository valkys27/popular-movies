package com.udacity.popularmovies.views;

import com.udacity.popularmovies.pojo.Trailer;

import java.util.List;

public interface DetailTrailersView extends BaseView {
    void setData(List<Trailer> trailers);
    void setLoaded();
}
