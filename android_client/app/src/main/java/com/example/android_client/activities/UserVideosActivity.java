package com.example.android_client.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.AppDB;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.repositories.VideoRepository;

public class UserVideosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private VideoRepository videoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_videos);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);
        String userId = getIntent().getStringExtra("USER_ID");
        videoRepository = new VideoRepository(AppDB.getInstance().videoDao());

        if (userId != null) {
            videoRepository.getVideosByUserId(userId).observe(this, videos -> {
                if (videos != null) {
                    videoAdapter.setVideos(videos);
                }
            });
        }
    }
}
