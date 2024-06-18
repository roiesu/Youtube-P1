package com.example.android_client.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.example.android_client.R;
import com.example.android_client.activities.MainPage;

public class LogoFragment extends Fragment {

    public LogoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logo, container, false);

        ImageView logoImageView = view.findViewById(R.id.logoImageView);
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                if (!(context instanceof MainPage)) {
                    startActivity(new Intent(getActivity(), MainPage.class));
                }
            }
        });

        return view;
    }
}
