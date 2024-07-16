package com.example.android_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.ChannelActivity;
import com.example.android_client.activities.WatchingVideo;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private List<Video> videos;

    public VideoAdapter(Context context, List<Video> videos) {
        super();
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_link_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.videoTitle.setText(video.getName());
        holder.videoUploader.setText(video.getUploader().getName());
        holder.videoViews.setText(Utilities.numberFormatter(video.getViews()));
        holder.videoDate.setText(Utilities.dateDiff(video.getDate()));
        holder.videoDuration.setText(Utilities.secondsToTime(video.getDuration()));
        Glide.with(context).load(video.getThumbnailFromServer()).into(holder.videoPreview);
        Glide.with(context).load(video.getUploader().getImageFromServer()).into(holder.uploaderImage);

        holder.videoPreview.setOnClickListener(l->{
            Intent intent = new Intent(context, WatchingVideo.class);
            intent.putExtra("videoId",video.get_id());
            context.startActivity(intent);
            this.notifyItemChanged(position);
        });

        // image -> channel
        holder.uploaderImage.setOnClickListener(l->{
            Intent intent = new Intent(context, WatchingVideo.class);
            intent.putExtra("videoId",video.get_id());
            context.startActivity(intent);
            this.notifyItemChanged(position);
        });

        //name -> channel
        holder.videoUploader.setOnClickListener(l -> {
            Intent intent = new Intent(context, ChannelActivity.class);
            intent.putExtra("USER_ID", video.getUploader().get_id());
            context.startActivity(intent);
            this.notifyItemChanged(position);
        });
    }
    public void setVideos(List<Video> newVideos){
        this.videos=newVideos;
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoPreview;
        ImageView uploaderImage;
        TextView videoTitle;
        TextView videoUploader;
        TextView videoViews;
        TextView videoDate;
        TextView videoDuration;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoPreview = itemView.findViewById(R.id.videoPreview);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoUploader = itemView.findViewById(R.id.videoUploader);
            videoViews = itemView.findViewById(R.id.videoViews);
            videoDate = itemView.findViewById(R.id.videoDate);
            videoDuration = itemView.findViewById(R.id.videoDuration);
            uploaderImage = itemView.findViewById(R.id.uploaderImage);
        }
    }
}
