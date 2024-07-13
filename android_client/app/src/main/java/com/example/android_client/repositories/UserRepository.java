package com.example.android_client.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.api.UserAPI;
import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

public class UserRepository {
    private UserDao dao;
    private UserData userData;
    private UserAPI api;
    public UserRepository(){
        api = new UserAPI();
        userData = new UserData();
//        dao = UserDao

    }
    class UserData extends MutableLiveData<User> {
        public UserData(){
            super();
            User user = new User();
            setValue(user);
        }
        @Override
        protected void onActive(){
            super.onActive();
            new Thread(()->{
               userData.postValue(dao.get("admin1"));
            }).start();
        }

    }
    public MutableLiveData<User> get(){
        return userData;
    }
    public void reload(){
        api.get();
    }

}
