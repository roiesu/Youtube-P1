package com.example.android_client.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class MyVideosPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button uploadVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_videos);

        recyclerView = findViewById(R.id.recyclerView);
        uploadVideoButton = findViewById(R.id.uploadVideoButton);

        if (recyclerView != null) {
            Log.d("MyVideosPage", "RecyclerView found");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            loadVideos();
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
            Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
            if (dividerDrawable != null) {
                dividerItemDecoration.setDrawable(dividerDrawable);
                recyclerView.addItemDecoration(dividerItemDecoration);
            }
        } else {
            Log.e("MyVideosPage", "RecyclerView is null");
        }

        uploadVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVideosPage.this, VideoUpload.class);
                startActivity(intent);
            }
        });
    }

    private void loadVideos() {
        List<Video> videos = DataManager.filterVideosBy(1, DataManager.getCurrentUser().getUsername());
        MyVideosAdapter adapter = new MyVideosAdapter(this, new ArrayList<>(videos));
        recyclerView.setAdapter(adapter);
    }
}
