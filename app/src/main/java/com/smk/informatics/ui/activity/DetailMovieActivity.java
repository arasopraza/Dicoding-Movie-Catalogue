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
import com.smk.informatics.database.MovieHelper;
import com.smk.informatics.model.Movie;
import com.smk.informatics.widget.MovieFavoriteWidget;

import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    private MovieHelper mMovieHelper;
    private Movie mMovie;
    private Button mButton;
    private Boolean isFavorite = false;

    public DetailMovieActivity() {
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
        mButton = findViewById(R.id.button_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Detail Movie");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24dp);

        mButton.setOnClickListener(this);

        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvName.setText(mMovie.getmName());
        tvRelease.setText(mMovie.getmRelease());
        tvDesc.setText(mMovie.getmDesc());
        tvPopularity.setText(mMovie.getmPopularity());
        tvVoteAverage.setText(mMovie.getmVote());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w780/" + mMovie.getmPhoto())
                .placeholder(R.drawable.ic_image_black_24dp)
                .transform(new FitCenter())
                .into(imgPhoto);

        mMovieHelper = MovieHelper.getInstance(getApplicationContext());
        if (mMovieHelper.CheckMovie(String.valueOf(mMovie.getmId()))) {
            mButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_24dp));
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
                mMovie.setmType("movie");
                mMovieHelper.open();
                long result = mMovieHelper.insertMovie(mMovie);
                mMovieHelper.close();
                if (result > 0) {
                    mButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_24dp));
                    Toast.makeText(getApplicationContext(), "Success " + mMovie.getmName() + " Added to Favorite", Toast.LENGTH_SHORT).show();
                    updateWidget();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + mMovie.getmName() + " Added to Favorite", Toast.LENGTH_SHORT).show();
                }
            } else {
                mMovie.setmType("movie");
                mMovieHelper.open();
                long result = mMovieHelper.deleteMovie(mMovie.getmId());
                mMovieHelper.close();
                if (result > 0) {
                    mButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_24dp));
                    Toast.makeText(getApplicationContext(), "Success " + mMovie.getmName() + " Delete from Favorite", Toast.LENGTH_SHORT).show();
                    updateWidget();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + mMovie.getmName() + " Delete Favorite", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void updateWidget() {
        Intent widgetUpdateIntent = new Intent(this, MovieFavoriteWidget.class);
        widgetUpdateIntent.setAction(MovieFavoriteWidget.TOAST_ACTION);
        sendBroadcast(widgetUpdateIntent);
    }
}
