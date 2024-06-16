package com.example.android_client.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.entities.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        RecyclerView lstVideos = findViewById(R.id.recyclerView);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        // Load videos from JSON
        ArrayList<Video> videos = loadVideosFromJson();
        VideoAdapter adapter = new VideoAdapter(this, videos);
        lstVideos.setAdapter(adapter);
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

            // Creates new Gson
            Gson gson = new Gson();

            // Parse the json file
            Type videoListType = new TypeToken<ArrayList<Video>>() {}.getType();
            videos = gson.fromJson(json, videoListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videos;
    }
}
