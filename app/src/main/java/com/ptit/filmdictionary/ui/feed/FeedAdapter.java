package com.ptit.filmdictionary.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;
import com.ptit.filmdictionary.databinding.CardCreatePostBinding;
import com.ptit.filmdictionary.databinding.CardTextImageBinding;
import com.ptit.filmdictionary.ui.chat.ChatAdapter;
import com.ptit.filmdictionary.ui.feed.card.card_create_post.CardCreatePostVH;
import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImageVH;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<BaseVH> {
    private List<BaseFeed> mCards = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private FeedCallback mCallback;

    public FeedAdapter(Context context) {
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
            notifyItemRangeChanged(before, data.size());
        }
    }

    public void setCallback(FeedCallback callback) {
        mCallback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return CardType.CARD_CREATE_POST;
        return mCards.get(position - 1).getCardType();
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CardType.CARD_CREATE_POST:
                CardCreatePostBinding binding = DataBindingUtil.inflate(mInflater, R.layout.card_create_post, parent, false);
                return new CardCreatePostVH(binding, mCallback);
            case CardType.CARD_TEXT_IMAGE:
                CardTextImageBinding binding1 = DataBindingUtil.inflate(mInflater, R.layout.card_text_image, parent,false);
                return new CardTextImageVH(binding1, mCallback);
        }
        CardCreatePostBinding binding = DataBindingUtil.inflate(mInflater, R.layout.card_create_post, parent, false);
        return new CardCreatePostVH(binding, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
        if (position == 0) {
            holder.bindData(null);
            return;
        }
        holder.bindData(mCards.get(position - 1));
    }

    @Override
    public int getItemCount() {
        return mCards.size() + 1;
    }
}
