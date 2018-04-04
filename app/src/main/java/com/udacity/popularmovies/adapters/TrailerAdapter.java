package com.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import butterknife.*;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Trailer;

import java.util.*;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private static final int ITEM_LAYOUT_ID = R.layout.list_trailer_item;
    private final OnTrailerAdapterClickHandler mClickHandler;
    private final Context mContext;

    private List<Trailer> mData;

    public interface OnTrailerAdapterClickHandler {
        void onTrailerClick(Trailer trailer);
    }

    public TrailerAdapter(@NonNull Context context, OnTrailerAdapterClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        mData = new ArrayList<>();
    }

    public void setData(List<Trailer> data) {
        mData = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(ITEM_LAYOUT_ID, parent, false);
        view.setFocusable(true);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        final Trailer trailer = mData.get(position);
        holder.itemView.setTag(trailer);
        holder.name.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailerName_tv) TextView name;

        TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onTrailerClick((Trailer)v.getTag());
        }
    }
}