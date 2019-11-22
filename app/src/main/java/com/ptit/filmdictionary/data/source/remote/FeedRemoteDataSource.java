package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.FeedDataSource;
import com.ptit.filmdictionary.data.source.remote.ApiSecondRequest;
import com.ptit.filmdictionary.data.source.remote.request.FollowBody;
import com.ptit.filmdictionary.data.source.remote.request.LikeBody;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FeedRemoteDataSource implements FeedDataSource.Remote {
    private ApiSecondRequest mApiSecondRequest;

    @Inject
    public FeedRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        mApiSecondRequest = apiSecondRequest;
    }

    @Override
    public Single<BaseResponse<List<BaseFeed>>> loadFeed(String userId, String page) {
        return mApiSecondRequest.loadFeed(userId, page);
    }

    @Override
    public Single<BaseResponse<List<BaseFeed>>> loadFeedProfile(String userId, String page) {
        return mApiSecondRequest.loadFeedProfile(userId, page);
    }

    @Override
    public Single<BaseResponse<BaseFeed>> createPost(String userId, BaseFeed baseFeed) {
        return mApiSecondRequest.createPost(userId, baseFeed);
    }

    @Override
    public Single<BaseResponse<String>> likePost(String userId, String postId, boolean isLike) {
        return mApiSecondRequest.likePost(new LikeBody(userId, postId, isLike));
    }

    @Override
    public Single<BaseResponse<String>> followUser(String userId, String userIdFollow, boolean isFollow) {
        return mApiSecondRequest.followUser(new FollowBody(userId, userIdFollow, isFollow));
    }
}
