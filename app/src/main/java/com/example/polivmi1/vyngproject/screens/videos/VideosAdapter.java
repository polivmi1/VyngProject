package com.example.polivmi1.vyngproject.screens.videos;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.polivmi1.vyngproject.R;
import com.example.polivmi1.vyngproject.utils.ImageLoader;
import com.example.polivmi1.vyngproject.models.Video;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class VideosAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private final List<Video> videos = new ArrayList<>();
    private ImageLoader imageLoader;
    private VideosAdapterOnClick videosAdapterOnClick;

    @Inject
    public VideosAdapter(ImageLoader imageLoader) {
        super();
        this.imageLoader = imageLoader;
    }

    void setVideosAdapterOnClick(VideosAdapterOnClick videosAdapterOnClick) {
        this.videosAdapterOnClick = videosAdapterOnClick;
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        viewHolder.getAdapterPosition();
        view.setOnClickListener(view1 ->
                videosAdapterOnClick.onClicked(viewHolder.getAdapterPosition())
        );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, final int position) {
        holder.bind(videos.get(position), imageLoader);
    }

    void addItem(final Video video) {
        videos.add(video);
    }

    Video getItem(final int position) {
        return videos.get(position);
    }

    void clear() {
        videos.clear();
    }

    public interface VideosAdapterOnClick {
        void onClicked(int index);
    }
}