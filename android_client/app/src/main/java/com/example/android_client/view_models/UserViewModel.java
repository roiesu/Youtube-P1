package com.example.android_client.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;
import com.example.android_client.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private UserRepository repository;

    public UserViewModel(String username) {
        this.repository = new UserRepository(username);
        user = repository.get();
    }

    public UserViewModel() {
        this.repository = new UserRepository();
        user = repository.get();
    }

    public MutableLiveData<User> getUserData() {
        if (user == null) {
            return new MutableLiveData<>();
        }
        return user;
    }

    public void getUser(String username) {
        repository.getUser(username);
    }

    public void getFullUserDetails(String username) {
        repository.getFullUserDetails(username);
    }

    public void login() {
        this.repository.login();
    }

    public void create() {
        this.repository.addUser();
    }

}
