package com.example.android_client.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_client.AppDB;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.repositories.UserRepository;
import com.example.android_client.repositories.VideoRepository;

public class ChannelActivity extends AppCompatActivity {

    private TextView channelName;
    private ImageView profilePicture;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private VideoRepository videoRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        channelName = findViewById(R.id.channelHeader);
        profilePicture = findViewById(R.id.channelImage);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);

        String userId = getIntent().getStringExtra("USER_ID");
        userRepository = new UserRepository(this);
        videoRepository = new VideoRepository(AppDB.getInstance().videoDao());
        if (userId != null) {
            userRepository.getUserById(userId).observe(this, user -> {
                if (user != null) {
                    channelName.setText(user.getName() + "'s channel");
                    Glide.with(this)
                            .load(user.getImageFromServer())
                            .circleCrop()
                            .into(profilePicture);
                }
            });

            videoRepository.getVideosByUserId(userId).observe(this, videos -> {
                if (videos != null) {
                    videoAdapter.setVideos(videos);                  }
            });
        }
    }
}
