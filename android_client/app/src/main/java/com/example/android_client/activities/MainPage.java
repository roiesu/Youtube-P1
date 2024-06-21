package com.example.android_client.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.entities.VideoPreview;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    boolean nightMode;
    private SwitchCompat switchMode;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView videoList;
    private ArrayList<VideoPreview> videos;
    private TextView welcomeMessage;
    private SearchView searchInput;
    private ImageView displayImage;
    private CardView imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        switchMode = findViewById(R.id.darkModeSwitch);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        if (nightMode) {
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            switchMode.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchMode.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", true);
                }
                editor.apply();
            }
        });

        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        welcomeMessage = findViewById(R.id.welcomeMessage);
        displayImage = findViewById(R.id.userImage);
        imageContainer = findViewById(R.id.userImageContainer);
        getVideos("");
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);
        searchInput = findViewById(R.id.searchBar);

        searchInput.setOnSearchClickListener(l -> Log.w("CLICK", "CLICK"));
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
                if (s.equals("")) {
                    getVideos(s);
                    adapter.setVideos(videos);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videoList.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
            videoList.addItemDecoration(dividerItemDecoration);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setWelcomeMessage();
    }

    private void setWelcomeMessage() {
        User currentUser = DataManager.getCurrentUser();
        if (currentUser != null) {
            welcomeMessage.setText("Welcome, " + currentUser.getName() + "!");
            displayImage.setImageURI(currentUser.getImageUri());
        } else {
            welcomeMessage.setText("Hello Guest! Please sign in");
            imageContainer.setVisibility(View.GONE);
        }
    }

    private void getVideos(String query) {
        ArrayList<Video> filtered = DataManager.filterVideosBy(DataManager.FILTER_TITLE_KEY, query);
        ArrayList<VideoPreview> previewedVideos = new ArrayList<>();
        for (Video video : filtered) {
            previewedVideos.add(video.toPreview(this));
        }
        this.videos = previewedVideos;
    }
}
