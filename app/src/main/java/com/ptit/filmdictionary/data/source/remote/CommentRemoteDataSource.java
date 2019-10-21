package com.ptit.filmdictionary.data.source.remote;

import android.content.Context;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.CommentDataSource;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class CommentRemoteDataSource implements CommentDataSource.Remote {
    private static CommentRemoteDataSource sCommentRemoteDataSource;
    private ApiSecondRequest mApiRequest;

    private CommentRemoteDataSource(ApiSecondRequest apiRequest) {
        mApiRequest = apiRequest;
    }

    public static CommentRemoteDataSource getInstance(Context context) {
        if (sCommentRemoteDataSource == null) {
            sCommentRemoteDataSource = new CommentRemoteDataSource(NetworkService.getSecondInstance(context));
        }
        return sCommentRemoteDataSource;
    }

    @Override
    public Single<BaseResponse<List<CommentResponse>>> getCommentsByTrailerId(String trailerId, int page) {
        return mApiRequest.getCommentsByTrailerId(trailerId, page);
    }

    @Override
    public Single<BaseResponse<String>> createComment(String trailerId, CommentBody commentBody) {
        return mApiRequest.createComment(trailerId, commentBody);
    }
}
