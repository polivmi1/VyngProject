package com.example.polivmi1.vyngproject.app;

import android.app.Activity;
import android.app.Application;

import com.example.polivmi1.vyngproject.app.dagger.AppComponent;
import com.example.polivmi1.vyngproject.app.dagger.AppModule;
import com.example.polivmi1.vyngproject.app.dagger.DaggerAppComponent;
import com.example.polivmi1.vyngproject.models.MyObjectBox;

import io.objectbox.BoxStore;

public class App extends Application {
    private BoxStore boxStore;
    private AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public AppComponent component() {
        return appComponent;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}