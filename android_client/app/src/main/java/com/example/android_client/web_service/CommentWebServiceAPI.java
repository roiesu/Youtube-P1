package com.example.android_client.web_service;

import com.example.android_client.entities.Comment;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface CommentWebServiceAPI {
    @GET("index/comments")
    Call<List<Comment>> getAll();

    @DELETE("users/{channel}/videos/{videoId}/comments/{commentId}")
    Call<Void> deleteComment(@Path("channel") String channel, @Path("videoId") String videoId, @Path("commentId") String commentId, @Header("Authorization") String token);

    @PATCH("users/{channel}/videos/{videoId}/comments/{commentId}")
    Call<Void> editComment(@Path("channel") String channel, @Path("videoId") String videoId, @Path("commentId") String commentId, @Header("Authorization") String token, @Body Comment comment);
}