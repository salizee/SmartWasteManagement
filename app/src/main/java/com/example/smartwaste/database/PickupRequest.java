package com.example.smartwaste.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pickup_requests")
public class PickupRequest {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String email;
    private String wasteType;
    private String location;
    private String phoneNumber;
    private String details;

    // NEW: request status (Pending, Accepted, Rejected)
    private String status;

    // NEW: date & time of request
    private String date;

    public PickupRequest() {}

    public PickupRequest(String username, String email, String wasteType,
                         String location, String phoneNumber,
                         String details, String status, String date) {
        this.username = username;
        this.email = email;
        this.wasteType = wasteType;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.details = details;
        this.status = status;
        this.date = date;
    }

    // ================= GETTERS =================

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getWasteType() {
        return wasteType;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    // ================= SETTERS =================

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }
}