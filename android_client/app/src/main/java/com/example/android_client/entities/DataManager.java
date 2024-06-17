package com.example.android_client.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class DataManager {
    private static Context context;
    public static ArrayList<User> usersList;
    public static ArrayList<Video> videoList;

    public static User currentUser;

    //Weird magic class stuff
    private static DataManager instance;

    private DataManager() {}

    public static void setUsersList(ArrayList<User> users){
        usersList=users;
    }
    public static void setVideoList(ArrayList<Video> videos){
        videoList=videos;
    }

    public static void setCurrentUser(User newUser){
        currentUser=newUser;
    }
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    public static User findUser(String username){
        for(User user: usersList){
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
    public static void addUser(User user){
        usersList.add(user);
    }
}