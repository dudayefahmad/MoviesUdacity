package com.ahmaddudayef.movieudacity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmaddudayef.movieudacity.R;
import com.ahmaddudayef.movieudacity.pojo.Review;

/**
 * Created by Ahmad Dudayef on 12/5/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    java.util.List<Review> list_review;

    public ReviewAdapter (java.util.List<Review> list_review){
        this.list_review = list_review;
    }

    public void updateListReview(java.util.List<Review> list_review){
        this.list_review = list_review;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.activity_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        holder.author.setText(list_review.get(position).getAuthor());
        holder.content.setText(list_review.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (null != list_review){
            return list_review.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
