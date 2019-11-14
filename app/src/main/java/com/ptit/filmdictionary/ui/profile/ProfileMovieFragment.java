package com.ptit.filmdictionary.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentProfileMovieBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ProfileMovieFragment extends Fragment {
    private static final String ARGUMENT_PROFILE_USER_ID = "user_id";
    private FragmentProfileMovieBinding mBinding;
    private FeedProfileAdapter mProfileAdapter;
    @Inject
    ProfileViewModel mViewModel;
    private String userId;

    public static ProfileMovieFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PROFILE_USER_ID, userId);
        ProfileMovieFragment fragment = new ProfileMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        userId = getArguments().getString(ARGUMENT_PROFILE_USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_movie, container, false);
        initComponents();
        loadData();
        observeData();
        return mBinding.getRoot();
    }

    private void loadData() {
        mViewModel.loadFeedProfile(userId, 0);
    }

    private void observeData() {
        mViewModel.getLiveFeedProfile().observe(this, data -> {
            mProfileAdapter.setData(data);
        });
    }

    private void initComponents() {
        mProfileAdapter = new FeedProfileAdapter(getActivity());
        mBinding.recyclerFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerFeed.setAdapter(mProfileAdapter);
    }
}
