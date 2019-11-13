package com.ptit.filmdictionary.ui.movie_detail.info;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.FragmentMovieInfoBinding;
import com.ptit.filmdictionary.ui.chat.ChatActivity;
import com.ptit.filmdictionary.ui.comment.CommentDialogFragment;
import com.ptit.filmdictionary.ui.genre.GenreActivity;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailViewModel;
import com.ptit.filmdictionary.ui.profile.ProfileActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment implements GenreRecylerAdapter.ItemClickListener,
        MovieDetailViewModel.OnFavoriteListener, View.OnClickListener, CommentAdapter.OnCommentListener {
    private static final String STR_ADDED = "Added";
    private static final String STR_DELETED = "Deleted";
    private static final String KEY_MOVIE_ID = "movie_id";
    private MovieDetailViewModel mViewModel;
    private FragmentMovieInfoBinding mBinding;
    private String movieId;
    private CommentAdapter mCommentAdapter;

    @Inject
    PreferenceUtil mPreferenceUtil;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    public static MovieInfoFragment newInstance(String movieId) {

        Bundle args = new Bundle();
        args.putString(KEY_MOVIE_ID, movieId);

        MovieInfoFragment fragment = new MovieInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
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
        observeLiveData();
        return mBinding.getRoot();
    }

    private void observeLiveData() {
        mViewModel.getLiveComments().observe(this, data -> {
            if (data != null && data.size() > 3) {
                mCommentAdapter.setData(data.subList(0, 3));
            } else if (data != null){
                mCommentAdapter.setData(data);
            }
        });
    }

    private void getIncomingData() {
        movieId = getArguments().getString(KEY_MOVIE_ID);
    }

    private void loadData() {
        mViewModel.loadComments(movieId);
    }

    private void initListener() {
        mViewModel.setFavoriteListener(this);
        mBinding.imageFavorite.setOnClickListener(v -> mViewModel.changeFavorite());
//        mBinding.layoutPostComment.imageSend.setOnClickListener(v -> {
//            if (!mBinding.layoutPostComment.textComment.getText().toString().isEmpty()) {
//                if (BaseHelper.isInternetOn(getContext())) {
//                    mViewModel.postComments(movieId,
//                            new CommentBody(mPreferenceUtil.getUserId(), mPreferenceUtil.getUserName(), mBinding.layoutPostComment.textComment.getText().toString()));
//                    mBinding.layoutPostComment.textComment.setText("");
//                } else {
//                    BaseHelper.showCustomSnackbarView(mBinding.viewOne, getContext(), getString(R.string.text_no_internet));
//                }
//            }
//        });
        mBinding.imageSeeAllComment.setOnClickListener(this);
        mBinding.textSeeAllComment.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mBinding.recyclerGenre.setAdapter(new GenreRecylerAdapter(new ArrayList<>(), this));

        mCommentAdapter = new CommentAdapter(getContext());
        mCommentAdapter.setListener(this);
        mBinding.recyclerComment.setAdapter(mCommentAdapter);
        mBinding.recyclerComment.setNestedScrollingEnabled(false);
        mBinding.recyclerComment.setLayoutManager(new LinearLayoutManager(getContext()));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_see_all_comment:
            case R.id.text_see_all_comment:
                CommentDialogFragment.newInstance(movieId).show(getChildFragmentManager(), null);
                break;
        }
    }

    @Override
    public void onClickItem(CommentResponse comment, int position) {
        ProfileActivity.start(getActivity(), comment.getUser());
    }
}
