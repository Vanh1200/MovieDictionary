package com.ptit.filmdictionary.ui.feed.card.card_list_image;

import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.base.BaseFeed;

import java.util.List;

public class CardListImage extends BaseFeed {
    @SerializedName("images")
    private List<String> images;

    @SerializedName("text")
    private String text;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
