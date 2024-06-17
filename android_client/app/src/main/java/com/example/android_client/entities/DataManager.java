package com.example.android_client.entities;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


public class DataManager {
    private static ArrayList<User> usersList;
    private static ArrayList<Video> videoList;

    private static User currentUser;
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
    public static void likeVideo(int id, String username){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.like(username);
                return;
            }
        }
    }
    public static void commentVideo(int id,String username,String displayUsername,String text){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.addComment(username,displayUsername,text);
                return;
            }
        }
    }
    public static void removeCommentFromVideo(int id, String username, Date date){
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.deleteComment(username,date);
                return;
            }
        }
    }

    public void updateCommentInVideo(int id, String username, Date date, String newText){
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

    public static User getCurrentUser() {
        return currentUser;
    }
}