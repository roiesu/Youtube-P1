package com.example.android_client.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.DataManager;

import com.example.android_client.view_models.DatabaseViewModel;
import com.example.android_client.view_models.UserViewModel;
import com.example.android_client.view_models.VideoWithUserListViewModel;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainPage extends AppCompatActivity {

    boolean nightMode;
    private SwitchCompat switchMode;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView videoList;
    private TextView welcomeMessage;
    private SearchView searchInput;
    private ImageView displayImage;
    private CardView imageContainer;
    private UserViewModel userDetails;
    private VideoWithUserListViewModel videos;
    private VideoAdapter adapter;
    private DatabaseViewModel databaseViewModel;


    public void initItems() {
        switchMode = findViewById(R.id.darkModeSwitch);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        if (nightMode) {
            switchMode.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switchMode.setOnClickListener(view -> {
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
        });
        welcomeMessage = findViewById(R.id.welcomeMessage);
        displayImage = findViewById(R.id.userImage);
        imageContainer = findViewById(R.id.userImageContainer);
    }

    public void initVideos() {
        searchInput = findViewById(R.id.searchBar);
        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, new ArrayList<>());
        videoList.setAdapter(adapter);
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            searchInput.setQuery("", false);
            videos.reload();
        });
        videos.getVideos().observe(this, list -> {
            adapter.setVideos(list);
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videoList.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
            videoList.addItemDecoration(dividerItemDecoration);
        }
        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                videos.searchVideo(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        if (savedInstanceState == null) {
            initializeData();
        }
        videos = new VideoWithUserListViewModel(this);
        userDetails = new UserViewModel(this);
        initItems();
        initVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userDetails.getUser(DataManager.getCurrentUsername());
        setWelcomeMessage();
    }

    private void setWelcomeMessage() {
        welcomeMessage.setText("Hello Guest! Please sign in");
        imageContainer.setVisibility(View.GONE);
        userDetails.getUserData().observe(this, user -> {
            if (user != null) {
                DataManager.setCurrentUserId(user.get_id());
                welcomeMessage.setText("Welcome, " + user.getName() + "!");
                imageContainer.setVisibility(View.VISIBLE);
                Glide.with(this).load(user.getImageFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(displayImage);
            } else {
                welcomeMessage.setText("Hello Guest! Please sign in");
                imageContainer.setVisibility(View.GONE);
            }
        });
    }

    private void initializeData() {
        databaseViewModel.init(this);
        databaseViewModel.getInitialized().observe(this, value -> {
            if (value == true) {
                DataManager.setInitialized(true);
                videos.reload();
                userDetails.getUser(DataManager.getCurrentUsername());
            }
        });

    }
}
