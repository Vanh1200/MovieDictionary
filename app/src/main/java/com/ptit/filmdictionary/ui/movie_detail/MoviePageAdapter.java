package com.ptit.filmdictionary.ui.movie_detail;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoviePageAdapter extends FragmentStatePagerAdapter {
    private static final int INFO = 0;
    private static final int TRAILER = 1;
    private static final int CAST = 2;
    private static final int PRODUCER = 3;
    private static final String TITLE_INFO = "Information";
    private static final String TITLE_TRAILER = "Trailer";
    private static final String TITLE_CAST = "Casts";
    private static final String TITLE_PRODUCER = "Producer";
    private List<Fragment> mFragments;

    public MoviePageAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case INFO:
                return TITLE_INFO;
            case TRAILER:
                return TITLE_TRAILER;
            case CAST:
                return TITLE_CAST;
            case PRODUCER:
                return TITLE_PRODUCER;
            default:
                return super.getPageTitle(position);
        }
    }
}
