package com.ptit.filmdictionary.data.repository;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.FeedDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FeedRepository implements FeedDataSource.Remote {
    private FeedDataSource.Remote mRemote;

    @Inject
    public FeedRepository(FeedDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public Single<BaseResponse<List<BaseFeed>>> loadFeed(String userId) {
        return mRemote.loadFeed(userId);
    }

    @Override
    public Single<BaseResponse<BaseFeed>> createPost(String userId, BaseFeed baseFeed) {
        return mRemote.createPost(userId, baseFeed);
    }
}
