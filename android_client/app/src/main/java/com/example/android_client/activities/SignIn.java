package com.example.android_client.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.adapters.InputValidationAdapter;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.InputValidation;
import com.example.android_client.entities.User;

import java.util.ArrayList;


public class SignIn extends AppCompatActivity {
    private ArrayList<InputValidation> inputs;
    private RecyclerView inputList;
    private Button submit;
    private TextView errorView;
    private Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        inputList = findViewById(R.id.recyclerView);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        inputs = new ArrayList<>();
        String [] inputNames =getResources().getStringArray(R.array.inputNames);
        String [] inputRegex =getResources().getStringArray(R.array.inputRegex);
        String [] inputReqs =getResources().getStringArray(R.array.inputRequierments);
        for(int i=0;i<2;i++){
            inputs.add(new InputValidation(inputNames[i],inputRegex[i],inputReqs[i]));
        }
        InputValidationAdapter adapter = new InputValidationAdapter(this,inputs,false);
        inputList.setAdapter(adapter);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(l->{login();});
        errorView = findViewById(R.id.validationError);
        change = findViewById(R.id.signup);
        change.setOnClickListener(l->{
            Intent intent = new Intent(l.getContext(),SignUp.class);
            startActivity(intent);
        });
    }
    private void login(){
        User user= DataManager.findUser(inputs.get(0).getInputText());
        if(user==null||!user.getPassword().equals(inputs.get(1).getInputText())){
            errorView.setText("Uset not found");
            return;
        }
        DataManager.setCurrentUser(user);
        Intent intent = new Intent(this,MainPage.class);
        startActivity(intent);
    }
    public void onRestart(){
        super.onRestart();
        if(DataManager.getCurrentUser()!=null){
            Intent intent= new Intent(this, PageNotFound.class);
            startActivity(intent);
        }
    }
}