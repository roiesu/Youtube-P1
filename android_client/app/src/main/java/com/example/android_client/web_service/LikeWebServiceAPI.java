package com.example.android_client.web_service;

import com.example.android_client.entities.Like;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LikeWebServiceAPI {
    @GET("tokens/verify")
    Call<String> getUserId(@Header("Authorization") String token);

    @PUT("users/{username}/videos/{videoId}/like")
    Call<String> likeVideo(@Path("username") String username, @Path("videoId") String videoId, @Header("Authorization") String token);
}
