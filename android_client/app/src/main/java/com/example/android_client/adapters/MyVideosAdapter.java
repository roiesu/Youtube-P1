package com.example.android_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;

import java.util.ArrayList;
import java.util.List;

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
        holder.viewsNum.setText(""+video.getViews());
        holder.likesNum.setText(""+video.getLikesCount());
        holder.commentNum.setText(""+video.getCommentsCount());
        holder.videoDuration.setText(Utilities.secondsToTime(video.getDuration()));
        Glide.with(context).load(video.getThumbnailFromServer()).into(holder.videoThumbnail);


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

    public void resetVideos(ArrayList<Video> newVideos) {
        this.videos = newVideos;
        notifyDataSetChanged();
    }

    private void deleteVideo(int videoId) {
        DataManager.deleteVideoById(videoId);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoDuration;
        TextView videoName;
        TextView videoDate;
        TextView viewsNum;
        TextView likesNum;
        TextView commentNum;
//        Button editButton;
//        Button deleteButton;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoDuration = itemView.findViewById(R.id.videoDuration);
            videoName = itemView.findViewById(R.id.videoName);
            videoDate = itemView.findViewById(R.id.videoDate);
            viewsNum = itemView.findViewById(R.id.viewsNum);
            likesNum = itemView.findViewById(R.id.likesNum);
            commentNum = itemView.findViewById(R.id.commentNum);

//            editButton = itemView.findViewById(R.id.editButton);
//            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
