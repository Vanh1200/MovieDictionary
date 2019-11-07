package com.ptit.filmdictionary.ui.login;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
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
        setTheme(R.style.TransparentStatusTheme);
        AndroidInjection.inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //add tam thang login
        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, LoginFragment.getInstance()).commit();

    }

}
