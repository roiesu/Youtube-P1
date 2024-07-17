package com.example.android_client.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.UserApi;
import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

public class UserRepository {
    private UserDao dao;
    private UserData userData;
    private UserApi api;
    public UserRepository(String username){
        api = new UserApi();
        userData = new UserData(username);
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
    }
    public UserRepository(){
        api = new UserApi();
        userData = new UserData();
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
    }
    class UserData extends MutableLiveData<User> {
        public UserData(String username){
            super();
            api.get(username, this);
        }
        public UserData(){
            super();
        }
//        @Override
//        protected void onActive(){
//            super.onActive();
//            new Thread(()->{
//               userData.postValue(dao.get(username));
//            }).start();
//        }

    }
    public MutableLiveData<User> get(){
        return userData;
    }
    public void login(){
        this.api.login(userData);
    }

    public void addUser(){
        this.api.add(userData);
    }


}
