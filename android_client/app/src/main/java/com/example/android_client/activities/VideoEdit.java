package com.example.android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_client.R;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;


public class VideoEdit extends AppCompatActivity {
    private EditText editVideoName;
    private EditText editVideoDescription;
    private Button updateButton;
    private int videoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);

        editVideoName = findViewById(R.id.editVideoName);
        editVideoDescription = findViewById(R.id.editVideoDescription);
        updateButton = findViewById(R.id.updateButton);

        videoId = getIntent().getIntExtra("VIDEO_ID", -1);

        Video video = DataManager.findVideoById(videoId);
        if (video != null) {
            editVideoName.setText(video.getName());
            editVideoDescription.setText(video.getDescription());
        } else {
            Intent intent = new Intent(this, PageNotFound.class);
            startActivity(intent);
        }

        updateButton.setOnClickListener(v -> {
            String newName = editVideoName.getText().toString();
            String newDescription = editVideoDescription.getText().toString();
            if (newName.equals("") || newDescription.equals("")) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }
            DataManager.updateVideo(videoId, newName, newDescription);
            startActivity(new Intent(this, MyVideosPage.class));
        });
    }

    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() == null) {
            finish();
        }
    }


}
