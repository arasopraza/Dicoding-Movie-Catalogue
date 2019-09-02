package com.smk.informatics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smk.informatics.R;
import com.smk.informatics.model.Movie;
import com.smk.informatics.ui.activity.DetailMovieActivity;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder> {

    private final ArrayList<Movie> listMovieFavorite = new ArrayList<>();
    private final Activity activity;

    public FavoriteMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<Movie> getMovieFavorite() {
        return listMovieFavorite;
    }

    public void setListFavoriteMovie(ArrayList<Movie> listFavorite) {
        if (listFavorite.size() > 0) this.listMovieFavorite.clear();

        this.listMovieFavorite.addAll(listFavorite);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.tvName.setText(listMovieFavorite.get(position).getmName());
        holder.tvRelease.setText(listMovieFavorite.get(position).getmRelease());
        holder.tvDesc.setText(listMovieFavorite.get(position).getmDesc());
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w500/" + getMovieFavorite().get(position).getmPhoto())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_image_black_24dp))
                .into(holder.imageMovie);
    }

    @Override
    public int getItemCount() {
        return listMovieFavorite.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView tvName, tvRelease, tvDesc;
        final ImageView imageMovie;

        FavoriteViewHolder(@NonNull View itemView) {
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

                Intent movieIntent = new Intent(activity, DetailMovieActivity.class);
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, getMovieFavorite().get(position));
                activity.startActivity(movieIntent);
            }
        }
    }
}
