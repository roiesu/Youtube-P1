package com.example.android_client.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.DataManager;
import com.example.android_client.activities.helpers.MediaPickerActivity;
import com.example.android_client.entities.Video;
import com.example.android_client.view_models.VideoViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class VideoEdit extends MediaPickerActivity {
    private EditText nameInput;
    private EditText descriptionInput;
    private EditText tagsInput;
    private Button updateButton;
    private Button uploadImageButton;
    private VideoViewModel videoViewModel;
    private String oldId, oldThumbnail;
    private boolean initialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_edit);

        nameInput = findViewById(R.id.name);
        descriptionInput = findViewById(R.id.description);
        tagsInput = findViewById(R.id.tags);
        targetImageView = findViewById(R.id.thumbnail);
        updateButton = findViewById(R.id.updateButton);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        videoViewModel = new VideoViewModel(this);
        MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
        finished.observe(this, data -> {
            if (data) {
                Intent intent = new Intent();
                intent.putExtra("position", getIntent().getIntExtra("position", -1));
                intent.putExtra("newName", videoViewModel.getVideo().getValue().getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        videoViewModel.getVideo().observe(this, video -> {
            if (video != null && finished.getValue() == false && initialized == false) {
                initialized = true;
                oldId = video.get_id();
                oldThumbnail = video.getThumbnail();
                nameInput.setText(video.getName());
                descriptionInput.setText(video.getDescription());
                tagsInput.setText(String.join(" ", video.getTags()));
                Glide.with(this).load(video.getThumbnailFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(targetImageView);

            } else if (video != null && finished.getValue() == false && initialized == true) {
                videoViewModel.editVideo(finished, oldId, oldThumbnail);
            } else if (video == null) {
                finish();
            }
        });

        uploadImageButton.setOnClickListener(view -> {
            this.pickImage();
        });

        updateButton.setOnClickListener(l -> {
            Video video = videoViewModel.getVideo().getValue();
            video.setName(nameInput.getText().toString());
            video.setDescription(descriptionInput.getText().toString());
            video.setDescription(descriptionInput.getText().toString());
            video.setTags(new ArrayList<>(Arrays.asList(tagsInput.getText().toString().split(" "))));
            if (imageUri != null) {
                video.setThumbnail(Utilities.createThumbnail(Utilities.imageUriToBitmap(this, imageUri)));
            }
            videoViewModel.setVideo(video);
        });

        String videoId = getIntent().getStringExtra("videoId");
        videoViewModel.fetchVideoById(DataManager.getCurrentUserId(), videoId);
    }
}
