package com.example.android_client.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;
import com.example.android_client.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private UserRepository repository;
    public UserViewModel(String username){
        this.repository = new UserRepository(username);
        user = repository.get();
    }
    public MutableLiveData<User> getUser(){
        if(user==null){
            return new MutableLiveData<>();
        }
        return user;
    }

}
