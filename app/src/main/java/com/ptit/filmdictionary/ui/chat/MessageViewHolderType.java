package com.ptit.filmdictionary.ui.chat;

import androidx.annotation.IntDef;

@IntDef({MessageViewHolderType.SENT_TEXT_MESSAGE,
        MessageViewHolderType.RECEIVED_TEXT_MESSAGE,
        MessageViewHolderType.SENT_IMAGE_MESSAGE,
        MessageViewHolderType.RECEIVED_IMAGE_MESSAGE})

public @interface MessageViewHolderType {
    int SENT_TEXT_MESSAGE = 0;
    int RECEIVED_TEXT_MESSAGE = 1;
    int SENT_IMAGE_MESSAGE = 2;
    int RECEIVED_IMAGE_MESSAGE = 3;
}
