package com.smk.informatics.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smk.informatics.ui.fragment.favorite.MovieFavoriteFragment;
import com.smk.informatics.ui.fragment.favorite.TelevisionFavoriteFragment;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private final Fragment[] tabFragments;

    public ViewPageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabFragments = new Fragment[]{
                new MovieFavoriteFragment(),
                new TelevisionFavoriteFragment()
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragments[position];
    }

    @Override
    public int getCount() {
        return tabFragments.length;
    }
}
