package com.example.android_client.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import com.example.android_client.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class DataManager {
    public static final int FILTER_UPLOADER_KEY = 1;
    public static final int FILTER_TITLE_KEY = 2;
    private static ArrayList<User> usersList;
    private static ArrayList<Video> videoList;
    private static User currentUser;
    private static String currentUsername;
    private static String currentUserId;
    private static String token;
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

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static String getToken() {
        return token;
    }

    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public static void initializeData(Context context) {
        if (DataManager.isInitialized()) {
            return;
        }
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        Type videoListType = new TypeToken<ArrayList<Video>>() {
        }.getType();
        DataManager.setUsersList(loadDataFromJson("users.json", userListType, context));
        DataManager.setVideoList(loadDataFromJson("videos.json", videoListType, context));
        for (User user : usersList) {
            user.setImage(Utilities.getResourceUriString(context, user.getImage(), "drawable"));
        }
//        for (Video video : videoList) {
//            video.setSrc(Utilities.getResourceUriString(context, video.getSrc(), "raw"));
//            video.createVideoDetails(context);
//        }
        DataManager.setInitialized(true);
    }

    private static <T> ArrayList<T> loadDataFromJson(String fileName, Type type, Context context) {
        ArrayList<T> array = new ArrayList<>();
        try {
            // Open and read the json file
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Converts the file to string
            String json = new String(buffer, "UTF-8");
            // Parse the json file
            Gson gson = new Gson();

            array = gson.fromJson(json, type);
            return array;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        DataManager.currentUserId = currentUserId;
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void Logout() {
        DataManager instance = DataManager.getInstance();
        instance.currentUsername = null;
        instance.token = null;
        instance.currentUserId = null;
    }

}