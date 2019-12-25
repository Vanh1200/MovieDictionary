package com.ptit.filmdictionary.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.feed.CardType;
import com.ptit.filmdictionary.ui.feed.card.card_review.ReviewType;

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
    private String movie;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("reviewType")
    private int reviewType;

    @SerializedName("isLike")
    private boolean isLike;

    public Movie getMovieObject() {
        try {
            return new Gson().fromJson(movie, Movie.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReviewType() {
        try {
            return new Gson().fromJson(movie, Movie.class).getReviewType();
        } catch (Exception e) {
            return ReviewType.TYPE_PLAN;
        }
    }

    public void setReviewType(int reviewType) {
        this.reviewType = reviewType;
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
