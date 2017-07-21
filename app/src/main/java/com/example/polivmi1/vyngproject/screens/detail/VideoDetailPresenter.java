package com.example.polivmi1.vyngproject.screens.detail;

import android.content.Context;

import com.example.polivmi1.vyngproject.app.App;
import com.example.polivmi1.vyngproject.models.Video;
import com.example.polivmi1.vyngproject.models.Video_;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class VideoDetailPresenter implements VideoDetailContract.Presenter<VideoDetailContract.View> {

    private final Box<Video> videoBox;
    protected VideoDetailContract.View view = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Video video;

    @Inject
    public VideoDetailPresenter(Context context) {
        videoBox = ((App) context).getBoxStore().boxFor(Video.class);
    }

    @Override
    public void attachView(VideoDetailContract.View view) {
        this.view = view;
        init();
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }


    public Disposable getThumbsUpSubscription() {
        return view.thumbUpClicked()
                .doOnNext(__ -> view.setLoading(true))
                .observeOn(Schedulers.io())
                .doOnNext(__ -> increaseLikes())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> view.setLoading(false))
                .retry()
                .subscribe(__ -> view.refreshCounter(video.getLikes()));
    }


    public Disposable getThumbsDownSubscription() {
        return view.thumbDownClicked()
                .doOnNext(__ -> view.setLoading(true))
                .observeOn(Schedulers.io())
                .doOnNext(__ -> decreaseLikes())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> view.setLoading(false))
                .retry()
                .subscribe(__ -> view.refreshCounter(video.getLikes()));
    }


    public Disposable getSaveSubscription() {
        return view.saveClicked()
                .doOnNext(__ -> view.setLoading(true))
                .doOnNext(__ -> saveVideo(view.getVideo()))
                .doOnEach(__ -> view.setLoading(false))
                .doOnNext(__ -> view.setSave(false))
                .retry()
                .subscribe(videosList -> view.showSavedToast());
    }


    public void saveVideo(Video v) {
        video = v;
        video.assignID();
        video.setLikes(0);
        videoBox.put(video);
        view.refreshCounter(video.getLikes());
    }

    private void increaseLikes() {
        video.increaseLikes();
        videoBox.put(video);
    }

    private void decreaseLikes() {
        video.decreaseLikes();
        videoBox.put(video);
    }

    @Override
    public void init() {
        List<Video> v = videoBox.query().equal(Video_.key, view.getVideo().getKey()).build().find();
        if (v.size() != 0) {
            video = v.get(0);
            view.refreshCounter(video.getLikes());
            view.setSave(false);
        } else {
            view.setSave(true);
        }
        compositeDisposable.add(getThumbsDownSubscription());
        compositeDisposable.add(getThumbsUpSubscription());
        compositeDisposable.add(getSaveSubscription());
    }
}
