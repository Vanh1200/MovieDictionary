package com.ptit.filmdictionary.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.appbar.AppBarLayout;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.ActivityProfileBinding;
import com.ptit.filmdictionary.ui.chat.ChatActivity;
import com.ptit.filmdictionary.ui.follower.FollowActivity;
import com.ptit.filmdictionary.utils.ImageHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRAS_USER = "user";
    private ActivityProfileBinding mBinding;
    private static final int DEFAULT_OFFSET = 1000;
    private UserResponse user;
    private boolean isMe;
    private ProfilePagerAdapter mPagerAdapter;

    @Inject
    PreferenceUtil mPreferenceUtil;

    @Inject
    ProfileViewModel mProfileViewModel;

    public static void start(Context context, UserResponse user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRAS_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
//        setTheme(R.style.TransparentStatusTheme);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        getIncomingData();
        initData();
        initComponents();
        loadData();
        observeData();
        initListeners();
    }

    private void observeData() {

    }

    private void loadData() {
    }

    private void initData() {
        ImageHelper.loadImage(mBinding.imageAvatar, user.getAvatar());
        ImageHelper.loadImage(mBinding.imageAvatarToolbar, user.getAvatar());
        ImageHelper.loadImage(mBinding.imageCover, user.getAvatar());
        mBinding.textUserNameToolbar.setText(user.getUserName());
        mBinding.textUserName.setText(user.getUserName());
    }

    private void getIncomingData() {
        user = getIntent().getParcelableExtra(EXTRAS_USER);
    }

    private void initComponents() {
        if (user != null && mPreferenceUtil.getUserId().equals(user.getId())) {
            isMe = true;
            mBinding.buttonMessage.setImageResource(R.drawable.ic_edit);
        }
        mPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager(), user.getId());
        mBinding.viewPager.setAdapter(mPagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    private void initListeners() {
        mBinding.appBarLayout.addOnOffsetChangedListener(getAppBarListener());
        mBinding.buttonMessage.setOnClickListener(this);
        mBinding.imageBack.setOnClickListener(this);
        mBinding.textBiography.setOnClickListener(this);
    }

    private AppBarLayout.OnOffsetChangedListener getAppBarListener() {
        return (appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) > DEFAULT_OFFSET) {
                mBinding.linearInfo.setAlpha(Math.abs((verticalOffset + DEFAULT_OFFSET) / (float) (appBarLayout.getTotalScrollRange() - DEFAULT_OFFSET)));
                mBinding.linearBio.setAlpha(1 - Math.abs((verticalOffset + DEFAULT_OFFSET) / (float) (appBarLayout.getTotalScrollRange() - DEFAULT_OFFSET)));
                mBinding.imageBack.setImageResource(R.drawable.ic_arrow_left_black_24dp);
                mBinding.imageMore.setImageResource(R.drawable.ic_more_black_horizontal);
            } else {
                mBinding.linearInfo.setAlpha(0);
                mBinding.linearBio.setAlpha(1);
                mBinding.imageBack.setImageResource(R.drawable.ic_arrow_left_white_24dp);
                mBinding.imageMore.setImageResource(R.drawable.ic_more_white_horizontal);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_message:
                if (!isMe)
                    ChatActivity.start(this, user);
                break;
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.text_biography:
                FollowActivity.start(this, user);
                break;
            default:
                break;
        }
    }
}
