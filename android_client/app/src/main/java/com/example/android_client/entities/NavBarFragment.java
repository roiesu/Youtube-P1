package com.example.android_client.entities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

import com.example.android_client.R;
import com.example.android_client.activities.MainPage;
import com.example.android_client.activities.MyVideosPage;
import com.example.android_client.activities.SignIn;
import com.example.android_client.activities.SignUp;

public class NavBarFragment extends Fragment {

    public NavBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_nav_bar, container, false);

        ImageButton btnHome = view.findViewById(R.id.btnHome);
        ImageButton btnMyVideos = view.findViewById(R.id.btnMyVideos);
        ImageButton btnLogout = view.findViewById(R.id.btnLogout);
        ImageButton btnSignIn = view.findViewById(R.id.btnSignIn);
        ImageButton btnSignUp = view.findViewById(R.id.btnSignUp);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainPage.class));
            }
        });

        btnMyVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyVideosPage.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignIn.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignIn.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignUp.class));
            }
        });

        return view;
    }
}

