package com.ptit.filmdictionary.data.source.remote.request;

import com.google.gson.annotations.SerializedName;

public class FollowBody {
    @SerializedName("userId")
    private String userId;

    @SerializedName("opponentUserId")
    private String opponentUserId;

    @SerializedName("isFollow")
    private boolean isFollow;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpponentUserId() {
        return opponentUserId;
    }

    public void setOpponentUserId(String opponentUserId) {
        this.opponentUserId = opponentUserId;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
