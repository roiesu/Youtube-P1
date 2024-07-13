package com.example.android_client.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_client.R;
import com.example.android_client.adapters.VideoAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;
import com.example.android_client.entities.Video;
import com.example.android_client.view_models.UserViewModel;

import java.io.IOException;
import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

public class MainPage extends AppCompatActivity {

    boolean nightMode;
    private SwitchCompat switchMode;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView videoList;
    private ArrayList<Video> videos;
    private TextView welcomeMessage;
    private SearchView searchInput;
    private ImageView displayImage;
    private CardView imageContainer;
    private UserViewModel userDetails;

    public void initItems(){
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
    public void initVideos(){
        searchInput = findViewById(R.id.searchBar);
        videoList = findViewById(R.id.recyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));

//        getVideos("");
        VideoAdapter adapter = new VideoAdapter(this, videos);
        videoList.setAdapter(adapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(videoList.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
            videoList.addItemDecoration(dividerItemDecoration);
        }
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initItems();
//        initVideos();
        userDetails = new UserViewModel(DataManager.getCurrentUsername());
//        DataManager.initializeData(this);


    }
    @Override
    protected void onStart() {
        super.onStart();
        setWelcomeMessage();
    }

    private void setWelcomeMessage() {
        userDetails.getUser().observe(this,user->{
            if(user!=null){
                Log.w("USERRRR",user.toString());
                welcomeMessage.setText("Welcome, " + user.getName() + "!");
                imageContainer.setVisibility(View.VISIBLE);
                Glide.with(this).load(user.getImageFromServer()).into(displayImage);
            }
            else{
                welcomeMessage.setText("Hello Guest! Please sign in");
                imageContainer.setVisibility(View.GONE);
            }
        });
//        User currentUser = DataManager.getCurrentUser();
//        if (currentUser != null) {
//            welcomeMessage.setText("Welcome, " + currentUser.getName() + "!");
//            displayImage.setImageURI(currentUser.getImageUri());
//            imageContainer.setVisibility(View.VISIBLE);
//        } else {
//            welcomeMessage.setText("Hello Guest! Please sign in");
//            imageContainer.setVisibility(View.GONE);
//        }
    }

    private void getVideos(String query) {
        ArrayList<Video> filtered = DataManager.filterVideosBy(DataManager.FILTER_TITLE_KEY, query);
        this.videos = filtered;
    }
}
