package com.example.smartwaste;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RequestAdapter.OnRequestClickListener {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private AppDatabase db;
    private Button addRequestBtn, loginBtn, registerBtn, aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI elements
        recyclerView = findViewById(R.id.recyclerView);
        addRequestBtn = findViewById(R.id.addRequestBtn);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        aboutBtn = findViewById(R.id.aboutBtn);

        db = AppDatabase.getInstance(this);

        // Setup RecyclerView and pass DB to adapter for deletion
        List<PickupRequest> requests = db.pickupRequestDao().getAllRequests();
        adapter = new RequestAdapter(requests, this, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Navigation buttons
        loginBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        registerBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
        aboutBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));

        // Add request button
        addRequestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditRequestActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list whenever returning to this activity
        List<PickupRequest> requests = db.pickupRequestDao().getAllRequests();
        adapter.setRequests(requests);
    }

    @Override
    public void onRequestClick(PickupRequest request) {
        // Edit existing request
        Intent intent = new Intent(MainActivity.this, AddEditRequestActivity.class);
        intent.putExtra("requestId", request.getId());
        startActivity(intent);
    }
}
