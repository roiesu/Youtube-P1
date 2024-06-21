package com.example.android_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.activities.MyVideosPage;
import com.example.android_client.activities.VideoEdit;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MyVideosAdapter extends RecyclerView.Adapter<MyVideosAdapter.VideoViewHolder> {
    private Context context;
    private ArrayList<Video> videos;

    public MyVideosAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
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
        holder.videoThumbnail.setImageBitmap(video.getThumbnail());
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoEdit.class);
            intent.putExtra("VIDEO_ID", video.getId());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            deleteVideo(video.getId());
            videos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, videos.size());
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void resetVideos(ArrayList<Video> newVideos) {
        this.videos = newVideos;
        notifyDataSetChanged();
    }

    private void deleteVideo(int videoId) {
        DataManager.deleteVideoById(videoId);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoName;
        Button editButton;
        Button deleteButton;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoName = itemView.findViewById(R.id.videoName);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
