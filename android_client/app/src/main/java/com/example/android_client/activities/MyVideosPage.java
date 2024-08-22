package com.example.android_client.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.MyVideosAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;
import com.example.android_client.view_models.VideoListViewModel;

import java.util.ArrayList;

public class MyVideosPage extends AppCompatActivity {

    private RecyclerView videosList;
    private Button uploadVideoButton;
    private VideoListViewModel videoListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_videos);
        videosList = findViewById(R.id.recyclerView);
        videosList.setLayoutManager(new LinearLayoutManager(this));
        uploadVideoButton = findViewById(R.id.uploadVideoButton);
        videoListViewModel = new VideoListViewModel(this);

        MyVideosAdapter adapter = new MyVideosAdapter(this, new ArrayList<>());
        videosList.setAdapter(adapter);
        videoListViewModel.getVideos().observe(this,videos -> {
            adapter.setVideos(videos);
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videosList.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        videosList.addItemDecoration(dividerItemDecoration);
        videoListViewModel.getVideosDetailsByUser();
        uploadVideoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyVideosPage.this, VideoUpload.class);
            startActivity(intent);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position = data.getIntExtra("videoPosition", 0);
        videosList.getAdapter().notifyItemChanged(position);

    }

    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() == null) {
            finish();
        }
    }
}
