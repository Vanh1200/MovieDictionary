package com.ptit.filmdictionary.ui.follower;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.utils.MyApplication;

public class FollowPagerAdapter extends FragmentPagerAdapter {
    private static final int TOTAL_PAGE = 2;

    public FollowPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FollowFragment.newInstance(FollowFragment.TYPE_FOLLOWER);
        }
        return FollowFragment.newInstance(FollowFragment.TYPE_FOLLOWING);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return MyApplication.getContext().getString(R.string.profile_follower);
        }
        return MyApplication.getContext().getString(R.string.profile_following);
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
    }
}
