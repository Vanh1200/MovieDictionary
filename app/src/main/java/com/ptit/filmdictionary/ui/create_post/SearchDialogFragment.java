package com.ptit.filmdictionary.ui.create_post;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.DialogFragmentSearchBinding;
import com.ptit.filmdictionary.ui.search_movie.adapter.SearchMovieAdapter;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SearchDialogFragment extends BottomSheetDialogFragment implements TextWatcher, BaseRecyclerViewAdapter.ItemListener<Movie> {
    private static final long TIME_DELAY_SEARCH = 400;
    private DialogFragmentSearchBinding mBinding;
    private SearchDialogListener mListener;
    private Handler mHandlerSearch = new Handler();
    private SearchMovieAdapter mSearchMovieAdapter;
    @Inject
    CreatePostViewModel createPostViewModel;

    public void setListener(SearchDialogListener listener) {
        mListener = listener;
    }

    public static SearchDialogFragment newInstance() {

        Bundle args = new Bundle();

        SearchDialogFragment fragment = new SearchDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_search, container, false);
        initAdapters();
        initListeners();
        observeLiveData();
        return mBinding.getRoot();
    }

    private void observeLiveData() {
        createPostViewModel.getLiveMovies().observe(this, data -> {
            mSearchMovieAdapter.setData(data);
//            mBinding.viewDumb.setVisibility(View.VISIBLE);
        });
    }

    private void initListeners() {
        mBinding.edtSearch.addTextChangedListener(this);
    }

    private void initAdapters() {
        mSearchMovieAdapter = new SearchMovieAdapter();
        mSearchMovieAdapter.setItemListener(this);
        mBinding.recyclerMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerMovies.setAdapter(mSearchMovieAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) return;
        mHandlerSearch.removeCallbacksAndMessages(null);
        mHandlerSearch.postDelayed(() -> createPostViewModel.searchMovie(s.toString(), 1), TIME_DELAY_SEARCH);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClicked(Movie movie, int position) {
        if (mListener != null) {
            mListener.onClickMovie(movie);
        }
    }

    @Override
    public void onElementClicked(Movie movie, int position) {

    }

    public interface SearchDialogListener {
        void onClickMovie(Movie movie);
    }
}
