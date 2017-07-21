package com.example.polivmi1.vyngproject.app.dagger;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.polivmi1.vyngproject.services.GiphyService;
import com.example.polivmi1.vyngproject.repository.IRepository;
import com.example.polivmi1.vyngproject.utils.ImageLoader;
import com.example.polivmi1.vyngproject.models.Video;
import com.example.polivmi1.vyngproject.repository.Repository;
import com.example.polivmi1.vyngproject.utils.VideoPlayer;
import com.example.polivmi1.vyngproject.models.VideosList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.addInterceptor(logging).build();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(RequestManager glide) {
        return new ImageLoader(glide);
    }

    @Provides
    @Singleton
    RequestManager provideGlide() {
        return Glide.with(context);
    }

    @Provides
    @Singleton
    GiphyService provideGiphyService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(VideosList.class, new VideosDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/gifs/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getOkHttpClient())
                .build();

        return retrofit.create(GiphyService.class);
    }

    @Provides
    @Singleton
    IRepository provideRepository(GiphyService giphyService) {
        return new Repository(giphyService);
    }


    @Provides
    @Singleton
    VideoPlayer provideVideoPlayer(Context context) {
        return new VideoPlayer(context);
    }

    public class VideosDeserializer implements JsonDeserializer<VideosList> {
        @Override
        public VideosList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            VideosList videos = new VideosList();
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement data = jsonObject.get("data");
            JsonArray arrayData = data.getAsJsonArray();
            for (JsonElement j : arrayData) {
                String id = j.getAsJsonObject().get("id").getAsString();
                String image = j.getAsJsonObject().get("images").getAsJsonObject().get("fixed_height_small_still").getAsJsonObject().get("url").getAsString();
                String video = j.getAsJsonObject().get("images").getAsJsonObject().get("original_mp4").getAsJsonObject().get("mp4").getAsString();
                videos.add(new Video(id, image, video));
            }

            return videos;
        }
    }
}