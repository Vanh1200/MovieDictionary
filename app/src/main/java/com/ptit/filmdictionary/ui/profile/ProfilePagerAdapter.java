package com.ptit.filmdictionary.ui.profile;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.ui.search.SearchViewModel;
import com.ptit.filmdictionary.ui.search_movie.SearchMovieFragment;
import com.ptit.filmdictionary.ui.search_user.SearchUserFragment;
import com.ptit.filmdictionary.utils.MyApplication;

public class ProfilePagerAdapter extends FragmentPagerAdapter {
    private static final int TOTAL_PAGE = 2;
    private String mUserId;

    public ProfilePagerAdapter(FragmentManager fm, String userId) {
        super(fm);
        mUserId = userId;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ProfilePostFragment.newInstance(mUserId);
        } else {
            return ProfileMovieFragment.newInstance(mUserId);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return MyApplication.getContext().getString(R.string.profile_tab_post);
        }
        return MyApplication.getContext().getString(R.string.profile_tab_reviewed_movie);
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
    }
}
