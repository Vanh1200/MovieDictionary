package com.ptit.filmdictionary.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.repository.FeedRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private FeedRepository mFeedRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<BaseFeed>> mLiveFeedProfile = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(FeedRepository feedRepository) {
        mFeedRepository = feedRepository;
    }

    public MutableLiveData<List<BaseFeed>> getLiveFeedProfile() {
        return mLiveFeedProfile;
    }

    public void loadFeedProfile(String userId, int page) {
        Disposable disposable = mFeedRepository.loadFeedProfile(userId, page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadFeedProfile: size", data.getData().size() + "");
                    mLiveFeedProfile.setValue(data.getData());
                }, throwable -> {
                    Log.d("loadFeedProfile: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

}
