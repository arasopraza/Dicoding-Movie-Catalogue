package com.smk.informatics.ui.fragment;


import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smk.informatics.R;
import com.smk.informatics.adapter.MovieAdapter;
import com.smk.informatics.adapter.MovieHorizontalAdapter;
import com.smk.informatics.model.MainViewModel;
import com.smk.informatics.model.Movie;

import java.util.ArrayList;
import java.util.Objects;

public class MovieFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private MovieAdapter mMovieAdapter;
    private MovieHorizontalAdapter mMovieHorizontalAdapter;
    private ProgressBar mProgressBar;
    private MainViewModel mMainViewModel;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_movie);
        mRecyclerView1 = rootView.findViewById(R.id.recycler_now_playing_movie);
        mProgressBar = rootView.findViewById(R.id.progressbar);

        mMainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        mMainViewModel.getMovies().observe(this, getMovie);
        mMainViewModel.setMovie();

        mMainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        mMainViewModel.getNowPlayingMovie().observe(this, getNowPlayingMovie);
        mMainViewModel.setNowPlayingMovie();

        mMovieAdapter = new MovieAdapter(getActivity());
        mMovieHorizontalAdapter = new MovieHorizontalAdapter(getActivity());
        mMovieAdapter.notifyDataSetChanged();
        mMovieHorizontalAdapter.notifyDataSetChanged();
        showRecyclerList();
        showLoading(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView1.setHasFixedSize(true);
        setHasOptionsMenu(true);
        return rootView;
    }

    private void showRecyclerList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView1.setAdapter(mMovieHorizontalAdapter);
    }

    private void showLoading(Boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mMainViewModel.getSearchMovies().observe(Objects.requireNonNull(getActivity()), getSearchMovie);
                    mMainViewModel.setSearchMovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mMainViewModel.setMovie();
                return true;
            }
        });
    }

    private final Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                mMovieAdapter.setListMovie(movie);
                showRecyclerList();
                showLoading(false);

            } else {
                showLoading(false);
            }
        }
    };

    private final Observer<ArrayList<Movie>> getNowPlayingMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                mMovieHorizontalAdapter.setListMovie(movie);
                showRecyclerList();
                showLoading(false);

            } else {
                showLoading(false);
            }
        }
    };

    private final Observer<ArrayList<Movie>> getSearchMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                mMovieAdapter.setListMovie(movie);
                showRecyclerList();
                showLoading(false);
                if (movie.size() == 0)
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
            } else {
                showLoading(false);
            }
        }
    };
}
