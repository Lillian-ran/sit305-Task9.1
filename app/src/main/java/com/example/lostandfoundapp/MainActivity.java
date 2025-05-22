package com.example.lostandfoundapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCreate).setOnClickListener(v ->
                startActivity(new Intent(this, CreateAdvertActivity.class)));

        findViewById(R.id.btnShow).setOnClickListener(v ->
                startActivity(new Intent(this, ShowItemsActivity.class)));
        findViewById(R.id.btnMap).setOnClickListener(v ->
                startActivity(new Intent(this, MapActivity.class)));

    }
}
