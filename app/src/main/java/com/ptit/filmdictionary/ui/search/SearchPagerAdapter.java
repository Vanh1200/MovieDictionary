package com.ptit.filmdictionary.ui.search;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.ui.search_movie.SearchMovieFragment;
import com.ptit.filmdictionary.ui.search_user.SearchUserFragment;
import com.ptit.filmdictionary.utils.MyApplication;

public class SearchPagerAdapter extends FragmentPagerAdapter {
    private static final int TOTAL_PAGE = 2;
    private SearchViewModel mSearchViewModel;


    public SearchPagerAdapter(FragmentManager fm, SearchViewModel viewModel) {
        super(fm);
        mSearchViewModel = viewModel;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            SearchMovieFragment movieFragment = SearchMovieFragment.newInstance();
            movieFragment.setViewModel(mSearchViewModel);
            return movieFragment;
        } else {
            SearchUserFragment userFragment = SearchUserFragment.newInstance();
            userFragment.setViewModel(mSearchViewModel);
            return userFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return MyApplication.getContext().getString(R.string.search_movie);
        }
        return MyApplication.getContext().getString(R.string.search_user);
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
    }
}
