package com.example.smartwaste;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pickup_requests")
public class PickupRequest {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String address;
    private String details;
    private String date;
    private String status;
    private String wasteType;
    private String phone;

    // Empty constructor (needed by Room)
    public PickupRequest() {
    }

    // Full constructor (used manually in code)
    public PickupRequest(String name, String address, String details,
                         String date, String status, String wasteType, String phone) {
        this.name = name;
        this.address = address;
        this.details = details;
        this.date = date;
        this.status = status;
        this.wasteType = wasteType;
        this.phone = phone;
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getWasteType() {
        return wasteType;
    }
    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
