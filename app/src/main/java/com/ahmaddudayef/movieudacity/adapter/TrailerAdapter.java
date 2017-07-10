package com.ahmaddudayef.movieudacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmaddudayef.movieudacity.R;
import com.ahmaddudayef.movieudacity.pojo.Trailer;

/**
 * Created by Ahmad Dudayef on 12/5/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    private static final String YOUTUBE_LINK = "http://www.youtube.com/watch?v=";
    java.util.List<Trailer> list_trailer;

    public TrailerAdapter (java.util.List<Trailer> list_trailer){
        this.list_trailer = list_trailer;
    }


    public void updateListTrailer(java.util.List<Trailer> list_trailer){
        this.list_trailer = list_trailer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.activity_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(list_trailer.get(position).getName());
        holder.site.setText(list_trailer.get(position).getSite());
        holder.type.setText(list_trailer.get(position).getType());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_link = new Intent(Intent.ACTION_VIEW);
                open_link.setData(Uri.parse(YOUTUBE_LINK + list_trailer.get(position).getKey()));
                v.getContext().startActivity(open_link);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null != list_trailer){
            return list_trailer.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView site;
        TextView type;
        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            name = (TextView) itemView.findViewById(R.id.name_trailer);
            site = (TextView) itemView.findViewById(R.id.site_trailer);
            type = (TextView) itemView.findViewById(R.id.type_trailer);
        }
    }
}
