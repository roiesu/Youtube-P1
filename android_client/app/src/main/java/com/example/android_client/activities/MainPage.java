package com.example.android_client.activities;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.VideoPreview;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;


public class MainPage extends AppCompatActivity {

    private RecyclerView videoList;
    private ArrayList<VideoPreview> videos;
    private TextView welcomeMessage;
    private SearchView searchInput;
    private Switch changThemeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        changThemeSwitch = findViewById(R.id.darkModeSwitch);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        getVideos("");
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);
        searchInput = findViewById(R.id.searchBar);
        searchInput.setOnSearchClickListener(l->{
            Log.w("CLICK","CLICK");
        });
        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getVideos(s);
                adapter.setVideos(videos);
                adapter.notifyDataSetChanged();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals("")){
                    getVideos(s);
                    adapter.setVideos(videos);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videoList.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
            videoList.addItemDecoration(dividerItemDecoration);
        }

        setWelcomeMessage();
    }
    private void setWelcomeMessage() {
        if (DataManager.getCurrentUser() != null) {
            welcomeMessage.setText("Welcome, " + DataManager.getCurrentUser().getName() + "!");
        } else {
            welcomeMessage.setText("Hello Guest! Please sign in");
        }
    }

    private void getVideos(String query) {
        ArrayList<Video> filtered = DataManager.filterVideosBy(DataManager.FILTER_TITLE_KEY, query);
        ArrayList<VideoPreview> previewedVideos = new ArrayList<>();
        for (Video video : filtered) {
            previewedVideos.add(video.toPreview(this));
        }
        this.videos=previewedVideos;
    }
}
