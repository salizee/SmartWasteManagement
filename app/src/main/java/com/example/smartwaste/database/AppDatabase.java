package com.example.smartwaste.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, PickupRequest.class}, version = 2) // version bumped from 1 to 2
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PickupRequestDao pickupRequestDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "smartwaste.db")
                    .addMigrations(MIGRATION_1_2) // apply migration
                    .build();
        }
        return INSTANCE;
    }

    // Migration from version 1 to version 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Add new columns: email and details with default empty strings
            database.execSQL("ALTER TABLE pickup_requests ADD COLUMN email TEXT DEFAULT '' NOT NULL");
            database.execSQL("ALTER TABLE pickup_requests ADD COLUMN details TEXT DEFAULT '' NOT NULL");
        }
    };
}