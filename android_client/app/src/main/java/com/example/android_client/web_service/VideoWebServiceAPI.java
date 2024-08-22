package com.example.android_client.web_service;

import androidx.room.Transaction;

import com.example.android_client.datatypes.VideoWithLikes;
import com.example.android_client.datatypes.VideoWithUserWithComments;
import com.example.android_client.entities.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VideoWebServiceAPI {
    @GET("videos")
    Call<List<Video>> getVideos(@Query("name") String query);

    //channel page
    @GET("users/{userId}/videos")
    Call<List<Video>> getVideosByUser(@Path("userId") String userId);

    @GET("users/{channel}/videos/{videoId}")
    Call<VideoWithUserWithComments> getVideo(@Path("channel") String channel, @Path("videoId") String videoId);

    @GET("index/videos")
    Call<List<VideoWithLikes>> getAll();

    @GET("users/{userId}/videos/details")
    Call<List<Video>> getVideosDetailsByUser(@Path("userId") String userId, @Header("Authorization") String token);

}
