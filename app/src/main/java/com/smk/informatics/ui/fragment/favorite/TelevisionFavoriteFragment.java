package com.smk.informatics.ui.fragment.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smk.informatics.R;
import com.smk.informatics.adapter.FavoriteTelevisionAdapter;
import com.smk.informatics.model.MainViewModel;
import com.smk.informatics.model.Television;

import java.util.ArrayList;

public class TelevisionFavoriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FavoriteTelevisionAdapter mFavoriteTelevisionAdapter;
    private ProgressBar mProgressBar;

    public TelevisionFavoriteFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_movie);
        mProgressBar = rootView.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTelevisionFavorite("television").observe(this, getTelevisionFavorite);

        mFavoriteTelevisionAdapter = new FavoriteTelevisionAdapter(getActivity());
        mFavoriteTelevisionAdapter.notifyDataSetChanged();
        mainViewModel.setTelevisionDatabase("television");
        showRecyclerList();
        showLoading(true);

        mRecyclerView.setHasFixedSize(true);
        return rootView;
    }

    private void showRecyclerList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFavoriteTelevisionAdapter);
    }

    private void showLoading(Boolean state){
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private final Observer<ArrayList<Television>> getTelevisionFavorite = new Observer<ArrayList<Television>>() {
        @Override
        public void onChanged(ArrayList<Television> television) {
            if (television != null) {
                mFavoriteTelevisionAdapter.setListFavoriteTelevision(television);
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
        mainViewModel.setMovieDatabase("television");
    }
}
