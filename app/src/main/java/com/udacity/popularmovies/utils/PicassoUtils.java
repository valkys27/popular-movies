package com.udacity.popularmovies.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.*;
import com.udacity.popularmovies.R;

/**
 * Created by tomas on 06.03.2018.
 */

public class PicassoUtils {

    private static final String BASE_URL = "https://image.tmdb.org/t/p/";

    public static void loadImage(final Context context, final String path, final ImageView iv) {
        loadImage(context, path, iv, -1, -1);
    }

    public static void loadImage(final Context context, final String path, final ImageView iv, final int width, final int height) {
        final String url = BASE_URL + path;
        RequestCreator creator = Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE);
        if (width != -1)
            creator.resize(width, height);
        creator.into(iv, new Callback() {
                    @Override
                    public void onSuccess() {}
                    @Override
                    public void onError() {
                        RequestCreator reCreator = Picasso.with(context)
                                .load(url);
                        if (width != -1)
                            reCreator.resize(width, height);
                        reCreator.error(R.drawable.ic_error)
                                .into(iv, new Callback() {
                                    @Override
                                    public void onSuccess() {}
                                    @Override
                                    public void onError() {
                                        Log.v("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });
    }
}
