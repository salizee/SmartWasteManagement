package com.example.smartwaste;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private CheckBox isAdminCheckBox;
    private Button registerButton;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        isAdminCheckBox = findViewById(R.id.isAdminCheckBox);
        registerButton = findViewById(R.id.registerButton);

        db = AppDatabase.getInstance(this);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String role = isAdminCheckBox.isChecked() ? "admin" : "user";

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    User existingUser = db.userDao().getUserByUsername(username);
                    if (existingUser != null) {
                        runOnUiThread(() ->
                                Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show()
                        );
                        return;
                    }

                    // Insert new user
                    User newUser = new User(username, password, role);
                    db.userDao().insert(newUser);

                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        // Optionally, redirect to LoginActivity after registration
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();
        });
    }
}