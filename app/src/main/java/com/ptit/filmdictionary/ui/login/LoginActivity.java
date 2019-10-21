package com.ptit.filmdictionary.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.repository.LoginRepository;
import com.ptit.filmdictionary.databinding.ActivityLoginBinding;
import com.ptit.filmdictionary.databinding.FragmentLoginBinding;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by vanh1200 on 15/10/2019
 */
public class LoginActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private ActivityLoginBinding mBinding;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //add tam thang login
        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, LoginFragment.getInstance()).commit();

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
