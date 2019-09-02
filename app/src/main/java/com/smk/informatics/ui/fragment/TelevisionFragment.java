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
import com.smk.informatics.adapter.TvAdapter;
import com.smk.informatics.adapter.TvHorizontalAdapter;
import com.smk.informatics.model.MainViewModel;
import com.smk.informatics.model.Television;

import java.util.ArrayList;
import java.util.Objects;

public class TelevisionFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private TvAdapter mTvAdapter;
    private TvHorizontalAdapter mTvHorizontalAdapter;
    private ProgressBar mProgressBar;
    private MainViewModel mMainViewModel;

    public TelevisionFragment() {
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
        mMainViewModel.getTelevisions().observe(this, getTelevision);
        mMainViewModel.setTelevision();

        mMainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        mMainViewModel.getNowPlayingTelevision().observe(this, getNowPlayingTv);
        mMainViewModel.setNowPlayingTelevision();

        mTvAdapter = new TvAdapter(getActivity());
        mTvHorizontalAdapter = new TvHorizontalAdapter(getActivity());
        mTvAdapter.notifyDataSetChanged();
        mTvHorizontalAdapter.notifyDataSetChanged();

        showRecyclerList();
        showLoading(true);

        mRecyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);
        return rootView;
    }

    private void showRecyclerList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mTvAdapter);
        mRecyclerView1.setAdapter(mTvHorizontalAdapter);
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
            searchView.setQueryHint(getResources().getString(R.string.search_hint_tv));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mMainViewModel.getSearchTv().observe(Objects.requireNonNull(getActivity()), getSearchTelevision);
                    mMainViewModel.setSearchTelevision(query);
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
                mMainViewModel.setTelevision();
                return true;
            }
        });
    }

    private final Observer<ArrayList<Television>> getTelevision = new Observer<ArrayList<Television>>() {
        @Override
        public void onChanged(ArrayList<Television> television) {
            if (television != null) {
                mTvAdapter.setListTelevision(television);
                showRecyclerList();
                showLoading(false);

            } else {
                showLoading(false);
            }
        }
    };

    private final Observer<ArrayList<Television>> getNowPlayingTv = new Observer<ArrayList<Television>>() {
        @Override
        public void onChanged(ArrayList<Television> television) {
            if (television != null) {
                mTvHorizontalAdapter.setListTelevision(television);
                showRecyclerList();
                showLoading(false);

            } else {
                showLoading(false);
            }
        }
    };

    private final Observer<ArrayList<Television>> getSearchTelevision = new Observer<ArrayList<Television>>() {
        @Override
        public void onChanged(ArrayList<Television> television) {
            if (television != null) {
                mTvAdapter.setListTelevision(television);
                showRecyclerList();
                showLoading(false);
                if (television.size() == 0)
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
            } else {
                showLoading(false);
            }
        }
    };
}
