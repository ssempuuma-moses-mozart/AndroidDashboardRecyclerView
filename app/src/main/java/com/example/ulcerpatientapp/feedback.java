package com.example.ulcerpatientapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Find the button by its ID
        Button btnSubmit = findViewById(R.id.startButton);

        // Set an OnClickListener for the button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to send feedback
                submitFeedback(v);
            }
        });
    }

    // Method to send feedback via email
    public void submitFeedback(View view) {
        // Get values from the EditText fields
        EditText editTextName = findViewById(R.id.firstname);
        EditText editTextEmail = findViewById(R.id.lastname);
        EditText editTextMessage = findViewById(R.id.password);

        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String message = editTextMessage.getText().toString();

        // Compose the email content
        String emailSubject = "Feedback from " + name;
        String emailBody = "Name: " + name + "\nEmail: " + email + "\n\nFeedback:\n" + message;

        // Create an Intent to send the email
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sophiaakankunda@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

        // Check if there is an app to handle the intent
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        }
    }
}
