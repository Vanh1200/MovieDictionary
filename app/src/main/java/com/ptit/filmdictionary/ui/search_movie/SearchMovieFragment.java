package com.ptit.filmdictionary.ui.search_movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.FragmentSearchMovieBinding;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.search.SearchViewModel;
import com.ptit.filmdictionary.ui.search_movie.adapter.SearchMovieAdapter;

public class SearchMovieFragment extends Fragment implements BaseRecyclerViewAdapter.ItemListener<Movie> {
    private FragmentSearchMovieBinding mBinding;
    private boolean mIsScrolling = false;
    private SearchMovieAdapter mSearchMovieAdapter;

    private SearchViewModel mSearchViewModel;

    public void setViewModel(SearchViewModel searchViewModel) {
        mSearchViewModel = searchViewModel;
    }

    public static SearchMovieFragment newInstance() {

        Bundle args = new Bundle();

        SearchMovieFragment fragment = new SearchMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_movie, container, false);
        initComponents();
        initListeners();
        return mBinding.getRoot();
    }

    private void initListeners() {

    }

    private void initComponents() {
        mSearchMovieAdapter = new SearchMovieAdapter();
        mSearchMovieAdapter.setItemListener(this);
        mBinding.recyclerSearch.setAdapter(mSearchMovieAdapter);
        mBinding.recyclerSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mBinding
                        .recyclerSearch.getLayoutManager();
                if (mIsScrolling && linearLayoutManager.findLastVisibleItemPosition() == mSearchMovieAdapter.getItemCount() - 1) {
                    loadMore();
                }
            }
        });
        if (mSearchViewModel != null) {
            mBinding.setViewModel(mSearchViewModel);
        }
    }

    @Override
    public void onItemClicked(Movie movie, int position) {
        startActivity(MovieDetailActivity.getIntent(getActivity(), movie.getId(), movie.getTitle()));
    }

    @Override
    public void onElementClicked(Movie movie, int position) {

    }

    private void loadMore() {
        mIsScrolling = false;
        mSearchViewModel.loadMore();
    }
}
