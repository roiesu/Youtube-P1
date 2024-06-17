package com.example.android_client.activities;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.android_client.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.CommentAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;

import java.util.ArrayList;

public class WatchingVideo extends AppCompatActivity {
    private Video video;
    private VideoView videoView;
    private RecyclerView commentsList;
    private TextView commentsHeader;

    private Button likeButton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int id = intent.getIntExtra("videoId", 0);
        video = DataManager.findVideoById(id, true);
        if (video == null) {
            setContentView(R.layout.video_not_found_layout);
            return;
        }
        setContentView(R.layout.watching_video);
        ((TextView) findViewById(R.id.videoTitle)).setText(video.getName());
        ((TextView) findViewById(R.id.videoViews)).setText("" + video.getViews());
        ((TextView) findViewById(R.id.videoDate)).setText("" + video.getDate_time());
        ((TextView) findViewById(R.id.videoDescription)).setText(video.getDescription());
        ((TextView) findViewById(R.id.videoUploader)).setText(video.getDisplayUploader());
        likeButton = findViewById(R.id.likeButton);
        likeButton.setText(video.getLikes().size()+"");
        likeButton.setOnClickListener(view -> {
            if(DataManager.getCurrentUser()==null){
                return;
            }
            DataManager.likeVideo(video.getId(), DataManager.getCurrentUser().getUsername());
            ((TextView)view).setText(video.getLikes().size()+"");
        });
        videoView = initVideo();
        videoView.start();

        commentsList = findViewById(R.id.commentsList);
        commentsList.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter adapter = new CommentAdapter(this, video.getComments());
        commentsList.setAdapter(adapter);

        commentsHeader = findViewById(R.id.commentsTitle);
        commentsHeader.setText(adapter.getItemCount() + " Comments");


    }

    private Uri getVideoURI() {
        int videoResId = getResources().getIdentifier(video.getSrc(), "raw", getPackageName());
        String uriString = "android.resource://" + getPackageName() + "/" + videoResId;
        return Uri.parse(uriString);
    }

    private VideoView initVideo() {
        VideoView temp = findViewById(R.id.videoView);
        temp.setVideoURI(getVideoURI());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(temp);
        temp.setMediaController(mediaController);
        return temp;

    }
}
