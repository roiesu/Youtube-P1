package com.example.android_client.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.entities.Comment;
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
    public void getAll(MutableLiveData comments){
        Call<List<Comment>> call = webServiceAPI.getAll();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> body = response.body();
                if(body!=null){
                    comments.setValue(body);
                }
                else{
                    Log.w("Errorrrr","Yes");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.w("ERRPRRRR",t);
            }
        });
    }
}
