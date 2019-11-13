package com.ptit.filmdictionary.ui.feed.card.card_create_post;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.CardCreatePostBinding;
import com.ptit.filmdictionary.ui.feed.FeedCallback;
import com.ptit.filmdictionary.utils.ImageHelper;

public class CardCreatePostVH extends BaseVH implements View.OnClickListener {
    private CardCreatePostBinding mBinding;
    private FeedCallback mCallback;
    private UserResponse mUserInfo;

    public CardCreatePostVH(@NonNull CardCreatePostBinding binding, FeedCallback callback) {
        super(binding);
        mBinding = binding;
        mCallback = callback;
        initListeners();
    }

    public void setUserInfo(UserResponse userInfo) {
        mUserInfo = userInfo;
        ImageHelper.loadImage(mBinding.imageAvatar, mUserInfo.getAvatar());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_post_review:
                if(mCallback != null) {
                    mCallback.onClickReviewCreatePost();
                }
                break;
            case R.id.layout_post_image:
                if(mCallback != null) {
                    mCallback.onClickImageCreatePost();
                }
                break;
            case R.id.layout_post_video:
                if(mCallback != null) {
                    mCallback.onClickVideoCreatePost();
                }
                break;

            case R.id.image_avatar:
                if(mCallback != null) {
                    mCallback.onClickAvatarCreatePost();
                }
                break;
            case R.id.text_question:
                if(mCallback != null) {
                    mCallback.onClickTextCreatePost();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void bindData(BaseFeed baseFeed) {

    }

    public void initListeners() {
        mBinding.layoutPostImage.setOnClickListener(this);
        mBinding.layoutPostReview.setOnClickListener(this);
        mBinding.layoutPostVideo.setOnClickListener(this);
        mBinding.imageAvatar.setOnClickListener(this);
        mBinding.textQuestion.setOnClickListener(this);
    }
}
