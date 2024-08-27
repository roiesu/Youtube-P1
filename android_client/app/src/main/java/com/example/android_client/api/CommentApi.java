package com.example.android_client.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.datatypes.CommentWithUser;
import com.example.android_client.entities.Comment;
import com.example.android_client.DataManager;
import com.example.android_client.entities.Video;
import com.example.android_client.repositories.CommentRepository;
import com.example.android_client.web_service.CommentWebServiceAPI;
import com.example.android_client.web_service.VideoWebServiceAPI;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentApi {
    Retrofit retrofit;
    CommentWebServiceAPI webServiceAPI;

    public CommentApi() {
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(CommentWebServiceAPI.class);
    }

    public void getAll(MutableLiveData comments) {
        Call<List<Comment>> call = webServiceAPI.getAll();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> body = response.body();
                if (body != null) {
                    comments.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void deleteComment(MutableLiveData<Comment> data, Comment comment, String videoUploader) {
        String header = "Bearer " + DataManager.getToken();
        Call<Void> call = webServiceAPI.deleteComment(videoUploader, comment.getVideoId(), comment.get_id(), header);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    comment.setUserId(null);
                    data.setValue(comment);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void editComment(MutableLiveData<Comment> data, Comment comment, String text, String videoUploader) {
        String header = "Bearer " + DataManager.getToken();
        Comment temp = new Comment();
        temp.setText(text);
        Call<Void> call = webServiceAPI.editComment(videoUploader, comment.getVideoId(), comment.get_id(), header, temp);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    comment.setText(text);
                    comment.setEdited(true);
                    data.setValue(comment);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }
    public void addComment(MutableLiveData<Comment> data,String text,String uploaderId,String videoId){
        String header = "Bearer " + DataManager.getToken();
        Comment temp = new Comment();
        temp.setText(text);
        Call<CommentWithUser> call = webServiceAPI.addComment(uploaderId,videoId,header,temp);
        call.enqueue(new Callback<CommentWithUser>() {
            @Override
            public void onResponse(Call<CommentWithUser> call, Response<CommentWithUser> response) {
                Comment body= response.body();
                if(body!=null){
                    data.setValue(body);
                }
                else{
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<CommentWithUser> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

}
