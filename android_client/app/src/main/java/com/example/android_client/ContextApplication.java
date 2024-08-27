package com.example.android_client;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ContextApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
