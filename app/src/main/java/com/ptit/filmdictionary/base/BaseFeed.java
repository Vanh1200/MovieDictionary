package com.ptit.filmdictionary.base;

import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.feed.CardType;

public class BaseFeed {
    @SerializedName("id")
    private String id;

    @SerializedName("cardType")
    @CardType
    private int cardType;

    @SerializedName("user")
    private UserResponse user;

    @SerializedName("numHeart")
    private int numHeart;

    @SerializedName("numComment")
    private int numComment;

    @SerializedName("createdAt")
    private int createdAt;

    @SerializedName("updatedAt")
    private int updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public int getNumHeart() {
        return numHeart;
    }

    public void setNumHeart(int numHeart) {
        this.numHeart = numHeart;
    }

    public int getNumComment() {
        return numComment;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }
}
