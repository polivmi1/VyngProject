package com.example.polivmi1.vyngproject.screens.detail;


import com.example.polivmi1.vyngproject.BaseContract;
import com.example.polivmi1.vyngproject.models.Video;

import io.reactivex.Observable;

public interface VideoDetailContract {
    interface View extends BaseContract.View {
        Observable<Object> thumbUpClicked();

        Observable<Object> thumbDownClicked();

        Observable<Object> saveClicked();

        void setLoading(boolean loading);

        void setSave(boolean visible);

        void refreshCounter(int counter);

        Video getVideo();

        void showSavedToast();
    }

    interface Presenter<V> extends BaseContract.Presenter<V> {
        void init();
    }
}