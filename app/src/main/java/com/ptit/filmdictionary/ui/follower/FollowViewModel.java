package com.ptit.filmdictionary.ui.follower;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.data.repository.AuthRepository;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FollowViewModel extends ViewModel {
    private MutableLiveData<List<UserResponse>> mLiveFollowers = new MutableLiveData<>();
    private MutableLiveData<List<UserResponse>> mLiveFollowing = new MutableLiveData<>();
    private AuthRepository mAuthRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public FollowViewModel(AuthRepository authRepository) {
        mAuthRepository = authRepository;
    }

    public MutableLiveData<List<UserResponse>> getLiveFollowers() {
        return mLiveFollowers;
    }

    public MutableLiveData<List<UserResponse>> getLiveFollowing() {
        return mLiveFollowing;
    }

    public void getFollower(String userId, String page) {
        Disposable disposable = mAuthRepository.userFollower(userId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            mLiveFollowers.postValue(data.getData());
                        },
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    public void getFollowing(String userId, String page) {
        Disposable disposable = mAuthRepository.userFollowing(userId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            mLiveFollowing.postValue(data.getData());
                        },
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }
}
