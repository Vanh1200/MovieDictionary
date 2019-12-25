package com.ptit.filmdictionary.ui.feed.card.card_review;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;
import com.ptit.filmdictionary.databinding.CardReviewBinding;
import com.ptit.filmdictionary.ui.feed.FeedCallback;
import com.ptit.filmdictionary.utils.BaseHelper;
import com.ptit.filmdictionary.utils.ImageHelper;
import com.ptit.filmdictionary.utils.StringUtils;

public class CardReviewVH extends BaseVH implements View.OnClickListener {
    private CardReviewBinding mBinding;
    private BaseFeed mBaseFeed;
    private FeedCallback mCallback;

    public CardReviewVH(@NonNull CardReviewBinding binding, FeedCallback callback) {
        super(binding);
        mBinding = binding;
        mCallback = callback;
        initListeners();
    }

    @Override
    public void bindData(BaseFeed baseFeed) {
        mBaseFeed = baseFeed;
        mBinding.layoutHeader.textUserName.setMovementMethod(LinkMovementMethod.getInstance());
        if (baseFeed.getMovieObject() != null) {
            ImageHelper.loadImage(mBinding.layoutCard.imagePoster, StringUtils.getImage(baseFeed.getMovieObject().getPosterPath()));
            mBinding.layoutCard.textOverview.setText(baseFeed.getMovieObject().getOverview());
            mBinding.layoutCard.ratingBar.setRating((float) baseFeed.getMovieObject().getVoteAverage());
            mBinding.layoutCard.textTitle.setText(baseFeed.getMovieObject().getTitle());
            mBinding.layoutCard.textReleaseDate.setText(baseFeed.getMovieObject().getReleaseDate());
            setTitle(baseFeed);
        }
        mBinding.textContent.setText(baseFeed.getText());
        ImageHelper.loadImage(mBinding.layoutHeader.imageAvatar, baseFeed.getUser().getAvatar());
        mBinding.layoutHeader.textTimeAgo.setText(BaseHelper.convertTimeStampToTimeAgo(mBaseFeed.getCreatedAt()));
        mBinding.layoutFooter.textNumLike.setText(String.valueOf(baseFeed.getNumHeart()));
        mBinding.layoutFooter.textNumComment.setText(String.valueOf(baseFeed.getNumComment()));
        if (baseFeed.isLike()) {
            mBinding.layoutFooter.imageHeart.setImageResource(R.drawable.ic_red_heart);
        } else {
            mBinding.layoutFooter.imageHeart.setImageResource(R.drawable.ic_gray_heart);
        }
    }

    private void setTitle(BaseFeed baseFeed) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(baseFeed.getUser().getUserName());
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                if (mCallback != null) {
                    mCallback.onClickUser(baseFeed.getUser(), getAdapterPosition() - 1);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, spannableStringBuilder.length() - baseFeed.getUser().getUserName().length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(mBinding.getRoot().getResources().getColor(R.color.color_black)), spannableStringBuilder.length() - baseFeed.getUser().getUserName().length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String type;
        if (baseFeed.getReviewType() == ReviewType.TYPE_PLAN) {
            type = mBinding.getRoot().getResources().getString(R.string.feed_type_planning);
        } else {
            type = mBinding.getRoot().getResources().getString(R.string.feed_type_reviewing);
        }
        spannableStringBuilder.append(type);
        spannableStringBuilder.setSpan(new TypefaceSpan(Typeface.create(mBinding.getRoot().getResources().getResourceName(R.font.san_francisco_display_regular), Typeface.NORMAL)), spannableStringBuilder.length() - type.length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(mBinding.getRoot().getResources().getColor(R.color.color_darker_gray)), spannableStringBuilder.length() - type.length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableStringBuilder.append(baseFeed.getMovieObject().getTitle());
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                if (mCallback != null) {
                    mCallback.onClickMovie(baseFeed, getAdapterPosition() - 1);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, spannableStringBuilder.length() - baseFeed.getMovieObject().getTitle().length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(mBinding.getRoot().getResources().getColor(R.color.color_black)), spannableStringBuilder.length() - baseFeed.getMovieObject().getTitle().length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.layoutHeader.textUserName.setText(spannableStringBuilder);
    }


    private void initListeners() {
        mBinding.layoutHeader.getRoot().setOnClickListener(this);
        mBinding.layoutFooter.layoutComment.setOnClickListener(this);
        mBinding.layoutFooter.layoutHeart.setOnClickListener(this);
        mBinding.layoutCard.getRoot().setOnClickListener(this);
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
            case R.id.layout_card:
                if (mCallback != null) {
                    mCallback.onClickMovie(mBaseFeed, getAdapterPosition() - 1);
                }
                break;
            default:
                break;
        }
    }
}
