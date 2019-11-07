package com.ptit.filmdictionary.ui.feed.card.card_create_post;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.databinding.CardCreatePostBinding;

public class CardCreatePostVH extends RecyclerView.ViewHolder {
    private CardCreatePostBinding mBinding;

    public CardCreatePostVH(@NonNull CardCreatePostBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

}
