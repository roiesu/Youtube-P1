package com.example.android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.CommentAdapter;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;

public class WatchingVideo extends AppCompatActivity {
    private Video video;
    private VideoView videoView;
    private RecyclerView commentsList;

    private TextView commentsHeader;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int id=intent.getIntExtra("videoId",0);
        video = DataManager.findVideoById(id);
        if(video==null){
            setContentView(R.layout.video_not_found_layout);
            return;
        }
        setContentView(R.layout.watching_video);
        ((TextView)findViewById(R.id.videoTitle)).setText(video.getName());
        ((TextView)findViewById(R.id.videoViews)).setText(""+video.getViews());
        ((TextView)findViewById(R.id.videoDate)).setText(""+video.getDate_time());
        ((TextView)findViewById(R.id.videoDescription)).setText(video.getDescription());
        ((TextView)findViewById(R.id.videoUploader)).setText(video.getDisplayUploader());
        commentsHeader= findViewById(R.id.commentsTitle);
        commentsHeader.setText(getCommentsTitle());
        videoView= findViewById(R.id.videoView);

        commentsList = findViewById(R.id.commentsList);
        commentsList.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter adapter = new CommentAdapter(this,video.getComments());
        commentsList.setAdapter(adapter);
    }
    private String getCommentsTitle(){
        int count = video.getComments().size();
        String text=count+" Comment";
        if(count!=1)
            text+="s";
        return text;

    }
}
