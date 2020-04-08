package com.ptit.filmdictionary.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.home.HomeFragment;
import com.ptit.filmdictionary.ui.home.MainAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.
        OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, HomeFragment.OnScrollListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVORITE = 2;
    private static final int FRAGMENT_DISCOVER = 1;
    private static final int FRAGMENT_PROFILE = 3;
    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;

    @Inject
    PreferenceUtil mPreferenceUtil;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidInjection.inject(this);
        initViews();
        initViewPager();
        registerEvents();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
    }

    private void initViewPager() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(mPreferenceUtil.getUserId());
        userResponse.setAvatar(mPreferenceUtil.getUserAvatar());
        userResponse.setUserName(mPreferenceUtil.getUserName());
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), userResponse);
        mViewPager.setAdapter(mainAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);
        HomeFragment.getInstance().setListener(this);
    }

    private void registerEvents() {
        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void initViews() {
        mViewPager = findViewById(R.id.view_pager);
        mNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                mViewPager.setCurrentItem(FRAGMENT_HOME);
                return true;
            case R.id.menu_favorite:
                mViewPager.setCurrentItem(FRAGMENT_FAVORITE);
                return true;
            case R.id.menu_discover:
                mViewPager.setCurrentItem(FRAGMENT_DISCOVER);
                return true;
            case R.id.menu_profile:
                mViewPager.setCurrentItem(FRAGMENT_PROFILE);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case FRAGMENT_HOME:
                mNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case FRAGMENT_FAVORITE:
                mNavigationView.setSelectedItemId(R.id.menu_favorite);
                break;
            case FRAGMENT_DISCOVER:
                mNavigationView.setSelectedItemId(R.id.menu_discover);
                break;
            case FRAGMENT_PROFILE:
                mNavigationView.setSelectedItemId(R.id.menu_profile);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onSlideCollapsed() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            setSystemBarTheme(this, false);
//        }
//        isScrollToTop = true;
    }

    @Override
    public void onSlideExpanded() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            setSystemBarTheme(this, true);
//        }
//        isScrollToTop = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
