package com.example.android_client.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_client.R;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.android_client.entities.Video;

public class WatchingVideo extends AppCompatActivity {
    private Video video;
    private VideoView videoView;
    private RecyclerView commentsList;
    private TextView commentsHeader;

    private com.google.android.material.button.MaterialButton likeButton;
    private Button commentButton;
    private Button shareButton;
    private EditText commentInput;
    private User currentUser;
    private boolean likedVideo;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String appLinkAction = intent.getAction();
        int id = 0;
        if (appLinkAction == null) {
            id = intent.getIntExtra("videoId", 0);
        } else {
            DataManager.initializeData(this);
            String lastPathSegment = intent.getData().getLastPathSegment();
            if (lastPathSegment.matches("^\\d{0,9}$")) {
                id = Integer.parseInt(lastPathSegment);
            }
        }
        video = DataManager.findVideoById(id, true);
        if (video == null) {
            intent = new Intent(this, PageNotFound.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.watching_video);
        ((TextView) findViewById(R.id.videoTitle)).setText(video.getName());
        ((TextView) findViewById(R.id.videoViews)).setText("" + video.getViews());
        ((TextView) findViewById(R.id.videoDate)).setText(Utilities.formatDate(video.getDate_time()));
        ((TextView) findViewById(R.id.videoDescription)).setText(video.getDescription());
        ((TextView) findViewById(R.id.videoUploader)).setText(video.getDisplayUploader());
        currentUser = DataManager.getCurrentUser();
        shareButton = findViewById(R.id.shareButton);
        commentInput = findViewById(R.id.commentInput);
        commentButton = findViewById(R.id.commentButton);
        likeButton = findViewById(R.id.likeButton);
        likeButton.setText(video.getLikes().size() + "");
        likeButton.setOnClickListener(view -> {
            if (DataManager.getCurrentUser() == null) {
                return;
            }
            DataManager.likeVideo(video.getId(), DataManager.getCurrentUser().getUsername());
            ((TextView) view).setText(video.getLikes().size() + "");
            likedVideo = !likedVideo;
            changeLikeIcon();
        });
        isLiked();
        initVideo();

        commentsList = findViewById(R.id.commentsList);
        commentsList.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter adapter = new CommentAdapter(this, video.getComments());
        commentsList.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        commentsList.addItemDecoration(dividerItemDecoration);

        commentsHeader = findViewById(R.id.commentsTitle);
        commentsHeader.setText(adapter.getItemCount() + " Comments");

        commentButton.setOnClickListener(l -> {
            commentVideo(adapter);
        });
        AlertDialog shareDialog = createShareDialog(video.getId());
        shareButton.setOnClickListener(l -> {
            shareDialog.show();
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
        videoView.setVideoURI(Uri.parse(video.getSrc()));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            videoView.start();
        });
    }

    private void commentVideo(CommentAdapter adapter) {
        if (currentUser == null) {
            return;
        }
        String content = commentInput.getText().toString();
        if (content.matches("^[\\s \\r\\t\\n]*$")) {
            // Notify user
            return;
        }
        video.addComment(currentUser.getUsername(), currentUser.getName(), content);
        commentInput.setText("");
        adapter.notifyItemInserted(0);
        commentsHeader.setText(adapter.getItemCount() + " Comments");
    }

    private AlertDialog createShareDialog(int videoId) {
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

    private void isLiked() {
        if (currentUser != null) {
            for (String username : video.getLikes()) {
                if (username.equals(currentUser.getUsername())) {
                    likedVideo = true;
                    changeLikeIcon();
                    return;
                }
            }
        }
        likedVideo = false;
    }

    private void changeLikeIcon() {
        if (likedVideo) {
            likeButton.setIcon(getResources().getDrawable(R.drawable.ic_filled_like, getTheme()));
        } else {
            likeButton.setIcon(getResources().getDrawable(R.drawable.ic_like, getTheme()));
        }
    }
}
