package com.ptit.filmdictionary.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentRegisterBinding;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBinding mBinding;
    private LoginViewModel mViewModel;

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

    }

    private void initListeners() {
        mBinding.imageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
