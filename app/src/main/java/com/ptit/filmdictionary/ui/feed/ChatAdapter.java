package com.ptit.filmdictionary.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.databinding.CardCreatePostBinding;
import com.ptit.filmdictionary.ui.feed.card.card_create_post.CardCreatePostVH;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseFeed> mCards = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public ChatAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return CardType.CARD_CREATE_POST;
        return mCards.get(position).getCardType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CardType.CARD_CREATE_POST:
                CardCreatePostBinding binding = DataBindingUtil.inflate(mInflater, R.layout.card_create_post, parent, false);
                return new CardCreatePostVH(binding);
            case CardType.CARD_TEXT_IMAGE:
//                Card
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCards.size() + 1;
    }
}
