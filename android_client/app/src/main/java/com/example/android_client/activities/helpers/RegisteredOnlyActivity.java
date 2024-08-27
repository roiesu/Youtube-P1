package com.example.android_client.activities.helpers;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_client.DataManager;
import com.example.android_client.activities.SignIn;

public class RegisteredOnlyActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        if (DataManager.getCurrentUsername() == null) {
            Intent intent = new Intent(this, SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
