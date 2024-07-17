package com.example.android_client.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.datatypes.VideoWithLikes;
import com.example.android_client.entities.Video;
import com.example.android_client.web_service.VideoWebServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class VideoApi {
    Retrofit retrofit;
    VideoWebServiceAPI webServiceAPI;

    public VideoApi() {
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(VideoWebServiceAPI.class);
    }

// channel page
    public void getVideosByUser(String userId, MutableLiveData<List<Video>> videoListData) {
        Call<List<Video>> call = webServiceAPI.getVideosByUser(userId);
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
                Log.w("VIDEO API", t);
            }
        });
    }
    public void getVideos(MutableLiveData videoListData,String query) {
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

    public void get(MutableLiveData videoData,String channel, String videoId) {
        if(videoId == null ||channel == null){
            return;
        }

        Call<Video> call = webServiceAPI.getVideo(channel, videoId);
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video body = response.body();
                if (body != null) {
                    videoData.setValue(body);
                }
            }
            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.w("USER RESPONSE", t);
            }
        });
    }
    public void getAll(MutableLiveData videoListData){
        Call<List<VideoWithLikes>> call = webServiceAPI.getAll();
        call.enqueue(new Callback<List<VideoWithLikes>>() {
            @Override
            public void onResponse(Call<List<VideoWithLikes>> call, Response<List<VideoWithLikes>> response) {
                List<VideoWithLikes> body = response.body();
                if(body!=null){
                    videoListData.setValue(body);
                }
                else{
                    Log.w("Zbabir","yes");
                }
            }

            @Override
            public void onFailure(Call<List<VideoWithLikes>> call, Throwable t) {
                Log.w("Zbabir",t);
            }
        });
    }
}
