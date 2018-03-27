package com.udacity.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.*;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Movie;

public class DetailTrailersFragment extends Fragment {

    public static DetailTrailersFragment newInstance(Movie movie) {
        DetailTrailersFragment fragment = new DetailTrailersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_trailers, container, false);
    }
}
