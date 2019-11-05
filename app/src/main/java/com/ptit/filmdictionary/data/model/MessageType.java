package com.ptit.filmdictionary.data.model;

import androidx.annotation.IntDef;

@IntDef({MessageType.TEXT_MESSAGE,
        MessageType.IMAGE_MESSAGE})

public @interface MessageType {
    int TEXT_MESSAGE = 0;
    int IMAGE_MESSAGE = 1;
}
