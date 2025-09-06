package com.example.smartwaste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<PickupRequest> requests;
    private OnRequestClickListener listener;
    private AppDatabase db;

    // Constructor
    public RequestAdapter(List<PickupRequest> requests, OnRequestClickListener listener, AppDatabase db) {
        this.requests = requests;
        this.listener = listener;
        this.db = db; // Database instance needed for deletion
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        PickupRequest request = requests.get(position);

        // Bind data
        holder.tvName.setText(request.getName());
        holder.tvAddress.setText(request.getAddress());
        holder.tvDetails.setText(request.getDetails());
        holder.tvDate.setText(request.getDate());
        holder.tvPhone.setText(request.getPhone());
        holder.tvWasteType.setText(request.getWasteType());

        // Item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRequestClick(request);
            }
        });

        // Delete button click
        holder.btnDelete.setOnClickListener(v -> {
            db.pickupRequestDao().delete(request); // Delete from DB
            requests.remove(position);             // Remove from list
            notifyItemRemoved(position);           // Update RecyclerView
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    // Refresh list dynamically
    public void setRequests(List<PickupRequest> newRequests) {
        this.requests = newRequests;
        notifyDataSetChanged();
    }

    // ViewHolder
    static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvDetails, tvDate, tvPhone, tvWasteType;
        Button btnDelete;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvWasteType = itemView.findViewById(R.id.tvWasteType);

            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Click listener interface
    public interface OnRequestClickListener {
        void onRequestClick(PickupRequest request);
    }
}
