package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowItemsActivity extends AppCompatActivity {
    DBHelper db;
    ArrayList<String> items;
    ArrayList<Integer> ids;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        db = new DBHelper(this);

        ListView listView = findViewById(R.id.listView);
        items = new ArrayList<>();
        ids = new ArrayList<>();

        Cursor cursor = db.getAllItems();
        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            items.add(cursor.getString(1) + " - " + cursor.getString(3));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("itemId", ids.get(position));
            startActivity(i);
        });
    }
}

