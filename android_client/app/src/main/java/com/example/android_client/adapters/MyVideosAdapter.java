package com.example.android_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.WatchingVideo;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class MyVideosAdapter extends RecyclerView.Adapter<MyVideosAdapter.VideoViewHolder> {
    private Context context;
    private List<Video> videos;

    public MyVideosAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }
    public void setVideos(List<Video> videos){
        this.videos=videos;
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
        PopupMenu popupMenu = createOptionsMenu(holder.videoOptions);
        holder.videoOptions.setOnClickListener(l->{
            popupMenu.show();
        });

        holder.videoThumbnail.setOnClickListener(l->{
            Intent intent = new Intent(context, WatchingVideo.class);
            intent.putExtra("videoId", video.get_id());
            intent.putExtra("channel", video.getUploaderId());
            context.startActivity(intent);
            this.notifyItemChanged(position);
        });

//        holder.editButton.setOnClickListener(v -> {
//            Intent intent = new Intent(context, VideoEdit.class);
//            intent.putExtra("VIDEO_ID", video.get_id());
//            context.startActivity(intent);
//        });
//
//        holder.deleteButton.setOnClickListener(v -> {
////            deleteVideo(video.get_id());
//            videos.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, videos.size());
//        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    private void deleteVideo(int videoId) {
        DataManager.deleteVideoById(videoId);
    }

    public PopupMenu createOptionsMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this.context, view);
        popupMenu.getMenuInflater().inflate(R.menu.comment_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.deleteCommentOption) {
                ContextApplication.showToast("First");
            } else if (menuItem.getItemId() == R.id.editCommentOption) {
                ContextApplication.showToast("Second");
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
