package com.example.android_client.entities;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_client.R;
import com.example.android_client.adapters.MyVideosAdapter;
import java.util.ArrayList;
import java.util.List;

public class MyVideos extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_videos);
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            Log.d("MyVideosPage", "RecyclerView found");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            loadVideos();
        } else {
            Log.e("MyVideosPage", "RecyclerView is null");
        }
    }

    private void loadVideos() {
        List<Video> videos = DataManager.filterVideosBy(1, DataManager.getCurrentUser().getUsername());
        MyVideosAdapter adapter = new MyVideosAdapter(this, new ArrayList<>(videos));
        recyclerView.setAdapter(adapter);
    }
}