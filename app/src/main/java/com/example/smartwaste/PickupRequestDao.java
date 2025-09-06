package com.example.smartwaste;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PickupRequestDao {

    @Insert
    long insert(PickupRequest request);

    @Update
    void update(PickupRequest request);

    @Delete
    void delete(PickupRequest request);

    @Query("SELECT * FROM pickup_requests ORDER BY id DESC")
    List<PickupRequest> getAllRequests();

    @Query("SELECT * FROM pickup_requests WHERE id = :id LIMIT 1")
    PickupRequest getRequestById(int id);
}
