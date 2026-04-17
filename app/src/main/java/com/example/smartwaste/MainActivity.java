package com.example.smartwaste;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.PickupRequest;
import com.example.smartwaste.database.PickupRequestDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private AppDatabase db;
    private Button addRequestBtn, loginBtn, registerBtn, aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addRequestBtn = findViewById(R.id.addRequestBtn);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        aboutBtn = findViewById(R.id.aboutBtn);

        db = AppDatabase.getInstance(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter initialized once
        adapter = new RequestAdapter(this, new ArrayList<>(), db.pickupRequestDao());
        recyclerView.setAdapter(adapter);

        addRequestBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEditRequestActivity.class)));

        loginBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        registerBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        aboutBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AboutActivity.class)));
    }

    private void loadRequests() {
        new Thread(() -> {
            PickupRequestDao dao = db.pickupRequestDao();
            List<PickupRequest> requests = dao.getAllRequests();

            runOnUiThread(() -> {
                adapter.setRequests(requests);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRequests();
    }
}