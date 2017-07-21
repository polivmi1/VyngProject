package com.example.polivmi1.vyngproject.repository;

import com.example.polivmi1.vyngproject.models.VideosList;
import com.example.polivmi1.vyngproject.services.GiphyService;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.example.polivmi1.vyngproject.utils.constants.DEFAULT_LIMIT;
import static com.example.polivmi1.vyngproject.utils.constants.KEY_GIPHY;

public class Repository implements IRepository{

    private GiphyService giphyService;

    @Inject
    public Repository(GiphyService giphyService){
        this.giphyService = giphyService;
    }

    @Override
    public Observable<VideosList> getAllVideos(String query) {
        return giphyService.getAllVideos(KEY_GIPHY, query, DEFAULT_LIMIT);
    }
}
