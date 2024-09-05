
package com.example.android_client.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.ContextApplication;
import com.example.android_client.R;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.Utilities;
import com.example.android_client.DataManager;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.fragments.CommentsBottomSheet;
import com.example.android_client.view_models.DatabaseViewModel;
import com.example.android_client.view_models.LikeViewModel;
import com.example.android_client.view_models.VideoWithUserListViewModel;
import com.example.android_client.view_models.VideoWithUserViewModel;

import java.util.concurrent.atomic.AtomicReference;

public class WatchingVideo extends AppCompatActivity {
    private VideoView videoView;
    private RecyclerView recommendationsList;
    private com.google.android.material.button.MaterialButton likeButton;
    private Button shareButton;
    private VideoWithUserViewModel video;
    private LikeViewModel likeViewModel;
    private VideoWithUserListViewModel recommendationViewModel;
    private DatabaseViewModel databaseViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watching_video);
        videoView = findViewById(R.id.videoView);
        shareButton = findViewById(R.id.shareButton);
        likeButton = findViewById(R.id.likeButton);
        databaseViewModel = new DatabaseViewModel();
        databaseViewModel.init(this);
        databaseViewModel.getInitialized().observe(this, value -> {
            if (value == true) {
                DataManager.setInitialized(true);
                resumeOnCreate();
            }
        });


    }

    public void resumeOnCreate() {
        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        String appLinkAction = intent.get().getAction();
        String channel = "";
        String videoId = "";
        if (appLinkAction == null) {
            videoId = intent.get().getStringExtra("videoId");
            channel = intent.get().getStringExtra("channel");
        } else {
            videoId = intent.get().getData().getQueryParameters("v").get(0);
            channel = intent.get().getData().getQueryParameters("channel").get(0);
        }

        // Initialize views
        video = new VideoWithUserViewModel(channel, videoId, this);
        video.getVideo().observe(this, video -> {
            if (video == null) {
                intent.set(new Intent(this, PageNotFound.class));
                startActivity(intent.get());
                finish();
            } else {
                initRecommendations(video.getUploader().getUsername(),video.get_id());
                initComments(video.get_id(), video.getUploader().getUsername());
                likeViewModel = new LikeViewModel(DataManager.getCurrentUserId(), video.get_id(),this);
                ((TextView) findViewById(R.id.videoTitle)).setText(video.getName());
                ((TextView) findViewById(R.id.videoViews)).setText(Utilities.numberFormatter(video.getViews()) + " Views");
                ((TextView) findViewById(R.id.videoDate)).setText("Uploaded at " + Utilities.formatDate(video.getDate()));
                ((TextView) findViewById(R.id.videoDescription)).setText(video.getDescription());
                ((TextView) findViewById(R.id.videoUploader)).setText(video.getUploader().getName());
                ImageView uploaderImage = findViewById(R.id.uploaderImage);
                Glide.with(this).load(video.getUploader().getImageFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(uploaderImage);
                uploaderImage.setOnClickListener(v -> {
                    Intent channelIntent = new Intent(this, ChannelActivity.class);
                    channelIntent.putExtra("USER_ID", video.getUploader().get_id());
                    this.startActivity(channelIntent);
                });
                likeButton.setOnClickListener(view -> {
                    if (DataManager.getCurrentUsername() == null) {
                        ContextApplication.showToast("Can't like video if not logged in");
                    } else if (!likeViewModel.getIsLiked().getValue()) {
                        likeViewModel.like(video.getUploader().getUsername(), video.get_id());
                    } else {
                        likeViewModel.dislike(video.getUploader().getUsername(), video.get_id());
                    }
                });
                initVideo();


                AlertDialog shareDialog = createShareDialog(video.get_id(), video.getUploader().get_id());
                shareButton.setOnClickListener(l -> shareDialog.show());
                likeViewModel.getIsLiked().observe(this, isLiked -> {
                    changeLikeIcon(isLiked);
                });
                likeViewModel.getVideoLikes().observe(this, likeNumber -> {
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
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
        });
    }

    private void initVideo() {
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(video.getVideo().getValue().getVideoFromServer());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
        });
    }
    private void initRecommendations(String videoUploader, String videoId){
        recommendationsList = findViewById(R.id.recList);
        recommendationViewModel = new VideoWithUserListViewModel(this);
        VideoAdapter adapter = new VideoAdapter(this);
        recommendationsList.setLayoutManager(new LinearLayoutManager(this));
        recommendationsList.setAdapter(adapter);
        recommendationViewModel.getVideos().observe(this,data->{
            if(data!=null) {
                adapter.setVideos(data);
                recommendationViewModel.getVideos().setValue(null);
            }
        });
        recommendationViewModel.getRecommendations(videoUploader,videoId);
    }
    private void initComments(String videoId, String videoUploader) {
        findViewById(R.id.showComments).setOnClickListener(l->{
            CommentsBottomSheet commentsBottomSheet = new CommentsBottomSheet(this,videoUploader,videoId);
            commentsBottomSheet.show(getSupportFragmentManager());
        });
    }



    private AlertDialog createShareDialog(String videoId, String userId) {
        String shareLink = getResources().getString(R.string.url) + "/watch?channel=" + userId + "&v=" + videoId;
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
            likeButton.setIcon(getResources().getDrawable(R.drawable.png_filled_like, getTheme()));
        } else {
            likeButton.setIcon(getResources().getDrawable(R.drawable.png_like, getTheme()));
        }
    }
}
