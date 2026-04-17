package com.example.smartwaste;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.User;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnRegister);

        db = AppDatabase.getInstance(this);

        loginButton.setOnClickListener(v -> {

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {

                User user = db.userDao().getUserByUsername(username);

                runOnUiThread(() -> {

                    if (user == null) {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (user.getPassword() == null ||
                            !user.getPassword().equals(password)) {

                        Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    // 🔥 ROLE CHECK ADDED HERE
                    String role = user.getRole();

                    if (role != null && role.equalsIgnoreCase("admin")) {
                        startActivity(new Intent(LoginActivity.this, AdminPanelActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    finish();
                });

            }).start();
        });

        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}