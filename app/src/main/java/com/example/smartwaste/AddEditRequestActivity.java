package com.example.smartwaste;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwaste.database.AppDatabase;
import com.example.smartwaste.database.PickupRequest;
import com.example.smartwaste.database.PickupRequestDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AddEditRequestActivity extends AppCompatActivity {

    private EditText etName, etEmail, etAddress, etRequestDetails, etPhoneNumber;
    private Spinner spinnerWasteType;
    private Button btnSubmit;

    private AppDatabase db;
    private PickupRequestDao pickupRequestDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_request);

        db = AppDatabase.getInstance(this);
        pickupRequestDao = db.pickupRequestDao();

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etRequestDetails = findViewById(R.id.etRequestDetails);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        spinnerWasteType = findViewById(R.id.spinnerWasteType);
        btnSubmit = findViewById(R.id.btnSubmit);

        String[] wasteTypes = {"Household", "Plastic", "Electronics", "Metal", "Organic"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, wasteTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWasteType.setAdapter(adapter);

        btnSubmit.setOnClickListener(v -> saveRequest());
    }

    private void saveRequest() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String details = etRequestDetails.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        String wasteType = spinnerWasteType.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty()
                || details.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ FIXED DATE FORMAT (NO MORE 13-DIGIT NUMBER)
        String formattedDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
        ).format(new Date());

        // Create request
        PickupRequest request = new PickupRequest();
        request.setUsername(name);
        request.setEmail(email);
        request.setWasteType(wasteType);
        request.setLocation(address);
        request.setPhoneNumber(phone);
        request.setDetails(details);
        request.setStatus("Pending");
        request.setDate(formattedDate);

        Executors.newSingleThreadExecutor().execute(() -> {
            long id = pickupRequestDao.insert(request);
            request.setId((int) id);

            runOnUiThread(() -> {
                Toast.makeText(this, "Request submitted successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}