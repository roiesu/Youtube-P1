package com.example.android_client.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.ContextApplication;
import com.example.android_client.api.UserAPI;
import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

public class UserRepository {
    private UserDao dao;
    private UserData userData;
    private UserAPI api;
    private String username;
    public UserRepository(String username){
        this.username = username;
        api = new UserAPI();
        userData = new UserData();
        AppDB database = AppDB.getInstance();
        dao = database.userDao();

    }
    class UserData extends MutableLiveData<User> {
        public UserData(){
            super();
            api.get(username, this);
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
    public void reload(){
        api.get(username,userData);
    }

}
