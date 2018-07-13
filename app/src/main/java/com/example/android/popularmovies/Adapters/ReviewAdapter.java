package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.Models.Reviews;
import com.example.android.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Reviews> rData;
    private LayoutInflater mLayoutInflater;
    private Context rContext;

    public ReviewAdapter(Context context) {
        this.rContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.rData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.user_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reviews reviews = rData.get(position);

        String reviewAuthor = reviews.getReviewAuthor();

        String reviewContent = reviews.getReviewContent();

        if (rData == null) {
            holder.reviewAuthor.setText("No Review Available");
            holder.reviewContent.setText("");
        }
        holder.reviewAuthor.setText(reviewAuthor);
        holder.reviewContent.setText(reviewContent);

    }

    @Override
    public int getItemCount() {
        return (rData == null) ? 0 : rData.size();
    }

    public void setReviewsList(List<Reviews> reviewList) {
        this.rData.clear();
        this.rData.addAll(reviewList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthor;
        TextView reviewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = (TextView) itemView.findViewById(R.id.reviewAuthor);
            reviewContent = (TextView) itemView.findViewById(R.id.review_content);
        }

    }

}


