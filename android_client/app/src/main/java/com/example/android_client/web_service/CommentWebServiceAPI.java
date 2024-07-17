package com.example.android_client.web_service;

import com.example.android_client.entities.Comment;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CommentWebServiceAPI {
    @GET("index/comments")
    Call<List<Comment>> getAll();

}
