package com.example.lostandfoundapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        db = new DBHelper(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        List<Item> items = db.getItemList(this);
        for (Item item : items) {
            if (item.getLatLng() != null) {
                mMap.addMarker(new MarkerOptions()
                        .position(item.getLatLng())
                        .title(item.getType() + ": " + item.getName()));
            }
        }

        if (!items.isEmpty() && items.get(0).getLatLng() != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(items.get(0).getLatLng(), 10));
        }
    }
}
