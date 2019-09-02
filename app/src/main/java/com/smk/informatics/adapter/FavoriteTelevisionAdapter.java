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
import com.smk.informatics.model.Television;
import com.smk.informatics.ui.activity.DetailTelevisionActivity;

import java.util.ArrayList;

public class FavoriteTelevisionAdapter extends RecyclerView.Adapter<FavoriteTelevisionAdapter.FavoriteViewHolder> {
    private final ArrayList<Television> listTelevisionFavorite = new ArrayList<>();
    private final Activity activity;

    public FavoriteTelevisionAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<Television> getTelevisionFavorite() {
        return listTelevisionFavorite;
    }

    public void setListFavoriteTelevision(ArrayList<Television> listFavoriteTelevision) {
        if (listFavoriteTelevision.size() > 0) this.listTelevisionFavorite.clear();
        this.listTelevisionFavorite.addAll(listFavoriteTelevision);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTelevisionAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new FavoriteTelevisionAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTelevisionAdapter.FavoriteViewHolder holder, int position) {
        holder.tvName.setText(listTelevisionFavorite.get(position).getmName());
        holder.tvRelease.setText(listTelevisionFavorite.get(position).getmRelease());
        holder.tvDesc.setText(listTelevisionFavorite.get(position).getmDesc());
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w500/" + getTelevisionFavorite().get(position).getmPhoto())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_image_black_24dp))
                .into(holder.imageTv);

    }

    @Override
    public int getItemCount() {
        return listTelevisionFavorite.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvName, tvRelease, tvDesc;
        final ImageView imageTv;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.text_item_name);
            tvRelease = itemView.findViewById(R.id.text_item_release);
            tvDesc = itemView.findViewById(R.id.text_item_desc);
            imageTv = itemView.findViewById(R.id.image_item_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent tvIntent = new Intent(activity, DetailTelevisionActivity.class);
                tvIntent.putExtra(DetailTelevisionActivity.EXTRA_TV, getTelevisionFavorite().get(position));
                activity.startActivity(tvIntent);
            }
        }
    }
}
