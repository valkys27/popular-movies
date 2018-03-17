package com.udacity.popularmovies.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Movie;
import com.udacity.popularmovies.utils.PicassoUtils;

import java.util.*;

import butterknife.*;

/**
 * Created by tomas on 28.02.2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final int ITEM_LAYOUT_ID = com.udacity.popularmovies.R.layout.list_movie_item;
    private final OnMovieAdapterClickHandler mClickHandler;
    private final Context mContext;

    private List<Movie> movies;

    public interface OnMovieAdapterClickHandler {
        void onPosterClick(int _id, int serverId);
    }

    public MovieAdapter(@NonNull Context context, OnMovieAdapterClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        movies = new ArrayList<>();
    }

    public void setData(List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(ITEM_LAYOUT_ID, parent, false);
        view.setFocusable(true);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        holder.poster.setTag(movie);
        Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x/ 2;
        int height = (int) (width * 1.5);
        int sourceWidth = (width <= 185) ? 185 : (width <= 342) ? 342 : 500;
        String url = String.format("w%d" + movie.getPosterPath(), sourceWidth);
        holder.onBindViewHolder(url, width, height);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_ib) ImageButton poster;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = (Movie) v.getTag();
            mClickHandler.onPosterClick(movie.get_id(), movie.getServerId());
        }

        public void onBindViewHolder(final String url, final int width, final int height) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.setMargins(0,0, 0, 0);
            itemView.setLayoutParams(params);
            PicassoUtils.loadImage(mContext, url, poster, width, height);
        }
    }
}
