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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadVideoButton = findViewById(R.id.uploadVideoButton);

        ArrayList<Video> videos = DataManager.filterVideosBy(1, DataManager.getCurrentUser().getUsername());
        MyVideosAdapter adapter = new MyVideosAdapter(this, videos);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(dividerItemDecoration);

        uploadVideoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyVideosPage.this, VideoUpload.class);
            startActivity(intent);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position = data.getIntExtra("videoPosition", 0);
        recyclerView.getAdapter().notifyItemChanged(position);

    }

    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() == null) {
            finish();
        }
    }
}
