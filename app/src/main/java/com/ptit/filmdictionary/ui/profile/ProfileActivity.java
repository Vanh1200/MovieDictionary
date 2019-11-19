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
import com.ptit.filmdictionary.ui.login.LoginFragment;
import com.ptit.filmdictionary.utils.ImageHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ProfileActivity extends AppCompatActivity {
    private static final String EXTRAS_USER = "user";
    private ActivityProfileBinding mBinding;
    private UserResponse user;

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
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            setTextStatusBarColor();
//        }
        user = getIntent().getParcelableExtra(EXTRAS_USER);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, ProfileFragment.newInstance(user)).commit();
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void setTextStatusBarColor() {
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//    }
}
