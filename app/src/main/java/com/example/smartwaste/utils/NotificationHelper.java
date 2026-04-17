package com.example.smartwaste.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NotificationHelper {

    public static final int SMS_PERMISSION_CODE = 100;

    // Check permission properly
    public static boolean hasSmsPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    // Request permission
    public static void requestSmsPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.SEND_SMS},
                SMS_PERMISSION_CODE);
    }

    // Send SMS safely
    public static void sendSms(Activity activity, String phone, String message) {

        if (phone == null || phone.isEmpty()) return;

        if (!hasSmsPermission(activity)) {
            requestSmsPermission(activity);
            Toast.makeText(activity,
                    "Grant SMS permission to send message",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager.getDefault().sendTextMessage(phone, null, message, null, null);
        } catch (Exception e) {
            Toast.makeText(activity, "SMS failed", Toast.LENGTH_SHORT).show();
        }
    }
}