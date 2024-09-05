package com.example.android_client.api;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.DataManager;
import com.example.android_client.Utilities;
import com.example.android_client.entities.Like;
import com.example.android_client.web_service.LikeWebServiceAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeApi {
    Retrofit retrofit;
    LikeWebServiceAPI webServiceAPI;

    public LikeApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create(gson)).build();
        webServiceAPI = retrofit.create(LikeWebServiceAPI.class);
    }

    public void likeVideo(String username, String videoId, MutableLiveData like) {
        String header =DataManager.getTokenHeader();
        Call<String> call = webServiceAPI.likeVideo(username, videoId, header);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                if (body != null) {
                    like.setValue(new Like(body, videoId));
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void dislikeVideo(String username, String videoId, MutableLiveData like) {
        String header =DataManager.getTokenHeader();
        Call<String> call = webServiceAPI.dislikeVideo(username, videoId, header);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                if (body != null) {
                    like.setValue(new Like(body, videoId));
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

}
