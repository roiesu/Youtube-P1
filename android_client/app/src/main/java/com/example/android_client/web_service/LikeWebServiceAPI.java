package com.example.android_client.web_service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LikeWebServiceAPI {
    @GET("tokens/verify")
    Call<String> getUserId(@Header("Authorization") String token);
}
