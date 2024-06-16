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
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.VideoPreview;
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
        ArrayList<User> videos2 = DataManager.getInstance().usersList;
        Log.w("Video",videos2.get(0).getName());
        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        Log.w("Test1",getPackageName());
        Log.w("Test1", String.valueOf(R.raw.lukaku));
        String src= "android.resource://"+getPackageName()+"/"+R.raw.lukaku;
        // Get previews
        ArrayList<VideoPreview> videos = new ArrayList<>();
        for(Video video:DataManager.videoList){
            videos.add(video.toPreview());
        }
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);
    }
}
