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
import com.smk.informatics.ui.activity.DetailTelevisionActivity;
import com.smk.informatics.R;
import com.smk.informatics.model.Television;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private final Context context;
    private ArrayList<Television> listTelevision;

    public TvAdapter(Context context) {
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
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new TvViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.tvName.setText(getListTelevision().get(position).getmName());
        holder.tvRelease.setText(getListTelevision().get(position).getmRelease());
        holder.tvDesc.setText(getListTelevision().get(position).getmDesc());
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

    class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView tvName;
        final TextView tvRelease;
        final TextView tvDesc;
        final ImageView imageTv;

        TvViewHolder(View itemView) {
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
                Intent tvIntent = new Intent(context, DetailTelevisionActivity.class);
                tvIntent.putExtra(DetailTelevisionActivity.EXTRA_TV, getListTelevision().get(position));
                context.startActivity(tvIntent);
            }
        }
    }
}

