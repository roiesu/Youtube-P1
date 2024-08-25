package com.example.android_client.web_service;

import androidx.room.Transaction;

import com.example.android_client.api.VideoApi;
import com.example.android_client.datatypes.VideoWithLikes;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.datatypes.VideoWithUserWithComments;
import com.example.android_client.entities.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
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

    @PATCH("users/{channel}/videos/{videoId}/view")
    Call<Video> incViews(@Path("channel") String channel, @Path("videoId") String videoId);

    @POST("users/{channel}/videos")
    Call<Video> uploadVideo(@Path("channel") String channel, @Header("Authorization") String token, @Body Video video);

    @DELETE("users/{channel}/videos/{videoId}")
    Call<Void> deleteVideo(@Path("channel") String channel, @Path("videoId") String videoId, @Header("Authorization") String token);
}
