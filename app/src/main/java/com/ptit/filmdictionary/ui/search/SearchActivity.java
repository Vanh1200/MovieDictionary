package com.ptit.filmdictionary.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseActivity;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.repository.AuthRepository;
import com.ptit.filmdictionary.data.repository.HistoryRepository;
import com.ptit.filmdictionary.data.repository.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.ActivitySearchBinding;
import com.ptit.filmdictionary.ui.history.HistoryDialogFragment;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.search_movie.SearchNavigator;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements
        SearchNavigator, TextWatcher, BaseRecyclerViewAdapter.ItemListener<Movie>, View.OnClickListener,
        TextView.OnEditorActionListener, HistoryDialogFragment.HistoryClickListener {
    private static final String EXTRAS_TYPE_SEARCH = "type_search";
    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;
    private SearchPagerAdapter mSearchPagerAdapter;

    public static final int TYPE_SEARCH_USER = 1;
    public static final int TYPE_SEARCH_MOVIE = 0;

    private int type;

    @Inject
    AuthRepository mAuthRepository;

    @Inject
    PreferenceUtil mPreferenceUtil;

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        mBinding = getViewDataBinding();
        setUpActionBar();
        getIncomingData();
        initComponents();
        initEvents();
    }

    private void getIncomingData() {
        if (getIntent() != null && getIntent().hasExtra(EXTRAS_TYPE_SEARCH)) {
            type = getIntent().getIntExtra(EXTRAS_TYPE_SEARCH, TYPE_SEARCH_MOVIE);
        }
    }

    public static void startSearch(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRAS_TYPE_SEARCH, type);
        context.startActivity(intent);
    }

    private void initComponents() {
        mSearchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager(), mSearchViewModel);
        mBinding.viewPager.setAdapter(mSearchPagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        if (type == TYPE_SEARCH_USER) {
            mBinding.viewPager.setCurrentItem(TYPE_SEARCH_USER);
        }
    }


    private void initEvents() {
        mBinding.textSearch.addTextChangedListener(this);
        mBinding.textSearch.setOnEditorActionListener(this);
        mBinding.imageHistory.setOnClickListener(this);
    }

    private void setUpActionBar() {
        setSupportActionBar(mBinding.toolbarSearch);
        mBinding.toolbarSearch.setNavigationIcon(R.drawable.ic_arrow_left_black_24dp);
        mBinding.toolbarSearch.setNavigationOnClickListener(a -> onBackPressed());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(mBinding.textSearch.getWindowToken(), 0);
    }

    @Override
    protected SearchViewModel getViewModel() {
        if (mSearchViewModel == null) {
            mSearchViewModel = new SearchViewModel(MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(this),
                    MovieLocalDataSource.getInstance(this)),
                    HistoryRepository.getInstance()
            );
            if (mAuthRepository != null) {
                mSearchViewModel.setAuthRepository(mAuthRepository);
            }
        }
        return mSearchViewModel;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie.getId(), movie.getTitle()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) return;
        mSearchViewModel.loadResultByKeyword(s.toString());
        if (mAuthRepository != null) {
            mSearchViewModel.setAuthRepository(mAuthRepository);
        }
        mSearchViewModel.searchUser(mPreferenceUtil.getUserId(), s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClicked(Movie movie, int position) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onElementClicked(Movie movie, int position) {

    }

    @Override
    protected void onDestroy() {
        mSearchViewModel.dispose();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        openHistoryDialog();
    }

    private void openHistoryDialog() {
        HistoryDialogFragment fragment = HistoryDialogFragment.getInstance();
        fragment.setListener(this);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    private void hideHistoryDialog() {
        HistoryDialogFragment fragment = HistoryDialogFragment.getInstance();
        fragment.dismiss();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String query = v.getText().toString();
            if (query.isEmpty()) return false;
            mSearchViewModel.saveHistory(query);
            hideSoftKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void onHistoryClickListener(History history) {
        mBinding.textSearch.setText(history.getTitle());
        hideHistoryDialog();
        mSearchViewModel.loadResultByKeyword(history.getTitle());
    }
}
