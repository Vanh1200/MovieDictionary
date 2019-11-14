package com.ptit.filmdictionary.ui.feed.card.card_load_more;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.base.BaseVH;

public class CardLoadMoreVH extends BaseVH {

    public CardLoadMoreVH(@NonNull ViewDataBinding viewDataBinding) {
        super(viewDataBinding);
    }

    @Override
    public void bindData(BaseFeed baseFeed) {
        Log.d("nothing", "bindData: ");
    }
}
