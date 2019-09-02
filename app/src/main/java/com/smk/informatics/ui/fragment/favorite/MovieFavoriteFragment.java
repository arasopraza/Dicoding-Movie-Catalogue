package com.smk.informatics.ui.fragment.favorite;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.smk.informatics.R;
import com.smk.informatics.adapter.FavoriteMovieAdapter;
import com.smk.informatics.model.MainViewModel;
import com.smk.informatics.model.Movie;

import java.util.ArrayList;

public class MovieFavoriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FavoriteMovieAdapter mFavoriteMovieAdapter;
    private ProgressBar mProgressBar;

    public MovieFavoriteFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_movie);
        mProgressBar = rootView.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovieFavorite("movie").observe(this, getMovieFavorite);

        mFavoriteMovieAdapter = new FavoriteMovieAdapter(getActivity());
        mFavoriteMovieAdapter.notifyDataSetChanged();
        mainViewModel.setMovieDatabase("movie");
        showRecyclerList();
        showLoading(true);

        mRecyclerView.setHasFixedSize(true);
        return rootView;
    }

    private void showRecyclerList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFavoriteMovieAdapter);
    }

    private void showLoading(Boolean state){
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private final Observer<ArrayList<Movie>> getMovieFavorite = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                mFavoriteMovieAdapter.setListFavoriteMovie(movie);
                showRecyclerList();
                showLoading(false);

            } else {
                showLoading(false);
            }
        }
    };

    public void onResume() {
        super.onResume();
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setMovieDatabase("movie");
    }
}
