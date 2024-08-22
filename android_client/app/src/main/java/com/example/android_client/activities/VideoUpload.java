package com.example.android_client.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
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

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.Comment;
import com.example.android_client.view_models.VideoViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class VideoUpload extends AppCompatActivity {
    private VideoView previewVideo;
    private Uri videoUri;
    private Button uploadVideoButton, submitVideoDetailsButton;
    private EditText videoNameInput, videoDescriptionInput, videoTagsInput;
    private VideoWithUser video;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    private VideoViewModel videoViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video);
        video = new VideoWithUser();
        videoViewModel = new VideoViewModel(this);
        videoViewModel.getVideo().observe(this, data -> {
            if(data.get_id()!=null) {
                ContextApplication.showToast(data.get_id());
            }
            else{
//                ContextApplication.showToast("yes");
            }
        });
        previewVideo = findViewById(R.id.videoPreview);
        uploadVideoButton = findViewById(R.id.videoInput);
        videoNameInput = findViewById(R.id.videoNameInput);
        videoDescriptionInput = findViewById(R.id.videoDescriptionInput);
        videoTagsInput = findViewById(R.id.videoTagsInput);
        submitVideoDetailsButton = findViewById(R.id.submitVideoButton);
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                videoUri = uri;
                String data = Utilities.videoUriToBase64(this, uri);
                video.setSrc(data);
                previewVideo.setVideoURI(videoUri);
                createVideoDetails(uri, video);
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
        if (DataManager.getCurrentUsername() == null) {
            finish();
        }
    }

    private void createVideoObject() {
        video.setName(videoNameInput.getText().toString());
        video.setDescription(videoDescriptionInput.getText().toString());
        video.setTags(new ArrayList<>(Arrays.asList(videoTagsInput.getText().toString().split(","))));
        video.setUploader(new User(DataManager.getCurrentUsername()));
        videoViewModel.setVideo(video);
        videoViewModel.uploadVideo();
    }

    public void createVideoDetails(Uri uri, Video video) {
        try {
            MediaMetadataRetriever mediaRetriever = new MediaMetadataRetriever();
            mediaRetriever.setDataSource(this, uri);
            String time = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long seconds = (long) Math.floor(Long.parseLong(time) / 1000);
            video.setDuration(seconds);
            video.setThumbnail(Utilities.bitmapToBase64(mediaRetriever.getFrameAtTime(), Utilities.IMAGE_TYPE));
            mediaRetriever.release();
        } catch (Exception ex) {
            Log.w("Error", ex.toString());
        }
    }
}
