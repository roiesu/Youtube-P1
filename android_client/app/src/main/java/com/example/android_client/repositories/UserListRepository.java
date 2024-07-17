package com.example.android_client.repositories;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.AppDB;
import com.example.android_client.api.UserAPI;
import com.example.android_client.dao.UserDao;
import com.example.android_client.entities.User;

import java.util.List;

public class UserListRepository {
    private UserDao dao;
    private UserListData userListData;
    private UserAPI api;

    public UserListRepository() {
        api = new UserAPI();
        userListData = new UserListData();
        // Room
        AppDB database = AppDB.getInstance();
        dao = database.userDao();
    }

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
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

    public MutableLiveData<List<User>> get() {
        return userListData;
    }

    public void init(LifecycleOwner lifecycleOwner) {
        api.getAll(userListData);
        userListData.observe(lifecycleOwner, list -> {
            dao.deleteAll();
            User [] userArray = list.toArray(new User[0]);
            dao.insert(userArray);
            List<User> users = dao.index();
        });
    }
}
