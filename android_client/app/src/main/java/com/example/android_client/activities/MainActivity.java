package com.example.android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_client.R;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
        if(!DataManager.isInitialized()) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            Type videoListType = new TypeToken<ArrayList<Video>>() {
            }.getType();
            DataManager.setUsersList(loadDataFromJson("users.json", userListType));
            DataManager.setVideoList(loadDataFromJson("videos.json", videoListType));
            DataManager.setInitialized(true);
        }

        setContentView(R.layout.activity_main);
        view = findViewById(R.id.main_page);
        view.setOnClickListener(view ->{
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.mainButton).setOnClickListener(view->{
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        });
        DataManager.printUsers();
    }
    private <T> ArrayList<T> loadDataFromJson(String fileName, Type type) {
        ArrayList<T> array = new ArrayList<>();
        try {
            // Open and read the json file
            InputStream is = getAssets().open(fileName);
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
}