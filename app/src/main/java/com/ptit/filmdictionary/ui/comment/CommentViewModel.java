package com.ptit.filmdictionary.ui.comment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.ptit.filmdictionary.data.repository.CommentRepository;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vanh1200 on 23/10/2019
 */
public class CommentViewModel extends ViewModel {
    private CommentRepository mCommentRepository;
    private CompositeDisposable mCompositeDisposable;
    private MutableLiveData<List<CommentResponse>> mLiveComments = new MutableLiveData<>();
    private MutableLiveData<CommentResponse> mLivePostComment = new MutableLiveData<>();

    @Inject
    public CommentViewModel(CommentRepository commentRepository) {
        mCommentRepository = commentRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<List<CommentResponse>> getLiveComments() {
        return mLiveComments;
    }

    public MutableLiveData<CommentResponse> getLivePostComment() {
        return mLivePostComment;
    }

    public void loadComments(int movieId) {
        Disposable disposable = mCommentRepository.getCommentsByTrailerId(movieId+"", 0)
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

    public void postComments(int movieId, CommentBody body) {
        Disposable disposable = mCommentRepository.createComment(movieId+"", body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("postComment: ", data.getData().getContent());
                    mLivePostComment.setValue(data.getData());
                }, throwable -> {
                    Log.d("postComment: error", throwable.toString());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
