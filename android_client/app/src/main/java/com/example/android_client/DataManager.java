package com.example.android_client;

import android.content.Context;

import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class DataManager {
    private static String currentUsername;
    private static String currentUserId;
    private static String token;
    private static DataManager instance;
    private static boolean initialized;
    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        DataManager.initialized = initialized;
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

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        DataManager.currentUserId = currentUserId;
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