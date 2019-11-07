package com.ptit.filmdictionary.ui.feed;

import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImage;

public interface FeedCallback {

    interface CreatePostItemCallback {
        void onClickImageCreatePost();
        void onClickVideoCreatePost();
        void onClickReviewCreatePost();
        void onClickAvatarCreatePost();
        void onClickTextCrreatePost();
    }



//    interface CardTextImageCallback {
//        void onClickItemTextImage(CardTextImage item);
//        void
//    }

}
