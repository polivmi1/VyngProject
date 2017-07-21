package com.example.polivmi1.vyngproject.screens.detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.example.polivmi1.vyngproject.R;
import com.example.polivmi1.vyngproject.models.Video;
import com.example.polivmi1.vyngproject.utils.VideoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


public class VideoDetailController extends Controller implements VideoDetailContract.View {

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.thumbsDown)
    ImageView thumbsDown;
    @BindView(R.id.thumbsUp)
    ImageView thumbsUp;
    @BindView(R.id.counter)
    TextView counter;
    @BindView(R.id.save)
    ImageView save;

    private VideoDetailPresenter presenter;
    private VideoPlayer player;
    private Video video;

    @Inject
    public VideoDetailController(VideoDetailPresenter presenter, VideoPlayer player) {
        this.presenter = presenter;
        this.player = player;
    }

    public VideoDetailController(Bundle bundle) {
        super(bundle);
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public void showSavedToast() {
        Toast.makeText(getApplicationContext(), R.string.video_saved, Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.view_video, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter.attachView(this);
        intiVideoPlayer();
    }

    private void intiVideoPlayer() {
        playerView.setPlayer(player.getPlayer());
        player.play(video.getVideo());
    }

    @Override
    public void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setSave(boolean visible) {
        if (visible) {
            save.setVisibility(View.VISIBLE);
            counter.setVisibility(View.INVISIBLE);
            thumbsDown.setVisibility(View.INVISIBLE);
            thumbsUp.setVisibility(View.INVISIBLE);
        } else {
            save.setVisibility(View.INVISIBLE);
            counter.setVisibility(View.VISIBLE);
            thumbsDown.setVisibility(View.VISIBLE);
            thumbsUp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refreshCounter(int likes) {
        counter.setText(Integer.toString(likes));
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        presenter.detachView();
        player.detach();
    }

    @Override
    public Observable<Object> thumbUpClicked() {
        return RxView.clicks(thumbsUp);
    }

    @Override
    public Observable<Object> thumbDownClicked() {
        return RxView.clicks(thumbsDown);
    }

    @Override
    public Observable<Object> saveClicked() {
        return RxView.clicks(save);
    }
}
