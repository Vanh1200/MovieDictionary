package com.ptit.filmdictionary.ui.movie_detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.repository.CommentRepository;
import com.ptit.filmdictionary.data.repository.MovieRepository;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.ui.main.OnInternetListener;

import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailViewModel {
    public final ObservableField<Movie> mMovie = new ObservableField<>();
    public final ObservableBoolean mShowProgress = new ObservableBoolean(true);
    public final ObservableBoolean mIsFavorite = new ObservableBoolean(false);
    private MovieRepository mMovieRepository;
    private CommentRepository mCommentRepository;
    private CompositeDisposable mCompositeDisposable;
    private OnTrailerListener mListener;
    private OnInternetListener mInternetListener;
    private OnFavoriteListener mFavoriteListener;

    public MovieDetailViewModel(MovieRepository repository, CommentRepository commentRepository,
                                OnTrailerListener listener) {
        mMovieRepository = repository;
        mCommentRepository = commentRepository;
        mListener = listener;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setInternetListener(OnInternetListener internetListener) {
        mInternetListener = internetListener;
    }

    public void setFavoriteListener(OnFavoriteListener favoriteListener) {
        mFavoriteListener = favoriteListener;
    }

    public void loadMovieDetail(int movieId) {
        Disposable disposable = mMovieRepository.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    mMovie.set(movie);
                    mShowProgress.set(false);
                    if (!movie.getVideoResult().getVideos().isEmpty()) {
                        mListener.onCreateTrailer(movie.getVideoResult().getVideos().get(0).getKey());
                    }
                }, throwable -> {
                    if (mInternetListener != null) mInternetListener.onNoInternet();
                });
        mCompositeDisposable.add(disposable);

        checkFavorite(movieId);
    }

    public void loadComments(int movieId) {
        Disposable disposable = mCommentRepository.getCommentsByTrailerId(movieId+"", 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadComment: size", data.getData().size() + "");
                }, throwable -> {
                    Log.d("loadComment: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    public void postComments(int movieId, CommentBody body) {
        Disposable disposable = mCommentRepository.createComment(movieId+"", body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("postComment: size", data.getData());
                }, throwable -> {
                    Log.d("postComment: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    public void checkFavorite(int movieId) {
        Disposable disposable = Observable.defer(() -> Observable.just(mMovieRepository.isFavorite(movieId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mIsFavorite.set(aBoolean);
                });
        mCompositeDisposable.add(disposable);
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

    public void changeFavorite() {
        mIsFavorite.set(!mIsFavorite.get());
        if (mIsFavorite.get()) {
            mMovieRepository.addFavorite(mMovie.get());
        } else {
            mMovieRepository.deleteFavorite(mMovie.get());
        }
        mFavoriteListener.onFavoriteClick(mIsFavorite.get());
    }

    public interface OnFavoriteListener {
        void onFavoriteClick(boolean isFavorite);
    }
}
