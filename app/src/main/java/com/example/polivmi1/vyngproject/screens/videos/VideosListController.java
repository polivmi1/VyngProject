package com.example.polivmi1.vyngproject.screens.videos;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.polivmi1.vyngproject.R;
import com.example.polivmi1.vyngproject.screens.detail.VideoDetailController;
import com.example.polivmi1.vyngproject.screens.detail.VideoDetailControllerFactory;
import com.example.polivmi1.vyngproject.models.Video;
import com.example.polivmi1.vyngproject.models.VideosList;
import com.example.polivmi1.vyngproject.utils.Utils;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


public class VideosListController extends Controller implements VideosListContract.View, VideosAdapter.VideosAdapterOnClick {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.searchInput)
    EditText searchInput;


    private VideosListPresenter presenter;
    private VideosAdapter videosAdapter;
    private VideoDetailControllerFactory videoDetailControllerFactory;


    @Inject
    public VideosListController(VideosListPresenter presenter, VideosAdapter videosAdapter, VideoDetailControllerFactory videoDetailControllerFactory) {
        this.presenter = presenter;
        this.videosAdapter = videosAdapter;
        this.videoDetailControllerFactory = videoDetailControllerFactory;
    }

    public VideosListController(Bundle bundle) {
        super(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.view_videos_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        recyclerView.setAdapter(videosAdapter);
        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter.attachView(this);
        videosAdapter.setVideosAdapterOnClick(this);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        presenter.detachView();
    }

    @Override
    public void addVideos(VideosList videos) {
        videosAdapter.clear();
        for (Video m : videos.getVideos()) {
            videosAdapter.addItem(m);
        }
        videosAdapter.notifyDataSetChanged();
    }


    @Override
    public void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public Observable<Object> searchClicked() {
        return RxView.clicks(searchButton);
    }

    @Override
    public String getSearchInput() {
        return searchInput.getText().toString();
    }

    @Override
    public void hideKeyboard() {
        Utils.hideKeyboard(getActivity());
    }

    @Override
    public void onClicked(int index) {
        Video video = videosAdapter.getItem(index);
        if (video != null) {
            VideoDetailController videoDetailController = videoDetailControllerFactory.getController();
            videoDetailController.setVideo(video);
            getRouter().pushController(RouterTransaction.with(
                    videoDetailController)
            );
        }
    }

}
