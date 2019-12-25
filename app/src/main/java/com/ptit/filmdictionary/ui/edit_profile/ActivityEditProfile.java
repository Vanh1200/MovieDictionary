package com.ptit.filmdictionary.ui.edit_profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.ActivityEditProfileBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class ActivityEditProfile extends AppCompatActivity {
    private ActivityEditProfileBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        mBinding.buttonSignUp.setOnClickListener(view -> {
            if (isValidateField()) {
                new Handler().postDelayed(() -> {
                    Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show();
                    setResult(123, new Intent().putExtra("fullname", mBinding.textFullName.getText().toString()));
                    finish();
                }, 500);

            } else {
                Toast.makeText(this, "Please input full information", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean isValidateField() {
        return !TextUtils.isEmpty(mBinding.textEmail.getText().toString()) &&
                !TextUtils.isEmpty(mBinding.textFullName.getText().toString()) &&
                !TextUtils.isEmpty(mBinding.textRePassword.getText().toString()) &&
                !TextUtils.isEmpty(mBinding.textPassword.getText().toString());
    }
}
