package com.udacity.popularmovies.views;

import android.support.design.widget.BottomNavigationView;

import com.udacity.popularmovies.fragments.DetailInfoFragment;

/**
 * Created by tomas on 05.03.2018.
 */

public interface DetailView extends BaseView, DetailInfoFragment.OnDetailInfoFragmentListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    void setData(int menuItemId);
}
