package com.ptit.filmdictionary.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.repository.FeedRepository;
import com.ptit.filmdictionary.ui.feed.CardType;
import com.ptit.filmdictionary.ui.feed.card.card_review.ReviewType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private FeedRepository mFeedRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<BaseFeed>> mLiveFeedProfile = new MutableLiveData<>();
    private MutableLiveData<List<BaseFeed>> mLiveFeedMovieProfile = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(FeedRepository feedRepository) {
        mFeedRepository = feedRepository;
    }

    public MutableLiveData<List<BaseFeed>> getLiveFeedProfile() {
        return mLiveFeedProfile;
    }

    public MutableLiveData<List<BaseFeed>> getLiveMovieFeedProfile() {
        return mLiveFeedMovieProfile;
    }

    public void loadFeedProfile(String userId, int page) {
        Disposable disposable = mFeedRepository.loadFeedProfile(userId, page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadFeedProfile: size", data.getData().size() + "");
                    List<BaseFeed> baseFeeds = data.getData();
                    if (baseFeeds.size() > 0) {
                        mLiveFeedProfile.setValue(data.getData());
                        filterData(data.getData());
                    } else {
                        mLiveFeedProfile.setValue(data.getData());
                    }

                }, throwable -> {
                    Log.d("loadFeedProfile: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    private void filterData(List<BaseFeed> data) {
        List<BaseFeed> filter = new ArrayList<>();
        for (BaseFeed baseFeed: data) {
            if (baseFeed.getCardType() == CardType.CARD_MOVIE) {
                filter.add(baseFeed);
            }
        }
        mLiveFeedMovieProfile.setValue(filter);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

}
