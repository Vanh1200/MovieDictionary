package com.ptit.filmdictionary.base;

import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.feed.CardType;

import java.util.List;

public class BaseFeed {
    @SerializedName("_id")
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
    private long createdAt;

    @SerializedName("updatedAt")
    private long updatedAt;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("text")
    private String text;

    @SerializedName("trailerId")
    private String trailerId;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("isLike")
    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
