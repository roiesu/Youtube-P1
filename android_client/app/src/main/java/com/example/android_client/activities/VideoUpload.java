package com.example.android_client.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;
import com.example.android_client.view_models.VideoViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class VideoUpload extends AppCompatActivity {
    private VideoView previewVideo;
    private Uri videoUri;
    private Button uploadVideoButton, submitVideoDetailsButton;
    private EditText videoNameInput, videoDescriptionInput, videoTagsInput;
    private Video video;
    private ImageView goBackButton;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    private VideoViewModel videoViewModel;
    private MutableLiveData<Boolean> finished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video);
        video = new VideoWithUser();
        videoViewModel = new VideoViewModel(this);
        goBackButton = findViewById(R.id.goBack);
        goBackButton.setOnClickListener(l -> {
            finish();
        });
        finished = new MutableLiveData<>();
        finished.observe(this, data -> {
            if (data) {
                Intent result = new Intent();
                result.putExtra("videoId", videoViewModel.getVideo().getValue().get_id());
                result.putExtra("uploaderId", videoViewModel.getVideo().getValue().getUploaderId());
                setResult(RESULT_OK, result);
                finish();
            }
        });
        previewVideo = findViewById(R.id.videoPreview);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(previewVideo);
        previewVideo.setMediaController(mediaController);
        previewVideo.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            previewVideo.start();
        });
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
                previewVideo.start();
            }
        });
        uploadVideoButton.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                    .build());
        });
        submitVideoDetailsButton.setOnClickListener(view -> {
            createVideoObject();
        });

    }

    private void createVideoObject() {
        createVideoDetails(videoUri, video);
        video.setName(videoNameInput.getText().toString());
        video.setDescription(videoDescriptionInput.getText().toString());
        video.setTags(new ArrayList<>(Arrays.asList(videoTagsInput.getText().toString().split(","))));
        video.setUploaderId(DataManager.getCurrentUsername());
        videoViewModel.setVideo(video);
        videoViewModel.uploadVideo(finished);
    }

    public String createThumbnail(MediaMetadataRetriever mediaRetriever) {
        Bitmap thumbnail = mediaRetriever.getFrameAtTime(previewVideo.getCurrentPosition() * 1000L, MediaMetadataRetriever.OPTION_CLOSEST);
        Bitmap resultBitmap = Bitmap.createBitmap(320, 180, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, 320, 180, paint);
        if (thumbnail != null) {
            int thumbnailWidth = thumbnail.getWidth();
            int thumbnailHeight = thumbnail.getHeight();
            float aspectRatio = (float) thumbnailWidth / thumbnailHeight;

            int newWidth = 320;
            int newHeight = (int) (newWidth / aspectRatio);

            if (newHeight > 180) {
                newHeight = 180;
                newWidth = (int) (newHeight * aspectRatio);
            }
            Bitmap resizedThumbnail = Bitmap.createScaledBitmap(thumbnail, newWidth, newHeight, true);
            int left = (320 - newWidth) / 2;
            int top = (180 - newHeight) / 2;
            canvas.drawBitmap(resizedThumbnail, left, top, null);
            thumbnail.recycle();
        }
        return Utilities.bitmapToBase64(resultBitmap, Utilities.IMAGE_TYPE);

    }

    public void createVideoDetails(Uri uri, Video video) {
        try {
            MediaMetadataRetriever mediaRetriever = new MediaMetadataRetriever();
            mediaRetriever.setDataSource(this, uri);
            String time = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long seconds = (long) Math.floor(Long.parseLong(time) / 1000);
            video.setDuration(seconds);
            video.setThumbnail(createThumbnail(mediaRetriever));
            mediaRetriever.release();
        } catch (Exception ex) {
            Log.w("Error", ex.toString());
        }
    }
}
