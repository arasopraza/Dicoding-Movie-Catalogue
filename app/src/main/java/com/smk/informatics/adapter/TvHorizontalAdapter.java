package com.smk.informatics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.smk.informatics.R;
import com.smk.informatics.model.Television;
import com.smk.informatics.ui.activity.DetailTelevisionActivity;

import java.util.ArrayList;

public class TvHorizontalAdapter extends RecyclerView.Adapter<TvHorizontalAdapter.MovieViewHolder> {
    private final Context context;
    private ArrayList<Television> listTelevision;

    public TvHorizontalAdapter(Context context) {
        this.context = context;
        listTelevision = new ArrayList<>();
    }

    private ArrayList<Television> getListTelevision() {
        return listTelevision;
    }

    public void setListTelevision(ArrayList<Television> listTelevision) {
        this.listTelevision = listTelevision;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie_horizontal, parent, false);
        return new MovieViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + getListTelevision().get(position).getmPhoto())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .transform(new RoundedCorners(16)))
                .into(holder.imageTv);
    }

    @Override
    public int getItemCount() {
        return getListTelevision().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageTv;

        MovieViewHolder(View itemView) {
            super(itemView);
            imageTv = itemView.findViewById(R.id.image_item_photo);
            itemView.setOnClickListener(this);
        }

        //
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent televisionIntent = new Intent(context, DetailTelevisionActivity.class);
                televisionIntent.putExtra(DetailTelevisionActivity.EXTRA_TV, getListTelevision().get(position));
                context.startActivity(televisionIntent);
            }
        }
    }
}
