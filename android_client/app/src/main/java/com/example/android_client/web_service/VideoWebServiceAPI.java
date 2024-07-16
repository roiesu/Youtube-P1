package com.example.android_client.web_service;

import com.example.android_client.entities.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VideoWebServiceAPI {
    @GET ("videos")
    Call<List<Video>> getVideos(@Query("name") String query);

    @GET("users/{channel}/videos/{videoId}")
    Call<Video> getVideo(@Path("channel") String channel, @Path("videoId") String videoId);
}
