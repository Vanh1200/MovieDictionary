package com.ptit.filmdictionary.data.source;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseResponse;

import java.util.List;

import io.reactivex.Single;

public interface FeedDataSource {
    interface Remote {
        Single<BaseResponse<List<BaseFeed>>> loadFeed(String userId, String page);

        Single<BaseResponse<List<BaseFeed>>> loadFeedProfile(String userId, String page);

        Single<BaseResponse<BaseFeed>> createPost(String userId, BaseFeed baseFeed);
    }
}
