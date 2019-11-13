package com.ptit.filmdictionary.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.filmdictionary.R;

/**
 * Created by vanh1200 on 22/10/2019
 */
public class ImageHelper {

    public static void loadImageCircle(ImageView view, String url) {
        RequestOptions options = new RequestOptions().circleCrop();

        Glide.with(view.getContext())
                .load(url)
                .apply(options)
                .into(view);
    }

    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .apply(RequestOptions.errorOf(R.drawable.bg_place_holder))
                .apply(RequestOptions.placeholderOf(R.drawable.bg_place_holder))
                .into(view);
    }
}
