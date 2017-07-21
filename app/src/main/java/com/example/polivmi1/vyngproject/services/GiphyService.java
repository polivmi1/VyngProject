package com.example.polivmi1.vyngproject.services;

import com.example.polivmi1.vyngproject.models.VideosList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyService {
    @GET("search")
    Observable<VideosList> getAllVideos(@Query("api_key") String apiKey, @Query("q") String query, @Query("limit") int limit);
}
