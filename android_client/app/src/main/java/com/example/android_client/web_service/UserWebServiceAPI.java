package com.example.android_client.web_service;

import com.example.android_client.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserWebServiceAPI {
    @GET("index/users")
    Call<List<User>> getAll();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("users/details/{id}")
    Call<User> getUserDetails(@Path("id") String username, @Header("Authorization") String token);

    @POST("users")
    Call<Void> createUser(@Body User user);

    @PATCH("users/{id}")
    Call<Void> updateUser(@Path("id") String id);

    @POST("tokens")
    Call<String> login(@Body User userDetails);

    @POST("users")
    Call<User> addUser(@Body User user);

    @DELETE("users/{userId}")
    Call<Void> deleteUser(@Path("userId") String userId, @Header("Authorization")String token);
}
