package com.ptit.filmdictionary.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.base.BaseErrorResponse;
import com.ptit.filmdictionary.data.repository.AuthRepository;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.utils.NetworkUtils;

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

    private AuthRepository mAuthRepository;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    private MutableLiveData<UserResponse> mLiveLoginResponse = new MutableLiveData<>();
    private MutableLiveData<String> mLiveLoginFail = new MutableLiveData<>();

    public MutableLiveData<UserResponse> getLiveLoginResponse() {
        return mLiveLoginResponse;
    }

    public MutableLiveData<String> getLiveLoginFail() {
        return mLiveLoginFail;
    }

    @Inject
    public LoginViewModel(AuthRepository authRepository) {
        mAuthRepository = authRepository;
    }

    public void login(String username, String password) {
        Disposable disposable = mAuthRepository.login(new LoginBody(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> mLiveLoginResponse.setValue(data.getData()),
                        throwable -> {
                    String error = throwable.toString();
                    BaseErrorResponse errorResponse = NetworkUtils.getErrorResponse(throwable);
                    if (errorResponse != null) {
                        error = errorResponse.getDescription();
                    }
                    mLiveLoginFail.setValue(error);
                });
        mDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
