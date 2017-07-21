package com.example.polivmi1.vyngproject.app.dagger;

import android.content.Context;

import com.example.polivmi1.vyngproject.services.GiphyService;
import com.example.polivmi1.vyngproject.repository.IRepository;
import com.example.polivmi1.vyngproject.utils.ImageLoader;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context context();

    ImageLoader imageLoader();

    GiphyService giphyService();

    IRepository repository();
}
