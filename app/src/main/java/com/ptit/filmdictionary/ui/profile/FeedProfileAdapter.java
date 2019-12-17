package com.ptit.filmdictionary.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.CardCreatePostBinding;
import com.ptit.filmdictionary.databinding.CardLoadMoreBinding;
import com.ptit.filmdictionary.databinding.CardReviewBinding;
import com.ptit.filmdictionary.databinding.CardTextImageBinding;
import com.ptit.filmdictionary.ui.feed.CardType;
import com.ptit.filmdictionary.ui.feed.FeedCallback;
import com.ptit.filmdictionary.ui.feed.card.card_create_post.CardCreatePostVH;
import com.ptit.filmdictionary.ui.feed.card.card_load_more.CardLoadMore;
import com.ptit.filmdictionary.ui.feed.card.card_load_more.CardLoadMoreVH;
import com.ptit.filmdictionary.ui.feed.card.card_review.CardReviewVH;
import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImageVH;

import java.util.ArrayList;
import java.util.List;

public class FeedProfileAdapter extends RecyclerView.Adapter<BaseVH> {
    private List<BaseFeed> mCards = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private FeedCallback mCallback;

    public FeedProfileAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<BaseFeed> data) {
        if (data != null) {
            mCards.clear();
            mCards.addAll(data);
            notifyDataSetChanged();
        }

    }

    public void addData(List<BaseFeed> data) {
        if (data != null) {
            int before = mCards.size();
            mCards.addAll(data);
            notifyItemRangeInserted(before, data.size());
        }
    }

    public void addLoadMore() {
        CardLoadMore cardLoadMore = new CardLoadMore();
        mCards.add(cardLoadMore);
        notifyItemInserted(mCards.size() - 1);
    }

    public void removeLoadMore() {
        if (mCards.size() > 0) {
            int size = mCards.size();
            mCards.remove(size - 1);
            notifyItemRemoved(size - 1);
        }
    }

    public void setCallback(FeedCallback callback) {
        mCallback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        return mCards.get(position).getCardType();
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CardType.CARD_LOAD_MORE:
                CardLoadMoreBinding binding2 = DataBindingUtil.inflate(mInflater, R.layout.card_load_more, parent, false);
                return new CardLoadMoreVH(binding2);
            case CardType.CARD_TEXT_IMAGE:
                CardTextImageBinding binding1 = DataBindingUtil.inflate(mInflater, R.layout.card_text_image, parent,false);
                return new CardTextImageVH(binding1, mCallback);
            case CardType.CARD_REVIEW:
                CardReviewBinding binding3 = DataBindingUtil.inflate(mInflater, R.layout.card_review, parent, false);
                return new CardReviewVH(binding3, mCallback);
        }
        CardCreatePostBinding binding = DataBindingUtil.inflate(mInflater, R.layout.card_create_post, parent, false);
        return new CardCreatePostVH(binding, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
        holder.bindData(mCards.get(position));
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
