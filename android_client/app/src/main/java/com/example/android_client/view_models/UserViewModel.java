package com.example.android_client.view_models;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private UserRepository repository;


    public UserViewModel(LifecycleOwner owner) {
        this.repository = new UserRepository(owner);
        user = repository.get();
    }

    public MutableLiveData<User> getUserData(){
        if(user==null){
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

    public void delete(MutableLiveData finished) {
        this.repository.deleteUser(finished);
    }

    public void edit(User user,MutableLiveData finished){this.repository.editUser(user,finished);}

}
