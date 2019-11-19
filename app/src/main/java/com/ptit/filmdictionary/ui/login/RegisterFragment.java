package com.ptit.filmdictionary.ui.login;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentRegisterBinding;
import com.ptit.filmdictionary.utils.BaseHelper;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dmax.dialog.SpotsDialog;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBinding mBinding;
    private LoginViewModel mViewModel;
    private AlertDialog mDialog;

    @Inject
    ViewModelFactory mViewModelFactory;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        initListeners();
        observeData();
        return mBinding.getRoot();
    }

    private void observeData() {
        mViewModel.getLiveRegisterResponse().observe(this, data -> {
            if (data != null && mDialog.isShowing()) {
                mDialog.dismiss();
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

            }
        });

        mViewModel.getLiveRegisterFailed().observe(this, data -> {
            if (data != null && mDialog.isShowing()) {
                mDialog.dismiss();
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {
        mBinding.imageBack.setOnClickListener(this);
        mBinding.buttonSignUp.setOnClickListener(this);
        mDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.SpotDialogCustom)
                .setMessage("Signing in")
                .setCancelable(false)
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.button_sign_up:
                handleSignUp();
                break;
        }
    }

    private void handleSignUp() {
        if (!BaseHelper.isInternetOn(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.text_no_internet), Toast.LENGTH_SHORT).show();
            return;
        }
        String email = mBinding.textEmail.getText().toString();
        String password = mBinding.textPassword.getText().toString();
        String rePassword = mBinding.textRePassword.getText().toString();
        String fullname = mBinding.textFullName.getText().toString();
        String gender;
        if (mBinding.buttonMale.isChecked()) {
            gender = "male";
        } else {
            gender = "female";
        }
        if (email.isEmpty() || password.isEmpty() || rePassword.isEmpty() || fullname.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.signup_empty_field), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(getActivity(), getString(R.string.signup_password_not_match), Toast.LENGTH_SHORT).show();
            return;
        }
        mViewModel.register(email, password, gender);
        mDialog.show();
    }
}
