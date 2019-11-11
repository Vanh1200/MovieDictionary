package com.ptit.filmdictionary.ui.gallery;

import androidx.annotation.IntDef;

/**
 * Created by vanh1200 on 09/11/2019
 */

@IntDef({MediaType.TYPE_IMAGE,
        MediaType.TYPE_VIDEO})
public @interface MediaType {
    int TYPE_IMAGE = 1;
    int TYPE_VIDEO = 3;
}
