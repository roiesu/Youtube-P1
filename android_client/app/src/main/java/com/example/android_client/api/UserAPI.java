package com.example.android_client.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.R;
import com.example.android_client.ContextApplication;
import com.example.android_client.entities.User;
import com.example.android_client.web_service.UserWebServiceAPI;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserAPI {
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;
    public UserAPI(){
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }
    public void getAll(){

    }
    public void get(String username, MutableLiveData userData){
        if(username==null || username == ""){
            return;
        }
        Call<User> call = webServiceAPI.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if(body!=null) {
                    userData.setValue(body);
                }
                Log.w("USER RESPONSE", String.valueOf(body));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("USER RESPONSE", t);
            }
        });
    }

}
