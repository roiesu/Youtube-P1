package com.example.android_client.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
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
}
