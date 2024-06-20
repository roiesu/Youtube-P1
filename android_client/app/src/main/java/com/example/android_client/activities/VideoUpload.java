package com.example.android_client.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_client.R;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class VideoUpload extends AppCompatActivity {
    private VideoView previewVideo;
    private Uri videoUri;
    private Button uploadVideoButton, submitVideoDetailsButton;
    private EditText videoNameInput, videoDescriptionInput, videoTagsInput;
    private ActivityResultCallback<Uri> videoCallback = uri -> {
        if (uri != null) {
            videoUri = uri;
            previewVideo.setVideoURI(videoUri);
            previewVideo.start();
        }
    };
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(), videoCallback);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video);

        previewVideo = findViewById(R.id.videoPreview);
        uploadVideoButton = findViewById(R.id.videoInput);
        videoNameInput = findViewById(R.id.videoNameInput);
        videoDescriptionInput = findViewById(R.id.videoDescriptionInput);
        videoTagsInput = findViewById(R.id.videoTagsInput);
        submitVideoDetailsButton = findViewById(R.id.submitVideoButton);
        uploadVideoButton.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                    .build());
        });
        submitVideoDetailsButton.setOnClickListener(view -> {
            if (videoUri != null) {
                createVideoObject();
                finish();
            }
        });
    }

    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() == null) {
            finish();
        }
    }

    private void createVideoObject() {
        String name = videoNameInput.getText().toString();
        String description = videoDescriptionInput.getText().toString();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(videoTagsInput.getText().toString().split(",")));
        String src = videoUri.toString();
        DataManager.createVideo(name, DataManager.getCurrentUser(), src, description, tags, this);
    }
}
