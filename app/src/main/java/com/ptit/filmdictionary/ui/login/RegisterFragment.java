package com.ptit.filmdictionary.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentRegisterBinding;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBinding mBinding;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        initListeners();
        return mBinding.getRoot();
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
