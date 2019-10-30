package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSecondRequest {

    @GET("api/comments/{trailerId}")
    Single<BaseResponse<List<CommentResponse>>> getCommentsByTrailerId(@Path("trailerId") String trailerId, @Query("page") int page);

    @POST("api/comments/{trailerId}")
    Single<BaseResponse<CommentResponse>> createComment(@Path("trailerId") String trailerId, @Body CommentBody commentBody);

    @POST("api/login")
    Single<BaseResponse<UserResponse>> login (@Body LoginBody body);

    @POST("api/register")
    Single<BaseResponse<String>> register (@Body RegisterBody body);

}
