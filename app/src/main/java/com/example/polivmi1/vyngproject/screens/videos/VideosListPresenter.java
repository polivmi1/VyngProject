package com.example.polivmi1.vyngproject.screens.videos;

import com.example.polivmi1.vyngproject.repository.IRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class VideosListPresenter implements VideosListContract.Presenter<VideosListContract.View> {

    protected VideosListContract.View view = null;
    private IRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public VideosListPresenter(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(VideosListContract.View view) {
        this.view = view;
        init();
    }

    public Disposable getDataSubscription() {
        return view.searchClicked()
                .doOnNext(__ ->
                {
                    view.hideKeyboard();
                    view.setLoading(true);
                })
                .observeOn(Schedulers.io())
                .switchMap(__ -> repository.getAllVideos(view.getSearchInput()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> view.setLoading(false))
                .retry()
                .subscribe(videosList -> view.addVideos(videosList));
    }

    @Override
    public void init() {
        compositeDisposable.add(getDataSubscription());
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }
}
