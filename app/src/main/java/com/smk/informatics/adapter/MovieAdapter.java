package com.smk.informatics.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.smk.informatics.ui.activity.DetailMovieActivity;
import com.smk.informatics.R;
import com.smk.informatics.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private ArrayList<Movie> listMovie;

    public MovieAdapter(Context context) {
        this.context = context;
        listMovie = new ArrayList<>();
    }

    private ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new MovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        holder.tvDesc.setText(getListMovie().get(position).getmDesc());
        holder.tvRelease.setText(getListMovie().get(position).getmRelease());
        holder.tvName.setText(getListMovie().get(position).getmName());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + getListMovie().get(position).getmPhoto())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .transform(new RoundedCorners(16)))
                .into(holder.imageMovie);
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView tvName;
        final TextView tvRelease;
        final TextView tvDesc;
        final ImageView imageMovie;

        MovieViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.text_item_name);
            tvRelease = itemView.findViewById(R.id.text_item_release);
            tvDesc = itemView.findViewById(R.id.text_item_desc);
            imageMovie = itemView.findViewById(R.id.image_item_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent movieIntent = new Intent(context, DetailMovieActivity.class);
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, getListMovie().get(position));
                context.startActivity(movieIntent);
            }
        }
    }
}
