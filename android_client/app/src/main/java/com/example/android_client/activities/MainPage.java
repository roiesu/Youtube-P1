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
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.VideoPreview;
import java.util.ArrayList;


public class MainPage extends AppCompatActivity {

    private RecyclerView videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        // Get previews
        ArrayList<VideoPreview> videos = new ArrayList<>();
        for(Video video:DataManager.getVideoList()){
            videos.add(video.toPreview(this));
        }
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);
    }

}
