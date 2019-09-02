package com.smk.informatics.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.smk.informatics.R;
import com.smk.informatics.model.Television;
import com.smk.informatics.database.MovieHelper;
import com.smk.informatics.widget.MovieFavoriteWidget;

import java.util.Objects;


public class DetailTelevisionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TV = "extra_tv";
    private MovieHelper mMovieHelper;
    private Television mTelevision;
    private Button button;
    private boolean isFavorite = false;

    public DetailTelevisionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView tvName = findViewById(R.id.text_name);
        TextView tvRelease = findViewById(R.id.text_release);
        TextView tvDesc = findViewById(R.id.text_description);
        TextView tvPopularity = findViewById(R.id.text_popularity);
        TextView tvVoteAverage = findViewById(R.id.text_vote_average);
        ImageView imgPhoto = findViewById(R.id.image_item_photo);
        button = findViewById(R.id.button_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Detail Television");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24dp);

        button.setOnClickListener(this);

        mTelevision = getIntent().getParcelableExtra(EXTRA_TV);
        tvName.setText(mTelevision.getmName());
        tvRelease.setText(mTelevision.getmRelease());
        tvDesc.setText(mTelevision.getmDesc());
        tvPopularity.setText(mTelevision.getmPopularity());
        tvVoteAverage.setText(mTelevision.getmVote());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w780/" + mTelevision.getmPhoto())
                .placeholder(R.drawable.ic_image_black_24dp)
                .transform(new FitCenter())
                .into(imgPhoto);

        mMovieHelper = MovieHelper.getInstance(getApplicationContext());
        if (mMovieHelper.CheckMovie(String.valueOf(mTelevision.getmId()))) {
            button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_24dp));
            isFavorite = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_favorite)
            if (!isFavorite) {
                mTelevision.setmType("television");
                mMovieHelper.open();
                long result = mMovieHelper.insertTelevision(mTelevision);
                mMovieHelper.close();
                if (result > 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_24dp));
                    Toast.makeText(getApplicationContext(), "Success " + mTelevision.getmName() + " Added to Favorite", Toast.LENGTH_SHORT).show();
                    updateWidget();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + mTelevision.getmName() + " Added to Favorite", Toast.LENGTH_SHORT).show();
                }
            } else {
                mTelevision.setmType("television");
                mMovieHelper.open();
                long result = mMovieHelper.deleteTelevision(mTelevision.getmId());
                mMovieHelper.close();
                if (result > 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_24dp));
                    Toast.makeText(getApplicationContext(), "Success " + mTelevision.getmName() + " Delete from Favorite", Toast.LENGTH_SHORT).show();
                    updateWidget();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + mTelevision.getmName() + " Delete Favorite", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void updateWidget() {
        Intent widgetUpdateIntent = new Intent(this, MovieFavoriteWidget.class);
        widgetUpdateIntent.setAction(MovieFavoriteWidget.TOAST_ACTION);
        sendBroadcast(widgetUpdateIntent);
    }
}
