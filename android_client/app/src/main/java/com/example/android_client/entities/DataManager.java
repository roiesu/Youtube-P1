package com.example.android_client.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


public class DataManager {
    private static ArrayList<User> usersList;
    private static ArrayList<Video> videoList;

    public static User currentUser;
    private static DataManager instance;

    private static boolean initialized;

    private DataManager() {
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        DataManager.initialized = initialized;
    }
    public static ArrayList<User> getUsersList() {
        return usersList;
    }

    public static ArrayList<Video> getVideoList() {
        return videoList;
    }

    public static void setUsersList(ArrayList<User> users) {
        usersList = users;
    }

    public static void setVideoList(ArrayList<Video> videos) {
        videoList = videos;
    }

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    public static void addUser(User user){
        usersList.add(user);
    }
    public static User findUser(String username) {
        for (User user : usersList) {
            Log.w("Useranme", user.getUsername());
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static Video findVideoById(int id,boolean addView) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                if(addView){
                    video.addView();
                }
                return video;
            }
        }
        return null;
    }
    public static void findVideoAndLike(int id,String username){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.like(username);
            }
        }
    }
    public static void commentVideo(int id,String username,String displayUsername,String text){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.addComment(username,displayUsername,text);
            }
        }
    }
    public static void removeCommentFromVideo(int id, String username, Date date){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.deleteComment(username,date);
            }
        }
    }

    public static void updateCommentInVideo(int id, String username, Date date, String newText){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.editComment(username,date,newText);
            }
        }
    }
    // Debugging
    public static void printUsers(){
        for (User user:usersList){
            Log.w("Username",user.getUsername());
        }
    }


}