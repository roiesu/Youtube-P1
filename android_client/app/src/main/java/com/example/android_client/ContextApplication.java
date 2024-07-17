package com.example.android_client;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ContextApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TESTING","YES");
        context = getApplicationContext();
        AppDB.initialize();
    }
    public static void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
