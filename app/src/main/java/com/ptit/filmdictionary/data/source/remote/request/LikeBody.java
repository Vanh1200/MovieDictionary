package com.ptit.filmdictionary.data.source.remote.request;

import com.google.gson.annotations.SerializedName;

public class LikeBody {
    @SerializedName("userId")
    private String userId;

    @SerializedName("postId")
    private String postId;

    @SerializedName("isLike")
    private boolean isLike;

    public LikeBody(String userId, String postId, boolean isLike) {
        this.userId = userId;
        this.postId = postId;
        this.isLike = isLike;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
