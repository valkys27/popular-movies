package com.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import butterknife.*;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.pojo.Review;

import java.util.*;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private static final int ITEM_LAYOUT_ID = R.layout.list_review_item;
    private final Context mContext;

    private List<Review> mData;

    public ReviewAdapter(@NonNull Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<Review> data) {
        mData = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(ITEM_LAYOUT_ID, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        final Review review = mData.get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_tv) TextView author;
        @BindView(R.id.content_tv) TextView content;

        ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}