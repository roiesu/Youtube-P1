package com.example.android_client.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.android_client.R;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.Utilities;
import com.example.android_client.adapters.CommentAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.view_models.CommentListViewModel;
import com.example.android_client.view_models.LikeViewModel;
import com.example.android_client.view_models.VideoViewModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class WatchingVideo extends AppCompatActivity {
    private VideoView videoView;
    private RecyclerView commentsList;
    private TextView commentsHeader;
    private com.google.android.material.button.MaterialButton likeButton;
    private Button commentButton;
    private Button shareButton;
    private EditText commentInput;
    private boolean likedVideo;
    private VideoViewModel video;
    private LikeViewModel likeViewModel;

    private CommentListViewModel commentListViewModel;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watching_video);

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        String appLinkAction = intent.get().getAction();
        String channel = "";
        String videoId = "";
        if (appLinkAction == null) {
            videoId = intent.get().getStringExtra("videoId");
            channel = intent.get().getStringExtra("channel");
        } else {
            DataManager.initializeData(this);
            String lastPathSegment = intent.get().getData().getLastPathSegment();
            if (lastPathSegment.matches("^\\d{0,9}$")) {
//                 id = Integer.parseInt(lastPathSegment);
            }
        }

        // Initialize views
        videoView = findViewById(R.id.videoView);
        commentsList = findViewById(R.id.commentsList);
        commentsHeader = findViewById(R.id.commentsTitle);
        shareButton = findViewById(R.id.shareButton);
        commentInput = findViewById(R.id.commentInput);
        commentButton = findViewById(R.id.commentButton);
        likeButton = findViewById(R.id.likeButton);

        video = new VideoViewModel(channel, videoId,this);
        video.getVideo().observe(this, video -> {
            if (video == null) {
                intent.set(new Intent(this, PageNotFound.class));
                startActivity(intent.get());
                finish();
            } else {
                initComments(video.get_id());
                likeViewModel = new LikeViewModel(DataManager.getCurrentUserId(), video.get_id(), this, video.getLikesNum());
                ((TextView) findViewById(R.id.videoTitle)).setText(video.getName());
                ((TextView) findViewById(R.id.videoViews)).setText(Utilities.numberFormatter(video.getViews()) + " Views");
                ((TextView) findViewById(R.id.videoDate)).setText("Uploaded at " + Utilities.formatDate(video.getDate()));
                ((TextView) findViewById(R.id.videoDescription)).setText(video.getDescription());
                ((TextView) findViewById(R.id.videoUploader)).setText(video.getUploader().getName());
                ImageView uploaderImage = findViewById(R.id.uploaderImage);
                Glide.with(this).load(video.getUploader().getImageFromServer()).into(uploaderImage);
                likeButton.setOnClickListener(view -> {
                    if (!likeViewModel.getIsLiked().getValue()) {
                        likeViewModel.like(video.getUploader().getUsername(), video.get_id());
                    } else {
                        likeViewModel.dislike(video.getUploader().getUsername(), video.get_id());
                    }
                });
                initVideo();




                AlertDialog shareDialog = createShareDialog(video.get_id());
                shareButton.setOnClickListener(l -> shareDialog.show());
                likeViewModel.getIsLiked().observe(this, isLiked -> {
                    changeLikeIcon(isLiked);
                });
                likeViewModel.getVideoLikes().observe(this,likeNumber->{
                    likeButton.setText(likeNumber + "");
                });
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }

    private void initVideo() {
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(video.getVideo().getValue().getVideoFromServer());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            videoView.start();
        });
    }
    private void initComments(String videoId){
        commentListViewModel = new CommentListViewModel();
        CommentAdapter adapter = new CommentAdapter(this);
        commentsList.setLayoutManager(new LinearLayoutManager(this));
        commentsList.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        commentsList.addItemDecoration(dividerItemDecoration);
        commentListViewModel.getComments().observe(this,commentsList->{
            adapter.setComments(commentsList);
            commentsHeader.setText(commentsList.size() + " Comments");
            commentButton.setOnClickListener(l -> commentVideo(adapter));
        });
        commentListViewModel.getCommentsByVideo(videoId);
    }
    private void commentVideo(CommentAdapter adapter) {
//        if (currentUser == null) {
//            return;
//        }
        String content = commentInput.getText().toString();
        if (content.matches("^[\\s \\r\\t\\n]*$")) {
            // Notify user
            return;
        }
//        video.addComment(currentUser.getUsername(), currentUser.getName(), content);
        commentInput.setText("");
        adapter.notifyItemInserted(0);
        commentsHeader.setText(adapter.getItemCount() + " Comments");
    }

    private AlertDialog createShareDialog(String videoId) {
        String shareLink = getResources().getString(R.string.url) + "/watch/" + videoId;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Copy to clipboard", (dialog, id1) -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("link", shareLink);
            clipboard.setPrimaryClip(clip);
        }).setNegativeButton("Share with Whatsapp", (dialog, id12) -> {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
            }
        }).setTitle("Share video");
        return dialogBuilder.create();
    }

    private void changeLikeIcon(Boolean isLiked) {
        if (isLiked) {
            likeButton.setIcon(getResources().getDrawable(R.drawable.ic_filled_like, getTheme()));
        } else {
            likeButton.setIcon(getResources().getDrawable(R.drawable.ic_like, getTheme()));
        }
    }
}
