package com.ptit.filmdictionary.ui.create_post;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.repository.FeedRepository;
import com.ptit.filmdictionary.data.repository.FileRepository;
import com.ptit.filmdictionary.data.source.remote.response.FileResponse;
import com.ptit.filmdictionary.utils.FileUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vanh1200 on 10/11/2019
 */
public class CreatePostViewModel extends ViewModel {
    private FileRepository mFileRepository;
    private FeedRepository mFeedRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private MutableLiveData<FileResponse> mLiveFileRes = new MutableLiveData<>();
    private MutableLiveData<BaseFeed> mLiveCreatePost = new MutableLiveData<>();

    public MutableLiveData<BaseFeed> getLiveCreatePost() {
        if (mLiveCreatePost == null) {
            mLiveCreatePost = new MutableLiveData<>();
        }
        return mLiveCreatePost;
    }

    public MutableLiveData<FileResponse> getLiveFileRes() {
        return mLiveFileRes;
    }

    @Inject
    public CreatePostViewModel(FileRepository fileRepository, FeedRepository feedRepository) {
        mFileRepository = fileRepository;
        mFeedRepository = feedRepository;
    }

    public void uploadFile(String path) {
        MultipartBody.Part body = FileUtils.prepareFilePart("file", path);
        RequestBody description = FileUtils.createPartFromString("cai nay chi de cho vui");
        Disposable disposable = mFileRepository.uploadFile(description, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> mLiveFileRes.postValue(data),
                        throwable -> {});
        mDisposable.add(disposable);
    }

    public void createPost(String userId, BaseFeed baseFeed) {
        Disposable disposable = mFeedRepository.createPost(userId, baseFeed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("loadFeed: create post", data.getData().getId() + "");
                    mLiveCreatePost.setValue(data.getData());
                }, throwable -> {
                    Log.d("loadFeed: error", throwable.toString());
                });
        mDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }
}
