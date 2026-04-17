package com.example.smartwaste.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwaste.R;
import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.PickupRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminRequestAdapter extends RecyclerView.Adapter<AdminRequestAdapter.AdminRequestViewHolder> {

    private Context context;
    private List<PickupRequest> requests;

    public AdminRequestAdapter(Context context) {
        this.context = context;
        this.requests = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdminRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.request_admin_item, parent, false);
        return new AdminRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRequestViewHolder holder, int position) {

        PickupRequest request = requests.get(position);

        holder.tvName.setText(request.getUsername());
        holder.tvEmail.setText(request.getEmail());
        holder.tvAddress.setText(request.getLocation());
        holder.tvDetails.setText(request.getDetails());
        holder.tvPhone.setText(request.getPhoneNumber());
        holder.tvWasteType.setText(request.getWasteType());

        // DATE
        String rawDate = request.getDate();
        String formattedDate = rawDate;

        try {
            SimpleDateFormat input =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            Date date = input.parse(rawDate);

            SimpleDateFormat output =
                    new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());

            formattedDate = output.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tvDateTime.setText(formattedDate);

        // ================= STATUS =================
        String status = request.getStatus();
        if (status == null) status = "Pending";

        holder.tvStatus.setText(status);

        // 🔥 IMPORTANT: RESET UI FIRST (FIXES BOTH BUGS)
        holder.layoutButtons.setVisibility(View.VISIBLE);
        holder.tvStatus.setBackgroundColor(0xFFFFA000); // ORANGE default

        // APPLY CORRECT STATE
        if (status.equalsIgnoreCase("Accepted")) {
            holder.tvStatus.setBackgroundColor(0xFF4CAF50); // GREEN
            holder.layoutButtons.setVisibility(View.GONE);
        }

        if (status.equalsIgnoreCase("Rejected")) {
            holder.tvStatus.setBackgroundColor(0xFFF44336); // RED
            holder.layoutButtons.setVisibility(View.GONE);
        }

        // ================= ACCEPT =================
        holder.btnAccept.setOnClickListener(v -> {

            request.setStatus("Accepted");

            new Thread(() ->
                    AppDatabase.getInstance(context)
                            .pickupRequestDao()
                            .updateRequest(request)
            ).start();

            sendSmsSafely(request.getPhoneNumber(),
                    "SmartWaste: Your request has been ACCEPTED.");

            notifyItemChanged(position);
        });

        // ================= REJECT =================
        holder.btnReject.setOnClickListener(v -> {

            request.setStatus("Rejected");

            new Thread(() ->
                    AppDatabase.getInstance(context)
                            .pickupRequestDao()
                            .updateRequest(request)
            ).start();

            sendSmsSafely(request.getPhoneNumber(),
                    "SmartWaste: Your request has been REJECTED.");

            notifyItemChanged(position);
        });
    }

    // ================= SMS =================
    private void sendSmsSafely(String phone, String message) {

        Activity activity = (Activity) context;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.SEND_SMS},
                    100);

            Toast.makeText(context,
                    "Enable SMS permission",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager.getDefault()
                    .sendTextMessage(phone, null, message, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void setRequests(List<PickupRequest> newRequests) {
        requests.clear();
        requests.addAll(newRequests);
        notifyDataSetChanged();
    }

    static class AdminRequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvAddress, tvDetails, tvPhone,
                tvWasteType, tvDateTime, tvStatus;

        Button btnAccept, btnReject;
        View layoutButtons;

        public AdminRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvWasteType = itemView.findViewById(R.id.tvWasteType);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);

            layoutButtons = itemView.findViewById(R.id.layoutButtons);
        }
    }
}