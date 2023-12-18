package com.example.ulcerpatientapp;

// MainActivity.java
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ulcerpatientapp.Fragment.AppoitmentFragment;

public class MainActivity21 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main21);



        // Find the button by its ID
        Button btnShowMap = findViewById(R.id.btnShowMap);

        // Set an OnClickListener for the button
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to open Google Maps
                showMap(v);
            }
        });
    }

    // Method to open Google Maps
    public void showMap(View view) {
        // Use coordinates (latitude and longitude) for a specific location
        double latitude = -0.607160; // Replace with the desired latitude
        double longitude = 30.654503; // Replace with the desired longitude

        // Create a Uri from the coordinates
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Marker)");

        // Create an Intent to open Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package to ensure it opens in Google Maps

        // Check if there is an app to handle the intent
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}

