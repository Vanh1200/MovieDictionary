package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class CommentResponse {
    @SerializedName("updatedAt")
    private long updatedAt;

    @SerializedName("deletedAt")
    private long deletedAt;

    @SerializedName("_id")
    private String id;

    @SerializedName("trailerId")
    private String trailerId;

    @SerializedName("user")
    private UserResponse user;

    @SerializedName("content")
    private String content;

    @SerializedName("createdAt")
    private long createdAt;

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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
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
