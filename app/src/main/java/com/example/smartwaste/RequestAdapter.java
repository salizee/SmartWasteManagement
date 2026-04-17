package com.example.smartwaste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwaste.database.PickupRequest;
import com.example.smartwaste.database.PickupRequestDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context context;
    private List<PickupRequest> requests;
    private PickupRequestDao dao;

    public RequestAdapter(Context context, List<PickupRequest> requests, PickupRequestDao dao) {
        this.context = context;
        this.requests = new ArrayList<>(requests);
        this.dao = dao;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        PickupRequest request = requests.get(position);

        holder.tvName.setText(request.getUsername());
        holder.tvAddress.setText(request.getLocation());
        holder.tvDetails.setText(request.getDetails());
        holder.tvPhone.setText(request.getPhoneNumber());
        holder.tvWasteType.setText(request.getWasteType());

        // ================= DATE =================
        String rawDate = request.getDate();
        if (rawDate != null && !rawDate.isEmpty()) {
            try {
                SimpleDateFormat inputFormat =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                Date date = inputFormat.parse(rawDate);

                SimpleDateFormat outputFormat =
                        new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());

                holder.tvDate.setText(outputFormat.format(date));

            } catch (ParseException e) {
                holder.tvDate.setText(rawDate);
            }
        } else {
            holder.tvDate.setText("N/A");
        }

        // ================= STATUS FIX (NEW) =================
        String status = request.getStatus();
        if (status == null) status = "Pending";

        holder.tvStatus.setText(status);

        if (status.equalsIgnoreCase("Accepted")) {
            holder.tvStatus.setTextColor(0xFF2E7D32); // GREEN
        }
        else if (status.equalsIgnoreCase("Rejected")) {
            holder.tvStatus.setTextColor(0xFFC62828); // RED
        }
        else {
            holder.tvStatus.setTextColor(0xFFF9A825); // ORANGE
        }

        // ================= DELETE =================
        holder.btnDelete.setOnClickListener(v -> {
            new Thread(() -> {
                dao.delete(request);
                ((android.app.Activity) context).runOnUiThread(() -> {
                    requests.remove(position);
                    notifyItemRemoved(position);
                });
            }).start();
        });
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

    static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvDetails, tvPhone, tvWasteType, tvDate, tvStatus;
        Button btnDelete;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvWasteType = itemView.findViewById(R.id.tvWasteType);
            tvDate = itemView.findViewById(R.id.tvDate);

            // NEW
            tvStatus = itemView.findViewById(R.id.tvStatus);

            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}