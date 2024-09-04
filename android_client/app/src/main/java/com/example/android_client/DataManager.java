package com.example.android_client;

import android.content.Context;
import android.content.Intent;

import com.example.android_client.activities.SignIn;


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

    public static String getTokenHeader() {
        return token != null ? "Bearer " + token : null;
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
        Context context = ContextApplication.context;
        Intent intent = new Intent(context, SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}