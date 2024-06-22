package com.example.android_client.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;

import com.example.android_client.R;
import com.example.android_client.activities.MainPage;
import com.example.android_client.activities.MyVideosPage;
import com.example.android_client.activities.SignIn;
import com.example.android_client.activities.SignUp;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;

public class NavBarFragment extends Fragment {
    private ImageButton btnHome;
    private ImageButton btnMyVideos;
    private ImageButton btnLogout;
    private ImageButton btnSignIn;
    private ImageButton btnSignUp;

    public NavBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_nav_bar, container, false);

        btnHome = view.findViewById(R.id.btnHome);
        btnMyVideos = view.findViewById(R.id.btnMyVideos);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        TooltipCompat.setTooltipText(btnHome,"Home page");
        TooltipCompat.setTooltipText(btnMyVideos,"My videos");
        TooltipCompat.setTooltipText(btnLogout,"Logout");
        TooltipCompat.setTooltipText(btnSignIn,"Sign in");
        TooltipCompat.setTooltipText(btnSignUp,"Sign up");


        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainPage.class));
        });
        btnMyVideos.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyVideosPage.class));
        });
        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignIn.class));
        });
        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignUp.class));
        });
        btnLogout.setOnClickListener(v -> {
            DataManager.setCurrentUser(null);
            startActivity(new Intent(getActivity(), SignIn.class));
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DataManager.getCurrentUser() == null) {
            btnMyVideos.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.VISIBLE);
            btnSignIn.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.GONE);
            btnMyVideos.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        }

    }
}
