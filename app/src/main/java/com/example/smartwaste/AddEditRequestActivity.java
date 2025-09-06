package com.example.smartwaste;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditRequestActivity extends AppCompatActivity {

    private EditText etName, etAddress, etRequestDetails, etPhoneNumber;
    private Spinner spinnerWasteType;
    private Button btnSubmit;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_request);

        // ✅ Use singleton DB instance
        db = AppDatabase.getInstance(this);

        // Initialize UI elements
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etRequestDetails = findViewById(R.id.etRequestDetails);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        spinnerWasteType = findViewById(R.id.spinnerWasteType);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Waste types
        String[] wasteTypes = {"Household", "Plastic", "Electronics", "Metal", "Organic"};

        // Adapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, wasteTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWasteType.setAdapter(adapter);

        // Button click
        btnSubmit.setOnClickListener(v -> saveRequest());
    }

    private void saveRequest() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String details = etRequestDetails.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        String wasteType = spinnerWasteType.getSelectedItem().toString();

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String status = "Pending";

        // Validation
        if (name.isEmpty() || address.isEmpty() || details.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.matches("\\d{10,15}")) {
            Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        PickupRequest request = new PickupRequest(
                name, address, details, date, status, wasteType, phone
        );

        // ✅ Try/catch to catch DB errors
        try {
            long id = db.pickupRequestDao().insert(request);
            request.setId((int) id);

            Toast.makeText(this, "Request submitted successfully!", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Error saving request: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
