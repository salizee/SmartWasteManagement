package com.example.smartwaste;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.PickupRequest;
import com.example.smartwaste.database.PickupRequestDao;
import com.example.smartwaste.utils.AdminRequestAdapter;

import java.util.List;

public class AdminPanelActivity extends AppCompatActivity {

    private RecyclerView rvRequests;
    private PickupRequestDao requestDao;
    private AdminRequestAdapter adapter;

    private TextView tvPending, tvAccepted, tvRejected, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        rvRequests = findViewById(R.id.rvRequests);

        tvPending = findViewById(R.id.tvPending);
        tvAccepted = findViewById(R.id.tvAccepted);
        tvRejected = findViewById(R.id.tvRejected);
        tvTotal = findViewById(R.id.tvTotal);

        requestDao = AppDatabase.getInstance(this).pickupRequestDao();

        setupRecyclerView();
        loadData();
    }

    private void setupRecyclerView() {
        adapter = new AdminRequestAdapter(this);
        rvRequests.setLayoutManager(new LinearLayoutManager(this));
        rvRequests.setAdapter(adapter);
    }

    // ================= FIXED LOAD DATA =================
    private void loadData() {

        new Thread(() -> {

            List<PickupRequest> requests = requestDao.getAllRequests();

            int pending = 0;
            int accepted = 0;
            int rejected = 0;

            for (PickupRequest r : requests) {

                String status = r.getStatus();

                if (status == null || status.equalsIgnoreCase("Pending")) {
                    pending++;
                } else if (status.equalsIgnoreCase("Accepted")) {
                    accepted++;
                } else if (status.equalsIgnoreCase("Rejected")) {
                    rejected++;
                }
            }

            int total = requests.size();

            // ✅ FIX: make final variables for lambda
            final int fPending = pending;
            final int fAccepted = accepted;
            final int fRejected = rejected;
            final int fTotal = total;

            runOnUiThread(() -> {

                adapter.setRequests(requests);

                tvPending.setText("Pending\n" + fPending);
                tvAccepted.setText("Accepted\n" + fAccepted);
                tvRejected.setText("Rejected\n" + fRejected);
                tvTotal.setText("Total\n" + fTotal);
            });

        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // refresh dashboard every time
    }
}