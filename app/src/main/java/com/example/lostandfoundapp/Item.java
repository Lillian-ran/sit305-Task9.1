package com.example.lostandfoundapp;

import com.google.android.gms.maps.model.LatLng;

public class Item {
    private int id;
    private String type;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location; // raw location text
    private LatLng latLng;   // for map locate

        public Item(int id, String type, String name, String phone, String description, String date, String location, LatLng latLng) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.phone = phone;
            this.description = description;
            this.date = date;
            this.location = location;
            this.latLng = latLng;
        }

        public int getId() { return id; }
        public String getType() { return type; }
        public String getName() { return name; }
        public String getPhone() { return phone; }
        public String getDescription() { return description; }
        public String getDate() { return date; }
        public String getLocation() { return location; }
        public LatLng getLatLng() { return latLng; }
    }
