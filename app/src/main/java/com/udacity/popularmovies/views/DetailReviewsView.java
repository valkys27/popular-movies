package com.udacity.popularmovies.views;

import com.udacity.popularmovies.pojo.Review;

import java.util.List;

public interface DetailReviewsView extends BaseView {
    void setData(List<Review> reviews);
    void setLoaded();
}
