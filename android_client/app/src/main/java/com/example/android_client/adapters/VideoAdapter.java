package com.example.android_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.WatchingVideo;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.VideoPreview;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
        private List<Video> videos;

        public VideoAdapter(List<Video> videos) {
            this.videos = videos;
        }

        public void updateVideos(List<Video> newVideos) {
            this.videos = newVideos;
        }

        public Video getLastAddedVideo() {
            if (videos != null && !videos.isEmpty()) {
                return videos.get(videos.size() - 1);
            }
            return null;
        }

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
            return new VideoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            Video video = videos.get(position);
            // Bind video data to the view holder
            holder.videoTitle.setText(video.getName());
            holder.videoUploader.setText(video.getUploader());
            holder.videoViews.setText(String.valueOf(video.getViews()));
            holder.videoDate.setText(video.getUploadDate().toString());
            holder.videoDuration.setText(String.valueOf(video.getDuration()));
            // Assuming you have a method to get a thumbnail
            holder.videoPreview.setImageBitmap(video.getThumbnail());
            holder.videoPreview.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), WatchingVideo.class);
                intent.putExtra("videoId", video.getId());
                holder.itemView.getContext().startActivity(intent);
                video.addView();
                this.notifyItemChanged(position);
            });
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }

        public static class VideoViewHolder extends RecyclerView.ViewHolder {
            ImageView videoPreview;
            TextView videoTitle;
            TextView videoUploader;
            TextView videoViews;
            TextView videoDate;
            TextView videoDuration;

            public VideoViewHolder(View itemView) {
                super(itemView);
                videoPreview = itemView.findViewById(R.id.videoPreview);
                videoTitle = itemView.findViewById(R.id.videoTitle);
                videoUploader = itemView.findViewById(R.id.videoUploader);
                videoViews = itemView.findViewById(R.id.videoViews);
                videoDate = itemView.findViewById(R.id.videoDate);
                videoDuration = itemView.findViewById(R.id.videoDuration);
            }
        }
    }
