package com.ptit.filmdictionary.ui.feed;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImage;

public interface FeedCallback {

    void onClickImageCreatePost();
    void onClickVideoCreatePost();
    void onClickReviewCreatePost();
    void onClickAvatarCreatePost();
    void onClickTextCrreatePost();

    void onClickUser (UserResponse userResponse);
    void onClickHeart (BaseFeed item);
    void onClickComment (BaseFeed item);
    void onClickImage (BaseFeed item, int position);
    void onClickVideo (BaseFeed item);
    void onClickMovie (BaseFeed item);

}
