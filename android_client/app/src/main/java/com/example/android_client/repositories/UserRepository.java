package com.example.android_client.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.UserApi;
import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

public class UserRepository {
    private UserDao dao;
    private UserData userData;
    private UserApi api;

    public UserRepository(String username) {
        api = new UserApi();
        userData = new UserData(username);
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
    }

    public UserRepository() {
        api = new UserApi();
        userData = new UserData();
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
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
            User user = dao.get(username);
            userData.postValue(user);
        }).start();
    }
    public LiveData<User> getUserById(String userId) {
        return dao.getUserById(userId);
    }

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    public void login() {
        this.api.login(userData);
    }

    public void addUser() {
        this.api.add(userData);
    }

}


