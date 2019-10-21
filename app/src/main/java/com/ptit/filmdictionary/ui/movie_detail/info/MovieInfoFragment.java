package com.ptit.filmdictionary.ui.movie_detail.info;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.databinding.FragmentMovieInfoBinding;
import com.ptit.filmdictionary.ui.genre.GenreActivity;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment implements GenreRecylerAdapter.ItemClickListener,
        MovieDetailViewModel.OnFavoriteListener {
    private static final String STR_ADDED = "Added";
    private static final String STR_DELETED = "Deleted";
    private static final String KEY_MOVIE_ID = "movie_id";
    private MovieDetailViewModel mViewModel;
    private FragmentMovieInfoBinding mBinding;
    private int movieId;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    public static MovieInfoFragment newInstance(int movieId) {

        Bundle args = new Bundle();
        args.putInt(KEY_MOVIE_ID, movieId);

        MovieInfoFragment fragment = new MovieInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false);
        mBinding.setViewModel(mViewModel);
        getIncomingData();
        initRecyclerView();
        initListener();
        loadData();
        return mBinding.getRoot();
    }

    private void getIncomingData() {
        movieId = getArguments().getInt(KEY_MOVIE_ID);
    }

    private void loadData() {
        mViewModel.loadComments(movieId);
    }

    private void initListener() {
        mViewModel.setFavoriteListener(this);
        mBinding.imageFavorite.setOnClickListener(v -> mViewModel.changeFavorite());
        mBinding.layoutPostComment.imageSend.setOnClickListener(v -> {
            mViewModel.postComments(movieId,
                    new CommentBody("1", "anh", mBinding.layoutPostComment.textComment.getText().toString()));
            mBinding.layoutPostComment.textComment.setText("");
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerGenre.setAdapter(new GenreRecylerAdapter(new ArrayList<>(), this));
    }

    public MovieDetailViewModel getViewModel() {
        return mViewModel;
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onItemClick(Genre genre) {
        Intent intent = GenreActivity.getIntent(getContext(), genre.getId(), genre.getName());
        getActivity().startActivity(intent);
    }

    @Override
    public void onFavoriteClick(boolean isFavorite) {
        Toast.makeText(getContext(), isFavorite ? STR_ADDED : STR_DELETED, Toast.LENGTH_SHORT).show();
    }
}
