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
        ArrayList<Video> videos = new ArrayList<>();
        Video v =new Video(1,"test","test","test", Uri.parse(src),new ArrayList<String>(),0,new Date(),"test",new ArrayList<String>(),new ArrayList<Comment>());
        videos.add(v);
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);
    }

    private ArrayList<Video> loadVideosFromJson() {
        ArrayList<Video> videos = new ArrayList<>();
        try {
            // Open and read the json file
            AssetManager am = getAssets();
            InputStream is = getAssets().open("videos.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Converts the file to string
            String json = new String(buffer, "UTF-8");

            // Creates new Gson
            Gson gson = new Gson();

            // Parse the json file
            Type videoListType = new TypeToken<ArrayList<Video>>() {}.getType();
            videos = gson.fromJson(json, videoListType);
            Log.w("First",videos.get(0).getName());
        } catch (IOException e) {
            Log.w("Exit",e);
            e.printStackTrace();
        }
        return videos;
    }
}
