package com.ptit.filmdictionary.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.ui.home.HomeFragment;
import com.ptit.filmdictionary.ui.home.MainAdapter;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.
        OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, HomeFragment.OnScrollListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVORITE = 2;
    private static final int FRAGMENT_DISCOVER = 1;
    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private boolean isScrollToTop = false;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
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
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
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
                // TODO: 20/03/2019 cho nay gay ra bug: bam sang favorite -> tro ve home -> slide khong hien???
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setSystemBarTheme(this, false);
//                }
                return true;
            case R.id.menu_discover:
                mViewPager.setCurrentItem(FRAGMENT_DISCOVER);
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
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setSystemBarTheme(this, !isScrollToTop);
//                }
                break;
            case FRAGMENT_FAVORITE:
                mNavigationView.setSelectedItemId(R.id.menu_favorite);
                // TODO: 20/03/2019 cho nay gay ra bug: bam sang favorite -> tro ve home -> slide khong hien???
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setSystemBarTheme(this, false);
//                }

                break;
            case FRAGMENT_DISCOVER:
                mNavigationView.setSelectedItemId(R.id.menu_discover);

            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setSystemBarTheme(final Activity pActivity, boolean isDark) {
        int lFlags = pActivity.getWindow().getDecorView().getSystemUiVisibility();
        getWindow().getDecorView().setSystemUiVisibility(isDark ?
                (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) :
                (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
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
