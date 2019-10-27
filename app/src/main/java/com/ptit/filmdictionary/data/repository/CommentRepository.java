package com.ptit.filmdictionary.data.repository;

import android.content.Context;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.CommentDataSource;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class CommentRepository implements CommentDataSource.Remote {
    private static CommentRepository sInstance;
    private CommentDataSource.Remote mRemote;

    public static CommentRepository getInstance(CommentDataSource.Remote remote) {
        if (sInstance == null) {
            sInstance = new CommentRepository(remote);
        }
        return sInstance;
    }

    @Inject
    public CommentRepository(CommentDataSource.Remote remote) {
        mRemote = remote;
    }


    @Override
    public Single<BaseResponse<List<CommentResponse>>> getCommentsByTrailerId(String trailerId, int page) {
        return mRemote.getCommentsByTrailerId(trailerId, page);
    }

    @Override
    public Single<BaseResponse<String>> createComment(String trailerId, CommentBody commentBody) {
        return mRemote.createComment(trailerId, commentBody);
    }
}
