package com.example.android_client.activities;

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
            }
        });
    }
    private void createVideoObject() {
        String name = videoNameInput.getText().toString();
        String description = videoDescriptionInput.getText().toString();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(videoTagsInput.getText().toString().split(",")));

        // cheking the name, description and video
        if (videoUri == null || name.isEmpty() || description.isEmpty() ) {
            Toast.makeText(this, "Please fill name, description and select a video", Toast.LENGTH_SHORT).show();
            return;
        }
        // reating new ID
        List<Video> videoList = DataManager.getVideoList();
        VideoAdapter videoAdapter = new VideoAdapter(videoList);
        Video lastVideo = videoAdapter.getLastAddedVideo();
        int id  = (lastVideo != null) ? lastVideo.getId() + 1 : 1;

        String uploader = DataManager.getCurrentUser().getUsername();
        String displayUploader = DataManager.getCurrentUser().getName();
        String src = videoUri.toString();
        ArrayList<String> likes = new ArrayList<>();
        int views = 0;
        Date dateTime = new Date();
        ArrayList<Comment> comments = new ArrayList<>();

        Video newVideo = new Video(id,name, uploader, displayUploader, src, likes, views,
                dateTime, description, tags,comments);
        // Video newVideo = new Video(videoId, videoName, uploader, uploadDate, views, videoUri.toString());
        DataManager.addVideo(newVideo);

        Toast.makeText(this, "Video uploaded successfully with ID: " + newId,
                Toast.LENGTH_SHORT).show();
        // Clear inputs
        videoNameInput.setText("");
        videoDescriptionInput.setText("");
        videoTagsInput.setText("");
        previewVideo.setVideoURI(null);
    }

}
}
