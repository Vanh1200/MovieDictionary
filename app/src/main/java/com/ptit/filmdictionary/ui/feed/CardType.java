package com.ptit.filmdictionary.ui.feed;


import androidx.annotation.IntDef;

@IntDef({CardType.CARD_CREATE_POST,
        CardType.CARD_TEXT_IMAGE,
        CardType.CARD_MOVIE,
        CardType.CARD_LIST_IMAGE,
        CardType.CARD_VIDEO})
public @interface CardType {
    int CARD_LOAD_MORE = -2;
    int CARD_CREATE_POST = -1;
    int CARD_TEXT_IMAGE = 1;
    int CARD_MOVIE = 2;
    int CARD_LIST_IMAGE = 3;
    int CARD_VIDEO = 4;
}
