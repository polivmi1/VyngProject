package com.example.polivmi1.vyngproject.screens.dagger;


import com.example.polivmi1.vyngproject.app.dagger.AppComponent;
import com.example.polivmi1.vyngproject.screens.MainActivity;

import dagger.Component;

@MainScope
@Component(modules = {MainModule.class}, dependencies = {AppComponent.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
