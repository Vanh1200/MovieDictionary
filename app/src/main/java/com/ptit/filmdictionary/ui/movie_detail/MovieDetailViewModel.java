package com.ptit.filmdictionary.ui.movie_detail;

import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import android.util.Log;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.repository.CommentRepository;
import com.ptit.filmdictionary.data.repository.MovieRepository;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.ui.main.OnInternetListener;

import java.util.List;

import io.reactivex.Observable;
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

    private MutableLiveData<List<CommentResponse>> mLiveComments = new MutableLiveData<>();
    private MutableLiveData<String> mLivePostComment = new MutableLiveData<>();

    public MutableLiveData<List<CommentResponse>> getLiveComments() {
        return mLiveComments;
    }

    public MutableLiveData<String> getLivePostComment() {
        return mLivePostComment;
    }

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

    public void loadComments(String movieId) {
        Disposable disposable = mCommentRepository.getCommentsByTrailerId(movieId, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadComment: size", data.getData().size() + "");
                    mLiveComments.setValue(data.getData());
                }, throwable -> {
                    Log.d("loadComment: error", throwable.toString());
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
