package com.ptit.filmdictionary.ui.login;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.ActivityLoginBinding;

import dagger.android.AndroidInjection;

/**
 * Created by vanh1200 on 15/10/2019
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        //add tam thang login
        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, LoginFragment.getInstance()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

}
