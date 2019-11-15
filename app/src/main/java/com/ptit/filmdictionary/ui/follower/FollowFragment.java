package com.ptit.filmdictionary.ui.follower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.FragmentFollowBinding;
import com.ptit.filmdictionary.ui.profile.ProfileActivity;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

public class FollowFragment extends Fragment implements UserFollowAdapter.UserCallback {
    public static final int TYPE_FOLLOWER = 0;
    public static final int TYPE_FOLLOWING = 1;
    private static final String ARGUMENT_FOLLOW = "follow";
    private FragmentFollowBinding mBinding;
    private UserFollowAdapter mUserFollowAdapter;
    private int type;
    private FollowViewModel mFollowViewModel;

    @Inject
    ViewModelFactory mViewModelFactory;

    public static FollowFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_FOLLOW, type);
        FollowFragment fragment = new FollowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow, container, false);
        getIncomingData();
        initComponents();
        observeData();
        return mBinding.getRoot();
    }

    private void getIncomingData() {
        type = getArguments().getInt(ARGUMENT_FOLLOW);
    }

    private void observeData() {
        mFollowViewModel.getLiveFollowers().observe(this, data -> {
            if (type == TYPE_FOLLOWER) {
                mUserFollowAdapter.setData(data);
                mBinding.progressLoadUser.setVisibility(View.GONE);
            }
        });

        mFollowViewModel.getLiveFollowing().observe(this, data -> {
            if (type == TYPE_FOLLOWING) {
                mUserFollowAdapter.setData(data);
                mBinding.progressLoadUser.setVisibility(View.GONE);
            }
        });
    }

    private void initComponents() {
        mUserFollowAdapter = new UserFollowAdapter(getActivity());
        mBinding.recyclerFollow.setAdapter(mUserFollowAdapter);
        mUserFollowAdapter.setCallback(this);
        mBinding.recyclerFollow.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFollowViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(FollowViewModel.class);
    }


    @Override
    public void onClickUser(UserResponse user) {
        ProfileActivity.start(getActivity(), user);
    }

    @Override
    public void onClickMore(UserResponse user) {

    }
}
