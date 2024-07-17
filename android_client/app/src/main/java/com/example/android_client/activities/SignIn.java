package com.example.android_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.InputValidationAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.InputValidation;
import com.example.android_client.entities.User;
import com.example.android_client.repositories.UserRepository;
import com.example.android_client.view_models.UserViewModel;

import java.util.ArrayList;


public class SignIn extends AppCompatActivity {
    private ArrayList<InputValidation> inputs;
    private RecyclerView inputList;
    private Button submit;
    private Button change;

    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel= new UserViewModel();
        userViewModel.getUserData().observe(this,user->{
            if(user==null){
                startActivity(new Intent(this,MainPage.class));
            }
        });
        setContentView(R.layout.sign_in);
        setInputs();
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(l -> {
            login();
        });
        change = findViewById(R.id.signup);
        change.setOnClickListener(l -> {
            Intent intent = new Intent(l.getContext(), SignUp.class);
            startActivity(intent);
        });
    }

    private void setInputs() {
        inputList = findViewById(R.id.recyclerView);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        inputs = new ArrayList<>();
        String[] inputNames = getResources().getStringArray(R.array.inputNames);
        String[] inputReqs = getResources().getStringArray(R.array.inputRequierments);
        for (int i = 0; i < 2; i++) {
            inputs.add(new InputValidation(inputNames[i], inputReqs[i]));
        }
        InputValidationAdapter adapter = new InputValidationAdapter(this, inputs, false);
        inputList.setAdapter(adapter);

    }

    private void login() {
        User userDetails = new User(null,inputs.get(0).getInputText(),inputs.get(1).getInputText(),null,null);
        userViewModel.getUserData().setValue(userDetails);
        userViewModel.login();
    }

    public void onRestart() {
        super.onRestart();
        if (DataManager.getCurrentUser() != null) {
            finish();
        }
    }
}