package com.ptit.filmdictionary.ui.feed.card.card_create_post;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

public class CardCreatePost extends BaseFeed {
    private UserResponse mUserResponse;

    public CardCreatePost(){

    }

    public CardCreatePost(UserResponse userResponse) {
        mUserResponse = userResponse;
    }

    public UserResponse getUserResponse() {
        return mUserResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        mUserResponse = userResponse;
    }
}
