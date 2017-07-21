package com.example.polivmi1.vyngproject.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.polivmi1.vyngproject.R;
import com.example.polivmi1.vyngproject.app.App;
import com.example.polivmi1.vyngproject.screens.dagger.DaggerMainComponent;
import com.example.polivmi1.vyngproject.screens.videos.VideosListController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ChangeHandlerFrameLayout container;
    @Inject
    VideosListController videosListController;
    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .appComponent(App.get(this).component())
                .build().inject(this);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(videosListController));
        }
    }

    @Override
    public void onBackPressed() {
        if (router != null && !router.handleBack()) {
            super.onBackPressed();
        }
    }
}
