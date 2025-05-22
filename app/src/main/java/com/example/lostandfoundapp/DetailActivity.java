package com.example.lostandfoundapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    DBHelper db;
    int itemId;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        db = new DBHelper(this);

        itemId = getIntent().getIntExtra("itemId", -1);
        Cursor c = db.getAllItems();
        while (c.moveToNext()) {
            if (c.getInt(0) == itemId) {
                String details = c.getString(1) + "\n" + c.getString(2) + "\n"
                        + c.getString(3) + "\n" + c.getString(4) + "\n"
                        + c.getString(5) + "\n" + c.getString(6);
                ((TextView)findViewById(R.id.tvDetails)).setText(details);
            }
        }

        findViewById(R.id.btnRemove).setOnClickListener(v -> {
            db.deleteItem(itemId);
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

