package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class LocationPickerActivity extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private TextView selectedLocationTextView;
    private Button confirmButton;
    private Button retrySearchButton;
    private String selectedAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        selectedLocationTextView = findViewById(R.id.tvSelectedLocation);
        confirmButton = findViewById(R.id.btnConfirmLocation);
        retrySearchButton = findViewById(R.id.btnRetrySearch);

        confirmButton.setEnabled(false);

        confirmButton.setOnClickListener(v -> {
            if (selectedAddress != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_location", selectedAddress);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        retrySearchButton.setOnClickListener(v -> launchAutocomplete());

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDnV3MPbiIyb_NJfHCWpKrwgxQd2y2yetU");
        }

        launchAutocomplete();
    }

    private void launchAutocomplete() {
        List<Place.Field> fields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
        );

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN,
                fields
        ).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                selectedAddress = place.getAddress();
                selectedLocationTextView.setText(selectedAddress);
                confirmButton.setEnabled(true);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("LocationPicker", "Autocomplete error: " + status.getStatusMessage());
                Toast.makeText(this, "Search failed, please try manual entry.", Toast.LENGTH_LONG).show();

                // Keep current page, user can input manually
                selectedLocationTextView.setText("Failed to load place. You may input manually.");
                confirmButton.setEnabled(false);
            }
        }
    }
}

