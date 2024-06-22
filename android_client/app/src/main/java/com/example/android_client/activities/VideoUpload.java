package com.example.android_client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

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
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                videoUri = uri;
                previewVideo.setVideoURI(videoUri);
                previewVideo.start();
            }
        });
        uploadVideoButton.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                    .build());
        });
        submitVideoDetailsButton.setOnClickListener(view -> {
            if (videoUri == null || videoNameInput.getText().toString().equals("") || videoDescriptionInput.getText().toString().equals("")) {
                Toast.makeText(this, "Video, Name and Description are required", Toast.LENGTH_SHORT).show();
                return;
            }
            createVideoObject();
        });
    }

    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() == null) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("mediaPicker", (Parcelable) pickMedia);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (savedInstanceState != null) {
            pickMedia = (ActivityResultLauncher<PickVisualMediaRequest>) savedInstanceState.get("mediaPicker");
        }
    }

    private void createVideoObject() {
        String name = videoNameInput.getText().toString();
        String description = videoDescriptionInput.getText().toString();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(videoTagsInput.getText().toString().split(",")));
        String src = videoUri.toString();
        DataManager.createVideo(name, DataManager.getCurrentUser(), src, description, tags, this);
        startActivity(new Intent(this, MyVideosPage.class));

    }
}
