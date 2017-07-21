package com.example.polivmi1.vyngproject.models;

import java.util.ArrayList;
import java.util.List;


public class VideosList {
    private List<Video> videos = new ArrayList<>();

    public VideosList() {

    }

    @Override
    public String toString() {
        return "VideosList{" +
                "videos=" + videos +
                '}';
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void addAll(VideosList videos) {
        this.videos.addAll(videos.getVideos());
    }

    public void add(Video video) {
        this.videos.add(video);
    }
}
