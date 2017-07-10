package com.ahmaddudayef.movieudacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmaddudayef.movieudacity.R;
import com.ahmaddudayef.movieudacity.activity.DetailActivity;
import com.ahmaddudayef.movieudacity.pojo.Result;
import com.squareup.picasso.Picasso;

/**
 * Created by Ahmad Dudayef on 11/30/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w342";


    java.util.List<Result> list;

    public MovieAdapter (java.util.List<Result> list){
        this.list = list;
    }

    public void updateList(java.util.List<Result> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Context context = holder.img_movie.getContext();
        holder.title_movie.setText(list.get(position).getTitle());
        Picasso.with(context)
                .load(POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + list.get(position).getPosterPath())
                .placeholder(R.drawable.movie_db)
                .error(R.drawable.movie_db)
                .into(holder.img_movie);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_detail = new Intent(v.getContext(), DetailActivity.class);
                intent_detail.putExtra("title", list.get(position).getTitle());
                intent_detail.putExtra("rating", String.valueOf(list.get(position).getVoteAverage()));
                intent_detail.putExtra("release_date", list.get(position).getReleaseDate());
                intent_detail.putExtra("description", list.get(position).getOverview());
                intent_detail.putExtra("img_backdrop", list.get(position).getBackdropPath());
                intent_detail.putExtra("img_movie", list.get(position).getPosterPath());
                intent_detail.putExtra("id_movie", String.valueOf(list.get(position).getId()));
                v.getContext().startActivity(intent_detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null != list){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_movie;
        TextView title_movie;
        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            img_movie = (ImageView) itemView.findViewById(R.id.image_movie);
            title_movie = (TextView) itemView.findViewById(R.id.title_movie);
        }
    }


}
