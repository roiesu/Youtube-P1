package com.example.android_client.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.android_client.R;
import com.example.android_client.entities.Video;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        super(context, R.layout.video_link_item,videos);

    }
}
