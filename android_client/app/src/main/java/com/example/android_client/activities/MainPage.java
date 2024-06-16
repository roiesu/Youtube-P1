package com.example.android_client.activities;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPage extends AppCompatActivity {

    private RecyclerView videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));

        String src= "android.resource://"+getPackageName()+"/"+R.raw.lukaku;
        // Load videos from JSON
        ArrayList<Video> videos = loadVideosFromJson();
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);
    }

    private ArrayList<Video> loadVideosFromJson() {
        ArrayList<Video> videos = new ArrayList<>();
        try {
            // Open and read the json file
            InputStream is = getAssets().open("videos.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Converts the file to string
            String json = new String(buffer, "UTF-8");
            // Parse the json file
            Log.w("Test",json);
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Video>>(){}.getType();
            videos = gson.fromJson(json, listType);

            Log.w("Test",videos.get(0).toString());


        } catch (IOException e) {
            Log.w("Exit",e);
            e.printStackTrace();
        }
        return videos;
    }
}
