package com.ptit.filmdictionary.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ptit.filmdictionary.data.repository.LoginRepository;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.response.LoginResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vanh1200 on 15/10/2019
 */
public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private LoginRepository mLoginRepository;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    private MutableLiveData<LoginResponse> mLiveLoginResponse = new MutableLiveData<>();

    public MutableLiveData<LoginResponse> getLiveLoginResponse() {
        return mLiveLoginResponse;
    }

    @Inject
    public LoginViewModel(LoginRepository loginRepository) {
        mLoginRepository = loginRepository;
    }

    public void login(String username, String password) {
        Disposable disposable = mLoginRepository.login(new LoginBody(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    mLiveLoginResponse.setValue(data.getData());
                }, throwable -> {
                    Log.d(TAG, throwable.toString());
                });
        mDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
