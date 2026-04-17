package com.example.smartwaste.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PickupRequestDao {

    @Insert
    long insert(PickupRequest request);

    @Update
    void updateRequest(PickupRequest request);

    @Delete
    void delete(PickupRequest request);

    // ================================
    // EXISTING (UNCHANGED - SAFE)
    // ================================
    @Query("SELECT * FROM pickup_requests ORDER BY id DESC")
    List<PickupRequest> getAllRequests();

    @Query("SELECT * FROM pickup_requests WHERE id = :id LIMIT 1")
    PickupRequest getRequestById(int id);

    // ================================
    // NEW: LIVE DATA SUPPORT (ADDED)
    // ================================
    @Query("SELECT * FROM pickup_requests ORDER BY id DESC")
    LiveData<List<PickupRequest>> getAllRequestsLive();

    @Query("SELECT * FROM pickup_requests WHERE username = :username ORDER BY id DESC")
    LiveData<List<PickupRequest>> getRequestsByUserLive(String username);
}