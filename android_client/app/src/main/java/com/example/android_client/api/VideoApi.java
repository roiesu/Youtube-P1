package com.example.android_client.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.entities.Video;
import com.example.android_client.web_service.VideoWebServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoApi {
    Retrofit retrofit;
    VideoWebServiceAPI webServiceAPI;

    public VideoApi() {
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(VideoWebServiceAPI.class);
    }

    public void getAll(MutableLiveData videoListData,String query) {
        Call<List<Video>> call = webServiceAPI.getVideos(query);
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                List<Video> body = response.body();
                if (body != null) {
                    videoListData.setValue(body);
                }
            }
            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Log.w("USER RESPONSE", t);
            }
        });
    }
}
