package com.example.android_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_client.R;
import com.example.android_client.entities.Video;

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
        
    }
}
