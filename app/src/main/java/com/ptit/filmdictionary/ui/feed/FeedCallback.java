package com.ptit.filmdictionary.ui.feed;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImage;

public interface FeedCallback {

    void onClickImageCreatePost();
    void onClickVideoCreatePost();
    void onClickReviewCreatePost();
    void onClickAvatarCreatePost();
    void onClickTextCreatePost();

    void onClickUser (UserResponse userResponse, int position);
    void onClickHeart (BaseFeed item, int position);
    void onClickComment (BaseFeed item, int position);
    void onClickImage (BaseFeed item, int position);
    void onClickVideo (BaseFeed item, int position);
    void onClickMovie (BaseFeed item, int position);

}
