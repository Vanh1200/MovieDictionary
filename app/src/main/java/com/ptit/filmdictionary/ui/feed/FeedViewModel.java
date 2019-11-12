package com.ptit.filmdictionary.ui.feed;

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

public class FeedViewModel extends ViewModel {
    private FeedRepository mFeedRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<BaseFeed>> mLiveFeed = new MutableLiveData<>();

    @Inject
    public FeedViewModel(FeedRepository feedRepository) {
        mFeedRepository = feedRepository;
    }

    public MutableLiveData<List<BaseFeed>> getLiveFeed() {
        return mLiveFeed;
    }

    public void loadFeed(String userId) {
        Disposable disposable = mFeedRepository.loadFeed(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadFeed: size", data.getData().size() + "");
                    mLiveFeed.setValue(data.getData());
                }, throwable -> {
                    Log.d("loadFeed: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }
}
