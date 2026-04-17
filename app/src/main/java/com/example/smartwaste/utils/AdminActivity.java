package com.example.smartwaste;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.PickupRequest;
import com.example.smartwaste.database.PickupRequestDao;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // your original layout

        recyclerView = findViewById(R.id.recyclerViewAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        // Load initial requests
        new Thread(() -> {
            List<PickupRequest> requests = db.pickupRequestDao().getAllRequests();
            runOnUiThread(() -> {
                adapter = new RequestAdapter(this, requests, db.pickupRequestDao());
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            List<PickupRequest> requests = db.pickupRequestDao().getAllRequests();
            runOnUiThread(() -> adapter.setRequests(requests));
        }).start();
    }
}