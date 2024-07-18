package com.example.android_client.activities;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.adapters.InputValidationAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.InputValidation;
import com.example.android_client.entities.User;
import com.example.android_client.view_models.UserViewModel;

import java.io.IOException;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    private ArrayList<InputValidation> inputs;

    private RecyclerView inputList;
    private Button submit;
    private Button uploadImageButton;

    private ImageView previewImage;
    private Uri imageUri;

    private String imageData;
    private Button change;
    private ActivityResultLauncher<Intent> galleryResultLauncher;
    private ActivityResultLauncher<Intent> cameraResultLauncher;
    private UserViewModel userViewModel;

    private final int REQUEST_CAMERA_CODE = 200;

    private void initInputs() {
        inputs = new ArrayList<>();
        inputList = findViewById(R.id.recyclerView);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        String[] inputNames = getResources().getStringArray(R.array.inputNames);
        String[] inputReqs = getResources().getStringArray(R.array.inputRequierments);
        for (int i = 0; i < 4; i++) {
            inputs.add(new InputValidation(inputNames[i], inputReqs[i]));
        }
        InputValidationAdapter adapter = new InputValidationAdapter(this, inputs, true);
        inputList.setAdapter(adapter);
    }

    private void initMediaPickers() {
        uploadImageButton = findViewById(R.id.imageInput);
        uploadImageButton.setOnClickListener(view -> {
            pickImage();
        });
        galleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Bitmap bitmap;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            imageData = Utilities.bitmapToBase64(bitmap, Utilities.IMAGE_TYPE);
                        } catch (IOException e) {
                            Log.w("IOException", e);
                        }
                        previewImage.setImageURI(imageUri);
                    }
                });
        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");
                        imageData = Utilities.bitmapToBase64(bitmap, Utilities.IMAGE_TYPE);
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image", null));
                        previewImage.setImageURI(imageUri);
                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new UserViewModel();
        userViewModel.getUserData().observe(this,user->{
            if(user==null){
                startActivity(new Intent(this,MainPage.class));
            }
        });
        setContentView(R.layout.sign_up);
        initInputs();
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            register();
        });
        previewImage = findViewById(R.id.imagePreview);
        change = findViewById(R.id.signin);
        change.setOnClickListener(l -> {
            Intent intent = new Intent(l.getContext(), SignIn.class);
            startActivity(intent);
        });

        initMediaPickers();
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

    public void useCamera() {
        cameraResultLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
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


    private void register() {
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        if (!inputs.get(1).getInputText().equals(inputs.get(2).getInputText())) {
            toast.setText(inputs.get(2).getReqs());
            toast.show();
            return;
        } else if (imageUri == null || imageData == null) {
            toast.setText("No image chosen");
            toast.show();
            return;
        }
        User newUser = new User(null, inputs.get(0).getInputText(), inputs.get(1).getInputText(), inputs.get(3).getInputText(), imageData);
        userViewModel.getUserData().setValue(newUser);
        userViewModel.create();
    }


    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() != null) {
            finish();
        }
    }


}
