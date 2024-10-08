package com.example.android_client.repositories;

import androidx.lifecycle.LifecycleOwner;
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
    private LifecycleOwner owner;

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
            User user = dao.get(username);
            userData.postValue(user);
        }).start();
    }
    public LiveData<User> getUserById(String userId) {
        return dao.getUserById(userId);
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
        this.userData.observe(owner, data -> {
            if (data == null || data.get_id() == null) {
                return;
            }
            new Thread(() -> {
                dao.insert(data);
                login();
            }).start();
        });
        this.api.add(userData);
    }

    public void deleteUser(MutableLiveData finished) {
        User user = this.userData.getValue();
        this.userData.observe(owner, data -> {
            if (data == null) {
                new Thread(() -> {
                    dao.delete(user);
                    finished.postValue(1);
                }).start();
            }
        });
        this.api.deleteUser(this.userData);
    }

    public void editUser(User user,MutableLiveData<Integer> finished) {
        userData.observe(owner, data -> {
            if (data != null) {
                new Thread(() -> {
                    dao.update(data);
                    finished.postValue(2);
                }).start();
            }
        });
        this.api.editUser(userData, user);
    }

}


