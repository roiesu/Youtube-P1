package com.example.android_client.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.MyVideosAdapter;
import com.example.android_client.entities.Video;
import com.example.android_client.view_models.VideoListViewModel;

import java.util.ArrayList;

public class MyVideosPage extends AppCompatActivity {

    private RecyclerView videosList;
    private Button uploadVideoButton;
    private Button editDetailsButton;
    private VideoListViewModel videoListViewModel;
    private MyVideosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_videos);
        videosList = findViewById(R.id.recyclerView);
        videosList.setLayoutManager(new LinearLayoutManager(this));
        uploadVideoButton = findViewById(R.id.uploadVideoButton);
        editDetailsButton = findViewById(R.id.editUserButton);
        ActivityResultLauncher<Intent> uploadVideoResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        videoListViewModel.addToMyVideos(intent.getStringExtra("videoId"), intent.getStringExtra("uploaderId"));
                    }
                });
        ActivityResultLauncher<Intent> editVideoResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        int position = intent.getIntExtra("position", 0);
                        String newName = intent.getStringExtra("newName");
                        Video video = videoListViewModel.getVideos().getValue().get(position);
                        video.setName(newName);
                        adapter.notifyItemChanged(position);
                    }
                });

        videoListViewModel = new VideoListViewModel(this);
        adapter = new MyVideosAdapter(this, new ArrayList<>(), this, editVideoResultLauncher);
        videosList.setAdapter(adapter);
        videoListViewModel.getVideos().observe(this, videos -> {
            if (adapter.getItemCount() == 0) {
                adapter.setVideos(videos);
            } else {
                adapter.notifyItemInserted(videos.size() - 1);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videosList.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        videosList.addItemDecoration(dividerItemDecoration);
        videoListViewModel.getVideosDetailsByUser();
        uploadVideoButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoUpload.class);
            uploadVideoResultLauncher.launch(intent);
        });
    }
}

