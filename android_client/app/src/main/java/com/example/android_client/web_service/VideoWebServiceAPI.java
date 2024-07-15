package com.example.android_client.web_service;

import com.example.android_client.entities.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoWebServiceAPI {
    @GET ("videos")
    Call<List<Video>> getVideos();
}
