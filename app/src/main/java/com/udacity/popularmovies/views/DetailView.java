package com.udacity.popularmovies.views;

import android.support.design.widget.BottomNavigationView;

import com.udacity.popularmovies.fragments.DetailInfoFragment;
import com.udacity.popularmovies.fragments.DetailReviewsFragment;
import com.udacity.popularmovies.fragments.DetailTrailersFragment;
import com.udacity.popularmovies.pojo.Movie;

/**
 * Created by tomas on 05.03.2018.
 */

public interface DetailView extends BaseView, DetailInfoFragment.OnDetailInfoFragmentListener,
        BottomNavigationView.OnNavigationItemSelectedListener, DetailTrailersFragment.OnDetailTrailersFragmentListener,
        DetailReviewsFragment.OnDetailReviewsFragmentListener {
    void setData(int menuItemId);
    void setMovieTitle(String title);
}
