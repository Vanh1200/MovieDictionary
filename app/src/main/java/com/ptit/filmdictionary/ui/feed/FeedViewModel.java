package com.ptit.filmdictionary.ui.feed;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.repository.FeedRepository;
import com.ptit.filmdictionary.ui.feed.card.card_review.ReviewType;

import java.util.List;
import java.util.Random;

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

    public void loadFeed(String userId, int page) {
        Disposable disposable = mFeedRepository.loadFeed(userId, page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadFeed: size", data.getData().size() + "");
                    List<BaseFeed> baseFeeds = data.getData();
                    BaseFeed baseFeed = baseFeeds.get(baseFeeds.size() - 1);
                    baseFeed.setCardType(CardType.CARD_REVIEW);
                    if (new Random().nextInt(2) == 1) {
                        baseFeed.setReviewType(ReviewType.TYPE_REVIEW);
                    } else {
                        baseFeed.setReviewType(ReviewType.TYPE_PLAN);
                    }
                    Movie movie = new Movie();
                    movie.setOverview("The failed coup d'état of February 23, 1981, which began with the capture of the Congress of Deputies and ended with the release of parliamentarians, put at serious risk the Spanish democracy.");
                    movie.setReleaseDate("2011-02-23");
                    movie.setVoteAverage(5.6);
                    movie.setTitle("23-F: la película");
                    movie.setBackdropPath("https://image.tmdb.org/t/p/w500//veDMW7eX6tat86EapsvGEICJ8Tq.jpg");
                    movie.setId(101411);
                    baseFeed.setMovie(movie);
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
