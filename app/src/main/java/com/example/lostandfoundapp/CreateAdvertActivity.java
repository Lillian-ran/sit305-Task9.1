package com.example.lostandfoundapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {
    DBHelper db;
    EditText loc;
    private static final int LOCATION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        db = new DBHelper(this);

        RadioGroup group = findViewById(R.id.radioGroup);
        EditText name = findViewById(R.id.etName);
        EditText phone = findViewById(R.id.etPhone);
        EditText desc = findViewById(R.id.etDescription);
        EditText date = findViewById(R.id.etDate);
        loc = findViewById(R.id.etLocation);

        loc.setFocusable(false);
        loc.setOnClickListener(v -> {
            Intent intent = new Intent(this, LocationPickerActivity.class);
            startActivityForResult(intent, 123);
        });


        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String type = ((RadioButton) findViewById(group.getCheckedRadioButtonId())).getText().toString();
            db.insertItem(type, name.getText().toString(), phone.getText().toString(),
                    desc.getText().toString(), date.getText().toString(), loc.getText().toString());
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button currentLocBtn = findViewById(R.id.btnCurrentLocation);
        currentLocBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                getCurrentLocationAndSet(loc);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedLocation = data.getStringExtra("selected_location");
            loc.setText(selectedLocation);
        }
    }

    private void getCurrentLocationAndSet(EditText locationField) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (!addresses.isEmpty()) {
                                String addressLine = addresses.get(0).getAddressLine(0);
                                locationField.setText(addressLine);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error getting address from location", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Unable to get location (null)", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
