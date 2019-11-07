package com.ptit.filmdictionary.ui.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ptit.filmdictionary.ui.favorite.FavoriteFragment;
import com.ptit.filmdictionary.ui.feed.FeedFragment;

public class MainAdapter extends FragmentPagerAdapter {
    private static final int SUM_FRAGMENT = 3;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVORITE = 1;
    private static final int FRAGMENT_SETTING = 2;

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FRAGMENT_HOME:
                return HomeFragment.getInstance();
            case FRAGMENT_FAVORITE:
                return FavoriteFragment.getInstance();
            case FRAGMENT_SETTING:
                return FeedFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return SUM_FRAGMENT;
    }
}
