package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedLocation = data.getStringExtra("selected_location");
            loc.setText(selectedLocation);
        }
    }
}
