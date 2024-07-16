package com.example.android_client.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.view_models.VideoListViewModel;
import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity {

    private RecyclerView channelVideoList;
    private VideoAdapter adapter;
    private VideoListViewModel videos;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        String userId = getIntent().getStringExtra("USER_ID");
        channelVideoList = findViewById(R.id.channelVideoList);
        channelVideoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, new ArrayList<>());
        channelVideoList.setAdapter(adapter);
        refreshLayout = findViewById(R.id.channelRefreshLayout);
        refreshLayout.setOnRefreshListener(() -> videos.loadVideosForUser(userId));
        videos = new VideoListViewModel();
        videos.getVideos().observe(this, list -> {
            adapter.setVideos(list);
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(channelVideoList.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
            channelVideoList.addItemDecoration(dividerItemDecoration);
        }

        videos.loadVideosForUser(userId);
    }
}
