package com.ptit.filmdictionary.ui.follower;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.ActivityFollowBinding;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class FollowActivity extends AppCompatActivity implements View.OnClickListener {
    private static String EXTRAS_USER = "user";
    private static String EXTRAS_FOLLOWING = "following";
    @Inject
    ViewModelFactory mViewModelFactory;
    @Inject
    PreferenceUtil mPreferenceUtil;
    private ActivityFollowBinding mBinding;
    private FollowPagerAdapter mFollowPagerAdapter;
    private FollowViewModel mFollowViewModel;
    private UserResponse mUser;
    private boolean isFollowingTab;

    public static void start(Context context, UserResponse user, boolean isFollowingTab) {
        Intent intent = new Intent(context, FollowActivity.class);
        intent.putExtra(EXTRAS_USER, user);
        intent.putExtra(EXTRAS_FOLLOWING, isFollowingTab);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_follow);
        mFollowViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FollowViewModel.class);
        getIncomingData();
        initComponents();
        initListeners();
        loadData();
    }

    private void initListeners() {
        mBinding.imageBack.setOnClickListener(this);
    }

    private void getIncomingData() {
        mUser = getIntent().getParcelableExtra(EXTRAS_USER);
        isFollowingTab = getIntent().getBooleanExtra(EXTRAS_FOLLOWING, false);
        mBinding.textUserName.setText(mUser.getUserName());
    }

    private void loadData() {
        mFollowViewModel.getFollower(mUser.getId(), "0");
        mFollowViewModel.getFollowing(mUser.getId(), "0");
    }

    private void initComponents() {
        mFollowPagerAdapter = new FollowPagerAdapter(getSupportFragmentManager());
        mBinding.viewpager.setAdapter(mFollowPagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewpager);
        if (isFollowingTab) {
            mBinding.viewpager.setCurrentItem(1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
