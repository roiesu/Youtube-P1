package com.example.android_client;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android_client.view_models.UserListViewModel;
import com.example.android_client.view_models.VideoListViewModel;

public class ContextApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.w("TESTING","YES");
    }
    public static void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
