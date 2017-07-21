package com.example.polivmi1.vyngproject.screens.videos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.polivmi1.vyngproject.R;
import com.example.polivmi1.vyngproject.utils.ImageLoader;
import com.example.polivmi1.vyngproject.models.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon)
    ImageView icon;

    VideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Video video, ImageLoader imageLoader) {
        imageLoader.load(video.getIcon(), icon);
    }

}
