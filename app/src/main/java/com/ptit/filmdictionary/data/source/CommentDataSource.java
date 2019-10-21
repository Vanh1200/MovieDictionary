package com.ptit.filmdictionary.data.source;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 14/10/2019
 */
public interface CommentDataSource {
    interface Local {

    }

    interface Remote {
        Single<BaseResponse<List<CommentResponse>>> getCommentsByTrailerId(String trailerId, int page);

        Single<BaseResponse<String>> createComment(String trailerId, CommentBody commentBody);
    }
}
