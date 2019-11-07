package com.ptit.filmdictionary.ui.feed.card.card_review;

import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.model.Movie;

public class CardReview extends BaseFeed {
    @SerializedName("text")
    private String text;

    @SerializedName("trailerId")
    private String trailerId;

    // request after getCardReview from vanh server
    private Movie movie;
}
