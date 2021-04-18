package com.example.olio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    EditText height;
    EditText weight;
    EditText goalWeight;
    Button logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        goalWeight = findViewById(R.id.goalWeight);
        logOut = findViewById(R.id.logout);
    }
}