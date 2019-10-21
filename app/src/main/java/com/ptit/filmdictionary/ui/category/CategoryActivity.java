package com.ptit.filmdictionary.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.filmdictionary.base.BaseMoviesActivity;
import com.ptit.filmdictionary.data.repository.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;

import java.util.ArrayList;

public class CategoryActivity extends BaseMoviesActivity<CategoryViewModel, CategoryAdapter> {
    public static final String BUNDLE_CATEGORY_KEY = "BUNDLE_CATEGORY_KEY";

    @Override
    protected void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_AGRS);
        String categoryKey = bundle.getString(BUNDLE_CATEGORY_KEY);
        mActionBarTitle = bundle.getString(BUNDLE_ACTION_BAR_TITLE);
        mViewModel = new CategoryViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance(this)), this);
        mViewModel.setCategoryKey(categoryKey);
        mBinding.setViewModel(mViewModel);
        mViewModel.loadMoviesByCategory(mViewModel.getPage());
    }

    @Override
    protected void initRecyclerAdapter() {
        mAdapter = new CategoryAdapter(new ArrayList<>(), this);
    }

    @Override
    protected void loadMoreMovies() {
        hideLoadMore(false);
        int nextPage = mViewModel.getPage();
        ++nextPage;
        mViewModel.loadMoviesByCategory(nextPage);
    }

    public static Intent getIntent(Context context, String categoryKey, String categoryTitle) {
        Intent intent = new Intent(context, CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CATEGORY_KEY, categoryKey);
        bundle.putString(BUNDLE_ACTION_BAR_TITLE, categoryTitle);
        intent.putExtra(EXTRA_AGRS, bundle);
        return intent;
    }

    @Override
    protected void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }
}
