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
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentProfilePostBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ProfilePostFragment extends Fragment {
    private static final String ARGUMENT_PROFILE_USER_ID = "user_id";
    private FragmentProfilePostBinding mBinding;
    private FeedProfileAdapter mProfileAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsLoading = false;
    private boolean mIsRefresh = true;
    private boolean isNoMoreData = false;
    private static final int DEFAULT_PER_PAGE = 20;
    private static final int DEFAULT_PAGE = 0;
    private int mCurrentPage = 0;

    @Inject
    ProfileViewModel mViewModel;
    private String userId;

    public static ProfilePostFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PROFILE_USER_ID, userId);
        ProfilePostFragment fragment = new ProfilePostFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_post, container, false);
        initComponents();
        loadData();
        observeData();
        return mBinding.getRoot();
    }

    private void loadData() {
        mViewModel.loadFeedProfile(userId, DEFAULT_PAGE);
    }

    private void observeData() {
        mViewModel.getLiveFeedProfile().observe(this, data -> {
            mIsLoading = false;
            if (mIsRefresh) {
                isNoMoreData = false;
                mProfileAdapter.setData(data);
                mIsRefresh = false;
            } else {
                mProfileAdapter.removeLoadMore();
                mProfileAdapter.addData(data);
                if (data.size() < DEFAULT_PER_PAGE) {
                    isNoMoreData = true;
                }
            }
        });
    }

    private void initComponents() {
        mProfileAdapter = new FeedProfileAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.recyclerFeed.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerFeed.setAdapter(mProfileAdapter);
        mBinding.recyclerFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    mIsScrolling = true;
//                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoading && mLinearLayoutManager.findLastVisibleItemPosition() == mProfileAdapter.getItemCount() - 1 && !isNoMoreData) {
                    mIsLoading = true;
                    mViewModel.loadFeedProfile(userId, ++mCurrentPage);
                    mProfileAdapter.addLoadMore();
                }
            }
        });
    }
}
