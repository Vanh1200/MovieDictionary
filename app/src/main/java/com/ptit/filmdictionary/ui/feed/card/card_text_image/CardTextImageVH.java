package com.ptit.filmdictionary.ui.feed.card.card_text_image;

import android.view.View;

import androidx.annotation.NonNull;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;
import com.ptit.filmdictionary.databinding.CardTextImageBinding;
import com.ptit.filmdictionary.ui.feed.FeedCallback;

public class CardTextImageVH extends BaseVH implements View.OnClickListener {
    private CardTextImageBinding mBinding;
    private CardTextImage mCardTextImage;
    private FeedCallback mCallback;

    public CardTextImageVH(@NonNull CardTextImageBinding binding, FeedCallback callback) {
        super(binding);
        mBinding = binding;
        mCallback = callback;
        initListeners();
    }

    @Override
    public void bindData(BaseFeed baseFeed) {
        mCardTextImage = (CardTextImage) baseFeed;
    }

    private void initListeners() {
        mBinding.layoutHeader.getRoot().setOnClickListener(this);
        mBinding.layoutFooter.layoutComment.setOnClickListener(this);
        mBinding.layoutFooter.layoutHeart.setOnClickListener(this);
        mBinding.imageContent.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_header:
                if (mCallback != null) {
                    mCallback.onClickUser(mCardTextImage.getUser(), getAdapterPosition() - 1);
                }
                break;
            case R.id.layout_heart:
                if (mCallback != null) {
                    mCallback.onClickHeart(mCardTextImage, getAdapterPosition() - 1);
                }
                break;
            case R.id.layout_comment:
                if (mCallback != null) {
                    mCallback.onClickComment(mCardTextImage, getAdapterPosition() - 1);
                }
                break;
            case R.id.image_content:
                if (mCallback != null) {
                    mCallback.onClickImage(mCardTextImage, getAdapterPosition() - 1);
                }
                break;
            default:
                break;
        }
    }
}
