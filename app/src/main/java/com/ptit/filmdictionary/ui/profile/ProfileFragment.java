package com.ptit.filmdictionary.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.FragmentProfileBinding;
import com.ptit.filmdictionary.ui.chat.ChatActivity;
import com.ptit.filmdictionary.ui.edit_profile.ActivityEditProfile;
import com.ptit.filmdictionary.ui.follower.FollowActivity;
import com.ptit.filmdictionary.utils.ImageHelper;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding mBinding;
    private static final String ARGUMENT_USER = "user";
    private static final int DEFAULT_OFFSET = 1000;
    private UserResponse user;
    private boolean isMe;
    private ProfilePagerAdapter mPagerAdapter;
    @Inject
    PreferenceUtil mPreferenceUtil;


    public static ProfileFragment newInstance(UserResponse user) {

        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        getIncomingData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initData();
        initComponents();
        initListeners();
        return mBinding.getRoot();
    }

    private void initData() {
        ImageHelper.loadImage(mBinding.imageAvatar, user.getAvatar());
        ImageHelper.loadImage(mBinding.imageAvatarToolbar, user.getAvatar());
        ImageHelper.loadImage(mBinding.imageCover, user.getAvatar());
        mBinding.textUserNameToolbar.setText(user.getUserName());
        mBinding.textUserName.setText(user.getUserName());
    }

    private void getIncomingData() {
        user = getArguments().getParcelable(ARGUMENT_USER);
    }

    private void initComponents() {
        if (user != null && mPreferenceUtil.getUserId().equals(user.getId())) {
            isMe = true;
            mBinding.buttonMessage.setImageResource(R.drawable.ic_edit);
            mBinding.textEditProfile.setVisibility(View.VISIBLE);
            mBinding.textMessage.setVisibility(View.GONE);
            mBinding.textFollow.setVisibility(View.GONE);
            mBinding.imageLogout.setVisibility(View.VISIBLE);
        } else {
            mBinding.imageLogout.setVisibility(View.GONE);
        }
        mPagerAdapter = new ProfilePagerAdapter(getChildFragmentManager(), user.getId());
        mBinding.viewPager.setAdapter(mPagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    private void initListeners() {
        mBinding.appBarLayout.addOnOffsetChangedListener(getAppBarListener());
        mBinding.buttonMessage.setOnClickListener(this);
        mBinding.imageBack.setOnClickListener(this);
        mBinding.viewFollowers.setOnClickListener(this);
        mBinding.viewFollowings.setOnClickListener(this);
        mBinding.textFollow.setOnClickListener(this);
        mBinding.textMessage.setOnClickListener(this);
        mBinding.textEditProfile.setOnClickListener(this);
        mBinding.imageLogout.setOnClickListener(this);
    }

    private AppBarLayout.OnOffsetChangedListener getAppBarListener() {
        return (appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) > DEFAULT_OFFSET) {
                mBinding.linearInfo.setAlpha(Math.abs((verticalOffset + DEFAULT_OFFSET) / (float) (appBarLayout.getTotalScrollRange() - DEFAULT_OFFSET)));
                mBinding.linearBio.setAlpha(1 - Math.abs((verticalOffset + DEFAULT_OFFSET) / (float) (appBarLayout.getTotalScrollRange() - DEFAULT_OFFSET)));
                mBinding.imageBack.setImageResource(R.drawable.ic_arrow_left_black_24dp);
                mBinding.imageLogout.setImageResource(R.drawable.ic_logout);
            } else {
                mBinding.linearInfo.setAlpha(0);
                mBinding.linearBio.setAlpha(1);
                mBinding.imageBack.setImageResource(R.drawable.ic_arrow_left_white_24dp);
                mBinding.imageLogout.setImageResource(R.drawable.ic_logout_white);
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_message:
                if (!isMe)
                    ChatActivity.start(getActivity(), user);
                else {
                    startActivityForResult(new Intent(getActivity(), ActivityEditProfile.class), 123);
                }
                break;
            case R.id.image_back:
                getActivity().onBackPressed();
                break;
            case R.id.view_followers:
                FollowActivity.start(getActivity(), user, false);
                break;
            case R.id.view_followings:
                FollowActivity.start(getActivity(), user, true);
                break;
            case R.id.text_message:
                    ChatActivity.start(getActivity(), user);
                break;
            case R.id.text_follow:
                handleFollow();
                break;
            case R.id.text_edit_profile:
                startActivityForResult(new Intent(getActivity(), ActivityEditProfile.class), 123);
                break;
            case R.id.image_logout:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && data != null) {
            mBinding.textUserName.setText(data.getStringExtra("fullname"));
            mBinding.textUserNameToolbar.setText(data.getStringExtra("fullname"));
        }
    }

    private void handleFollow() {

    }
}
