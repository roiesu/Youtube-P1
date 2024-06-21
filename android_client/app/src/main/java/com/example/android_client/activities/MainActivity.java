package com.example.android_client.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_client.R;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        private TextView view;
        private RecyclerView videoRecyclerView;
        private VideoAdapter videoAdapter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            videoRecyclerView = findViewById(R.id.videoRecyclerView);
            videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            videoAdapter = new VideoAdapter(DataManager.getVideoList());
            videoRecyclerView.setAdapter(videoAdapter);
        }

        @Override
        protected void onResume() {
            super.onResume();
            // Update the adapter with the latest video list
            videoAdapter.updateVideos(DataManager.getVideoList());
            videoAdapter.notifyDataSetChanged();
        }
    }


}