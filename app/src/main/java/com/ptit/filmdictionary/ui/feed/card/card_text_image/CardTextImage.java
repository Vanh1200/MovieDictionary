package com.ptit.filmdictionary.ui.feed.card.card_text_image;

import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.base.BaseFeed;

public class CardTextImage extends BaseFeed {
    @SerializedName("text")
    private String text;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
