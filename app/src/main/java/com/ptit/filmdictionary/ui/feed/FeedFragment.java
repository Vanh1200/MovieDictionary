package com.ptit.filmdictionary.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.databinding.FragmentFeedBinding;
import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImage;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    private FragmentFeedBinding mBinding;
    private FeedAdapter mFeedAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<BaseFeed> mData = new ArrayList<>();

    public static FeedFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        initComponents();
        initListeners();
        fakeData();
        return mBinding.getRoot();
    }

    private void fakeData() {
        CardTextImage CardTextImage = new CardTextImage();
        CardTextImage.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage1 = new CardTextImage();
        CardTextImage1.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage2 = new CardTextImage();
        CardTextImage2.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage3 = new CardTextImage();
        CardTextImage3.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage4 = new CardTextImage();
        CardTextImage4.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage5 = new CardTextImage();
        CardTextImage5.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage6 = new CardTextImage();
        CardTextImage6.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage7 = new CardTextImage();
        CardTextImage7.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage8 = new CardTextImage();
        CardTextImage8.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage9 = new CardTextImage();
        CardTextImage9.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage10 = new CardTextImage();
        CardTextImage10.setCardType(CardType.CARD_TEXT_IMAGE);

        mData.add(CardTextImage);
        mData.add(CardTextImage1);
        mData.add(CardTextImage2);
        mData.add(CardTextImage3);
        mData.add(CardTextImage4);
        mData.add(CardTextImage5);
        mData.add(CardTextImage6);
        mData.add(CardTextImage7);
        mData.add(CardTextImage8);
        mData.add(CardTextImage9);
        mData.add(CardTextImage10);

        mFeedAdapter.setData(mData);
    }

    private void initListeners() {

    }

    private void initComponents() {
        mFeedAdapter = new FeedAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.recyclerFeed.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerFeed.setAdapter(mFeedAdapter);
    }
}
