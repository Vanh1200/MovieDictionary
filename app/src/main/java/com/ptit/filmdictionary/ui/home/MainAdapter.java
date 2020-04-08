package com.ptit.filmdictionary.ui.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.favorite.FavoriteFragment;
import com.ptit.filmdictionary.ui.feed.FeedFragment;
import com.ptit.filmdictionary.ui.profile.ProfileFragment;

public class MainAdapter extends FragmentPagerAdapter {
    private static final int SUM_FRAGMENT = 4;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVORITE = 2;
    private static final int FRAGMENT_DISCOVER = 1;
    private static final int FRAGMENT_PROFILE = 3;
    private UserResponse user;

    public MainAdapter(FragmentManager fm, UserResponse user) {
        super(fm);
        this.user = user;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FRAGMENT_HOME:
                return HomeFragment.getInstance();
            case FRAGMENT_FAVORITE:
                return FavoriteFragment.getInstance();
            case FRAGMENT_DISCOVER:
                return FeedFragment.newInstance();
            case FRAGMENT_PROFILE:
                return ProfileFragment.newInstance(user);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return SUM_FRAGMENT;
    }
}
