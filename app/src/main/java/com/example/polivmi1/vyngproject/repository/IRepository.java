package com.example.polivmi1.vyngproject.repository;

import com.example.polivmi1.vyngproject.models.VideosList;

import io.reactivex.Observable;


public interface IRepository {
    Observable<VideosList> getAllVideos(String query);
}
