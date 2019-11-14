package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.MessageBody;
import com.ptit.filmdictionary.data.source.remote.request.MessageRequest;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.data.source.remote.response.FileResponse;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("api/messages/get")
    Single<BaseResponse<List<MessageResponse>>> getMessageByTwoUserId(@Body MessageRequest request, @Query("page") int page);

    @POST("api/messages/create")
    Single<BaseResponse<MessageResponse>> sendMessage(@Body MessageRequest messageRequest);

    @Multipart
    @POST("api/file")
    Single<FileResponse> uploadFile(@Part("file") RequestBody description, @Part MultipartBody.Part file);

    @GET("api/user/search/{userId}")
    Single<BaseResponse<List<UserResponse>>> searchUser(@Path("userId") String userId, @Query("q") String query, @Query("page") String page);

    @GET("api/users/{userId}")
    Single<BaseResponse<UserResponse>> getUser(@Path("userId") String userId);

    @GET("api/posts/{userId}")
    Single<BaseResponse<List<BaseFeed>>> loadFeed(@Path("userId") String userId, @Query("page") String page);

    @POST("api/posts/create/{userId}")
    Single<BaseResponse<BaseFeed>> createPost(@Path("userId") String userId, @Body BaseFeed baseFeed);

    @GET("api/posts/profile/{userId}")
    Single<BaseResponse<List<BaseFeed>>> loadFeedProfile(@Path("userId") String userId, @Query("page") String page);

    // like

    // follow

}
