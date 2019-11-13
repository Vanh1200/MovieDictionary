package com.ptit.filmdictionary.ui.feed.card.card_text_image;

import android.view.View;

import androidx.annotation.NonNull;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;
import com.ptit.filmdictionary.databinding.CardTextImageBinding;
import com.ptit.filmdictionary.ui.feed.FeedCallback;
import com.ptit.filmdictionary.utils.BaseHelper;
import com.ptit.filmdictionary.utils.ImageHelper;

public class CardTextImageVH extends BaseVH implements View.OnClickListener {
    private CardTextImageBinding mBinding;
    private BaseFeed mBaseFeed;
    private FeedCallback mCallback;

    public CardTextImageVH(@NonNull CardTextImageBinding binding, FeedCallback callback) {
        super(binding);
        mBinding = binding;
        mCallback = callback;
        initListeners();
    }

    @Override
    public void bindData(BaseFeed baseFeed) {
        mBaseFeed = baseFeed;
        if (!baseFeed.getImageUrl().isEmpty()) {
            mBinding.imageContent.setVisibility(View.VISIBLE);
            ImageHelper.loadImage(mBinding.imageContent, baseFeed.getImageUrl());
        } else {
            mBinding.imageContent.setVisibility(View.GONE);
        }
        mBinding.textContent.setText(baseFeed.getText());
        ImageHelper.loadImage(mBinding.layoutHeader.imageAvatar, baseFeed.getUser().getAvatar());
        mBinding.layoutHeader.textUserName.setText(baseFeed.getUser().getUserName());
        mBinding.layoutHeader.textTimeAgo.setText(BaseHelper.convertTimeStampToTimeAgo(mBaseFeed.getCreatedAt()));
        mBinding.layoutFooter.textNumLike.setText(String.valueOf(baseFeed.getNumHeart()));
        mBinding.layoutFooter.textNumComment.setText(String.valueOf(baseFeed.getNumComment()));
        if (baseFeed.isLike()) {
            mBinding.layoutFooter.imageHeart.setImageResource(R.drawable.ic_red_heart);
        } else {
            mBinding.layoutFooter.imageHeart.setImageResource(R.drawable.ic_gray_heart);
        }
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
                    mCallback.onClickUser(mBaseFeed.getUser(), getAdapterPosition() - 1);
                }
                break;
            case R.id.layout_heart:
                if (mCallback != null) {
                    mCallback.onClickHeart(mBaseFeed, getAdapterPosition() - 1);
                }
                break;
            case R.id.layout_comment:
                if (mCallback != null) {
                    mCallback.onClickComment(mBaseFeed, getAdapterPosition() - 1);
                }
                break;
            case R.id.image_content:
                if (mCallback != null) {
                    mCallback.onClickImage(mBaseFeed, getAdapterPosition() - 1);
                }
                break;
            default:
                break;
        }
    }
}
