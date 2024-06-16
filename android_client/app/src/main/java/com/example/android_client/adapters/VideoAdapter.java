package com.example.android_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_client.R;
import com.example.android_client.entities.Video;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private ArrayList<Video> videos;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
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
        holder.videoPreview.setVideoPath(video.getSrc());
        holder.videoTitle.setText(video.getName());
        holder.videoUploader.setText(video.getDisplayUploader());
        holder.videoViews.setText(String.valueOf(video.getViews()));
        holder.videoDate.setText(video.getDate_time().toString());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoPreview;
        TextView videoTitle;
        TextView videoUploader;
        TextView videoViews;
        TextView videoDate;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoPreview = itemView.findViewById(R.id.videoPreview);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoUploader = itemView.findViewById(R.id.videoUploader);
            videoViews = itemView.findViewById(R.id.videoViews);
            videoDate = itemView.findViewById(R.id.videoDate);
        }
    }
}
