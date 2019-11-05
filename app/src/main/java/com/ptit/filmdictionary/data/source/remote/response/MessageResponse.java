package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("sender")
    private UserResponse sernder;

    @SerializedName("receiver")
    private UserResponse receiver;

    @SerializedName("text")
    private String text;

    @SerializedName("createdAt")
    private long createdAt;

    @SerializedName("type")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserResponse getSernder() {
        return sernder;
    }

    public void setSernder(UserResponse sernder) {
        this.sernder = sernder;
    }

    public UserResponse getReceiver() {
        return receiver;
    }

    public void setReceiver(UserResponse receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
