package com.example.android_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.VideoEdit;
import com.example.android_client.activities.WatchingVideo;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;
import com.example.android_client.view_models.VideoViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class MyVideosAdapter extends RecyclerView.Adapter<MyVideosAdapter.VideoViewHolder> {
    private Context context;
    private VideoViewModel videoViewModel;
    private List<Video> videos;
    private LifecycleOwner owner;
    private ActivityResultLauncher editVideoLauncher;

    public MyVideosAdapter(Context context, List<Video> videos, LifecycleOwner owner, ActivityResultLauncher editVideoLauncher) {
        this.context = context;
        this.videos = videos;
        this.owner = owner;
        this.videoViewModel = new VideoViewModel(owner);
        this.editVideoLauncher = editVideoLauncher;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        this.notifyDataSetChanged();
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.videoName.setText(video.getName());
        holder.videoDate.setText(Utilities.formatDate(video.getDate()));
        holder.viewsNum.setText(Utilities.shortCompactNumber(video.getViews()));
        holder.likesNum.setText(Utilities.shortCompactNumber(video.getLikesNum()));
        holder.commentNum.setText(Utilities.shortCompactNumber(video.getCommentsNum()));
        holder.videoDuration.setText(Utilities.secondsToTime(video.getDuration()));
        Glide.with(context).load(video.getThumbnailFromServer()).into(holder.videoThumbnail);
        PopupMenu popupMenu = createOptionsMenu(holder.videoOptions, video.get_id(), position);
        holder.videoOptions.setOnClickListener(l -> {
            popupMenu.show();
        });

        holder.videoThumbnail.setOnClickListener(l -> {
            Intent intent = new Intent(context, WatchingVideo.class);
            intent.putExtra("videoId", video.get_id());
            intent.putExtra("channel", DataManager.getCurrentUserId());
            context.startActivity(intent);
            this.notifyItemChanged(position);
        });
    }

    public void deleteVideo(String videoId, int position) {
        videoViewModel.getVideo().observe(owner, data -> {
            if (data != null && data.get_id() != null) {
                this.videos.remove(position);
                this.notifyItemRemoved(position);
            }
        });
        videoViewModel.deleteVideo(videoId);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public PopupMenu createOptionsMenu(View view, String videoId, int position) {
        PopupMenu popupMenu = new PopupMenu(this.context, view);
        popupMenu.getMenuInflater().inflate(R.menu.comment_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.editCommentOption) {
                Intent editVideoIntent = new Intent(context, VideoEdit.class);
                Video video = videos.get(position);
                editVideoIntent.putExtra("videoId", video.get_id());
                editVideoIntent.putExtra("position", position);
                this.editVideoLauncher.launch(editVideoIntent);
            } else if (menuItem.getItemId() == R.id.deleteCommentOption) {
                deleteVideo(videoId, position);
            }
            return true;
        });
        return popupMenu;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoDuration;
        TextView videoName;
        TextView videoDate;
        TextView viewsNum;
        TextView likesNum;
        TextView commentNum;

        ImageView videoOptions;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoDuration = itemView.findViewById(R.id.videoDuration);
            videoName = itemView.findViewById(R.id.videoName);
            videoDate = itemView.findViewById(R.id.videoDate);
            viewsNum = itemView.findViewById(R.id.viewsNum);
            likesNum = itemView.findViewById(R.id.likesNum);
            commentNum = itemView.findViewById(R.id.commentNum);
            videoOptions = itemView.findViewById(R.id.videoOptions);
        }
    }

}
