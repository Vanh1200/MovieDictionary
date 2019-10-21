package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class CommentResponse {
    @SerializedName("userAvatarUrl")
    private String userAvatarUrl;

    @SerializedName("updatedAt")
    private long updatedAt;

    @SerializedName("deletedAt")
    private long deletedAt;

    @SerializedName("_id")
    private String id;

    @SerializedName("trailerId")
    private String trailerId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("userName")
    private String userName;

    @SerializedName("content")
    private String content;

    @SerializedName("createAt")
    private long createdAt;

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
