package com.example.android_client.api;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.R;
import com.example.android_client.entities.User;
import com.example.android_client.web_service.UserWebServiceAPI;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserAPI {
    Context context;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;
    public UserAPI(){
        retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }
    public void get(){
        Call<User> call = webServiceAPI.getUser("admin1");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("Error!!!", t);
            }
        });
    }

}
