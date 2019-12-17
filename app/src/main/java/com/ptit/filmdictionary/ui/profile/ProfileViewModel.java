package com.ptit.filmdictionary.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseResponse;
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
                        BaseFeed baseFeed = baseFeeds.get(baseFeeds.size() - 1);
                        baseFeed.setCardType(CardType.CARD_REVIEW);
                        if (new Random().nextInt(2) == 1) {
                            baseFeed.setReviewType(ReviewType.TYPE_REVIEW);
                            baseFeed.setText("Bộ phim hay nhất từng xem là đâyyyy");
                        } else {
                            baseFeed.setReviewType(ReviewType.TYPE_PLAN);
                            baseFeed.setText("Phim này có hay không hả mọi người ơiii");
                        }
                        Movie movie = new Movie();
                        movie.setOverview("The failed coup d'état of February 23, 1981, which began with the capture of the Congress of Deputies and ended with the release of parliamentarians, put at serious risk the Spanish democracy.");
                        movie.setReleaseDate("2011-02-23");
                        movie.setVoteAverage(5.6);
                        movie.setTitle("23-F: la película");
                        movie.setBackdropPath("https://image.tmdb.org/t/p/w500//veDMW7eX6tat86EapsvGEICJ8Tq.jpg");
                        movie.setId(101411);
                        baseFeed.setMovie(movie);
                        mLiveFeedProfile.setValue(data.getData());
                        fakeData(data.getData());
                    } else {
                        mLiveFeedProfile.setValue(data.getData());
                    }

                }, throwable -> {
                    Log.d("loadFeedProfile: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    private void fakeData(List<BaseFeed> data) {
        List<BaseFeed> fake = new ArrayList<>();
        for (BaseFeed baseFeed: data) {
            if (baseFeed.getCardType() == CardType.CARD_REVIEW) {
                fake.add(baseFeed);
            }
        }
        mLiveFeedMovieProfile.setValue(fake);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

}
