package com.example.android_client.activities.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.android_client.R;


public class MediaPickerActivity extends AppCompatActivity {
    protected ActivityResultLauncher<Intent> galleryResultLauncher;
    protected ActivityResultLauncher<Intent> cameraResultLauncher;
    protected Uri imageUri;
    protected Button uploadImageButton;
    protected ImageView targetImageView;
    protected final int REQUEST_CAMERA_CODE = 200;

    public void useCamera() {
        cameraResultLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    public void pickImage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Pick image source:");
        dialogBuilder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, button) -> {
            switch (button) {
                case 0: {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
                    } else {
                        useCamera();
                    }
                    break;
                }
                case 1: {
                    galleryResultLauncher.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                    break;
                }
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        targetImageView.setImageURI(imageUri);
                    }
                });
        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image", null));
                        targetImageView.setImageURI(imageUri);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                useCamera();
            } else {
                Toast toast = Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
