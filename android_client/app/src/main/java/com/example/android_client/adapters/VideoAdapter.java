package com.example.android_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_client.R;
import com.example.android_client.entities.Video;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> {
    private LayoutInflater inflater;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        super(context, R.layout.video_link_item,videos);
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Video video = getItem(position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.video_link_item,parent,false);
        }
        VideoView videoPreview = (VideoView) convertView.findViewById(R.id.videoPreview);
        TextView videoTitle = (TextView) convertView.findViewById(R.id.videoTitle);
        TextView videoUploader = (TextView) convertView.findViewById(R.id.videoUploader);
        TextView videoViews = (TextView) convertView.findViewById(R.id.videoViews);
        TextView videoDate = (TextView) convertView.findViewById(R.id.videoDate);

        videoPreview.setVideoPath(video.getSrc());
        videoTitle.setText(video.getName());
        videoUploader.setText(video.getDisplayUploader());
        videoDate.setText(video.getDate_time().toString());
        videoViews.setText(video.getViews());
        return convertView;
    }
}
