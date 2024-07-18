package com.example.android_client.view_models;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;
import com.example.android_client.repositories.UserListRepository;

import java.util.List;

public class UserListViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private UserListRepository repository;
    public UserListViewModel(){
        this.repository = new UserListRepository();
        users = repository.get();
    }
    public MutableLiveData<List<User>> getUsers(){
        if(users==null){
            return new MutableLiveData<>();
        }
        return users;
    }
    public void init(LifecycleOwner lifecycleOwner){
        repository.init(lifecycleOwner);
    }
}
