package com.example.polivmi1.vyngproject.screens.videos;


import com.example.polivmi1.vyngproject.BaseContract;
import com.example.polivmi1.vyngproject.models.VideosList;

import io.reactivex.Observable;

public interface VideosListContract {
    interface View extends BaseContract.View {
        void addVideos(VideosList videos);

        void setLoading(boolean loading);

        Observable<Object> searchClicked();

        String getSearchInput();

        void hideKeyboard();
    }

    interface Presenter<V> extends BaseContract.Presenter<V> {
        void init();
    }
}