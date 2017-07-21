package com.example.polivmi1.vyngproject.screens.dagger;

import android.content.Context;

import com.example.polivmi1.vyngproject.screens.detail.VideoDetailControllerFactory;
import com.example.polivmi1.vyngproject.screens.detail.VideoDetailPresenter;
import com.example.polivmi1.vyngproject.screens.videos.VideosAdapter;
import com.example.polivmi1.vyngproject.screens.videos.VideosListController;
import com.example.polivmi1.vyngproject.screens.videos.VideosListPresenter;
import com.example.polivmi1.vyngproject.repository.IRepository;
import com.example.polivmi1.vyngproject.utils.ImageLoader;
import com.example.polivmi1.vyngproject.utils.VideoPlayer;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    @MainScope
    VideosListPresenter provideVideosListPresenter(IRepository repository) {
        return new VideosListPresenter(repository);
    }

    @Provides
    @MainScope
    VideosAdapter provideVideosAdapter(ImageLoader imageLoader) {
        return new VideosAdapter(imageLoader);
    }

    @Provides
    @MainScope
    VideosListController provideVideosListController(VideosListPresenter videosListPresenter, VideosAdapter videosAdapter, VideoDetailControllerFactory videoDetailController) {
        return new VideosListController(videosListPresenter, videosAdapter, videoDetailController);
    }


    @Provides
    @MainScope
    VideoDetailPresenter provideVideoDetailPresenter(Context context) {
        return new VideoDetailPresenter(context);
    }

    @Provides
    @MainScope
    VideoDetailControllerFactory provideVideoDetailControllerFactory(VideoDetailPresenter presenter, VideoPlayer videoPlayer) {
        return new VideoDetailControllerFactory(presenter, videoPlayer);
    }
}