package com.example.polivmi1.vyngproject.utils;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.polivmi1.vyngproject.R;

import javax.inject.Inject;


public class ImageLoader {
    private RequestManager glide;

    @Inject
    public ImageLoader(RequestManager glide) {
        this.glide = glide;
    }

    public void load(String videoIcon, ImageView icon) {
        if (icon != null) {
            glide.load(videoIcon)
                    .asGif()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(icon);
        }
    }
}
