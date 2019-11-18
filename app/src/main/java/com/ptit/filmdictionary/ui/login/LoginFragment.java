package com.ptit.filmdictionary.ui.login;

import android.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.databinding.FragmentLoginBinding;
import com.ptit.filmdictionary.ui.main.MainActivity;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dmax.dialog.SpotsDialog;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private static LoginFragment sInstance;
    private FragmentLoginBinding mBinding;
    private AlertDialog mDialog;

    @Inject
    PreferenceUtil mPreferenceUtil;

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
            if (data != null && mDialog.isShowing()) {
                Toast.makeText(getActivity(), "Welcome " + data.getUserName(), Toast.LENGTH_SHORT).show();
                mPreferenceUtil.setEmail(data.getLocal().getEmail());
                mPreferenceUtil.setUserName(data.getUserName());
                mPreferenceUtil.setPassword(data.getLocal().getPassword());
                mPreferenceUtil.setUserId(data.getId());
                mPreferenceUtil.setUserAvatar(data.getAvatar());
                mDialog.dismiss();
                MainActivity.start(getActivity());
            }
        });
        mViewModel.getLiveLoginFail().observe(this, data -> {
            mDialog.dismiss();
            if (data != null && mDialog.isShowing()) {
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {
        mBinding.buttonSignIn.setOnClickListener(this);
        mBinding.textUserName.setText("axitpicric@gmail.com");
        mBinding.textPassword.setText("Aa@123456");
        mBinding.textSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                login();
                break;
            case R.id.text_sign_up:
                openRegister();
                break;
        }
    }

    private void openRegister() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.root_layout, RegisterFragment.newInstance()).addToBackStack(null).commit();
    }

    private void login() {
        String username = mBinding.textUserName.getText().toString();
        String password = mBinding.textPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please input your username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.SpotDialogCustom)
                .setMessage("Logging in")
                .setCancelable(false)
                .build();
        mDialog.show();
        mViewModel.login(username, password);
    }
}
