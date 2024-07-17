package com.example.android_client.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.R;
import com.example.android_client.ContextApplication;
import com.example.android_client.activities.MainPage;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.web_service.UserWebServiceAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserAPI {
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public UserAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create(gson)).build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }

    public void getAll(MutableLiveData users) {
        Call<List<User>> call = webServiceAPI.getAll();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> body = response.body();
                if (body != null) {
                    users.setValue(body);
                } else {
                    Log.w("INDEX ERROR", "error");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w("ERRORRRRR",t);
            }
        });
    }

    public void add(MutableLiveData<User> userDetails) {
        Call<Void> addUserCall = webServiceAPI.addUser(userDetails.getValue());
        addUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    login(userDetails);
                } else {
                    String errorMessage;
                    try {
                        errorMessage = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        errorMessage = response.message();
                    }
                    ContextApplication.showToast(errorMessage);
                    Log.w("Login" + response.raw().code(), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w("USER RESPONSE", t);
            }

        });

    }

    public void get(String username, MutableLiveData userData) {
        if (username == null || username == "") {
            return;
        }
        Call<User> call = webServiceAPI.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    userData.setValue(body);
                } else {
                    Log.w("USER RESPONSE", response.raw().code() + "");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("USER RESPONSE", t);
            }
        });
    }

    public void login(MutableLiveData<User> userDetails) {
        User user = userDetails.getValue();
        Call<String> call = webServiceAPI.login(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                if (body != null) {
                    DataManager instance = DataManager.getInstance();
                    instance.setCurrentUsername(user.getUsername());
                    instance.setToken(body);
                    userDetails.setValue(null);
                } else {
                    String errorMessage;
                    try {
                        errorMessage = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        errorMessage = response.message();
                    }
                    ContextApplication.showToast(errorMessage);
                    Log.w("Login" + response.raw().code(), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.w("Can't login", t);
            }
        });
    }

}
