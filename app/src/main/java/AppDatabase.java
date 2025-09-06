package com.example.smartwaste;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, PickupRequest.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract PickupRequestDao pickupRequestDao();

    // ✅ Singleton Instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "smartwaste_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()  // ⚠️ For simplicity, okay in small apps. Use background threads in production.
                    .build();
        }
        return instance;
    }
}
