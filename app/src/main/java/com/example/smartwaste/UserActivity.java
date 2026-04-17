package com.example.smartwaste;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView welcomeText = findViewById(R.id.welcomeText);

        String username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeText.setText("Welcome, " + username + "!");
        }
    }
}