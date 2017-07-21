package com.example.polivmi1.vyngproject.screens.detail;

import com.example.polivmi1.vyngproject.utils.VideoPlayer;

import javax.inject.Inject;


public class VideoDetailControllerFactory {

    private VideoDetailPresenter presenter;
    private VideoPlayer videoPlayer;

    @Inject
    public VideoDetailControllerFactory(VideoDetailPresenter presenter, VideoPlayer videoPlayer) {
        this.presenter = presenter;
        this.videoPlayer = videoPlayer;
    }

    public VideoDetailController getController() {
        return new VideoDetailController(presenter, videoPlayer);
    }
}
