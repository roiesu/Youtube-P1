package com.example.android_client.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;
    public MutableLiveData<User> getUser(){
        if(user==null){
            return new MutableLiveData<>();
        }
        return user;
    }
}
