package com.ptit.filmdictionary.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentLoginBinding;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private static LoginFragment sInstance;
    private FragmentLoginBinding mBinding;
    @Inject
    ViewModelFactory mViewModelFactory;

    private LoginViewModel mViewModel;

    public static LoginFragment getInstance() {
        if (sInstance == null) {
            sInstance = new LoginFragment();
        }
        return sInstance;
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
        mBinding.setViewModel(mViewModel);
        initListeners();
        initObservers();
        return mBinding.getRoot();
    }

    private void initObservers() {
        mViewModel.getLiveLoginResponse().observe(this, data -> {
            Toast.makeText(getActivity(), data.getAvatar() , Toast.LENGTH_SHORT).show();
        });
    }

    private void initListeners() {
        mBinding.buttonSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                login();
                break;
        }
    }

    private void login() {
        String username = mBinding.textUserName.getText().toString();
        String password = mBinding.textPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please input your username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        mViewModel.login(username, password);
    }
}
