package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.UserApi;
import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

public class UserRepository {
    private UserDao dao;
    private UserData userData;
    private UserApi api;
    private LifecycleOwner owner;

    public UserRepository(String username) {
        api = new UserApi();
        userData = new UserData(username);
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
    }

    public UserRepository(LifecycleOwner owner) {
        api = new UserApi();
        userData = new UserData();
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
        this.owner = owner;
    }

    class UserData extends MutableLiveData<User> {
        public UserData(String username) {
            super();
            getUser(username);
        }

        public UserData() {
            super();
        }

        //        @Override
        protected void onActive() {
            super.onActive();
        }
    }

    public void getUser(String username) {
        new Thread(() -> {
            userData.postValue(dao.get(username));
        }).start();
    }

    public void getFullUserDetails(String username) {
        api.getUserFullDetails(this.userData, username);
    }

    public MutableLiveData<User> get() {
        return userData;
    }

    public void login() {
        this.api.login(userData);
    }

    public void addUser() {
        this.api.add(userData);
    }

    public void deleteUser(MutableLiveData finished) {
        User user = this.userData.getValue();
        this.userData.observe(owner, data -> {
            if (data == null) {
                new Thread(() -> {
                    dao.delete(user);
                    finished.postValue(true);
                }).start();
            }
        });
        this.api.deleteUser(this.userData);
    }

}
