package com.example.android_client.api;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.R;
import com.example.android_client.ContextApplication;
import com.example.android_client.Utilities;
import com.example.android_client.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.web_service.UserWebServiceAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserApi {
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public UserApi() {
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
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void add(MutableLiveData<User> userDetails) {
        Call<User> addUserCall = webServiceAPI.addUser(userDetails.getValue());
        addUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    userDetails.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
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
                    Utilities.handleError(response);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
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
                    userDetails.postValue(null);
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

    public void getUserFullDetails(MutableLiveData data, String username) {
        String header =DataManager.getTokenHeader();
        Call<User> call = webServiceAPI.getUserDetails(username, header);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    data.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void deleteUser(MutableLiveData<User> data) {
        String header =DataManager.getTokenHeader();
        Call<Void> call = webServiceAPI.deleteUser(data.getValue().getUsername(), header);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    data.setValue(null);
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

    public void editUser(MutableLiveData<User> userData, User userDetails) {
        String header =DataManager.getTokenHeader();
        Call<User> call = webServiceAPI.editUser(userDetails.getUsername(), header, userDetails);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    userData.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }
}
