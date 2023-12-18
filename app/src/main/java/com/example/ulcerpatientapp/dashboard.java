package com.example.ulcerpatientapp;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import android.speech.tts.TextToSpeech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ulcerpatientapp.Activity.AlarmActivity;
import com.example.ulcerpatientapp.Activity.PatientLoginActivity;
import com.example.ulcerpatientapp.Activity.ViewAppointmentActivity;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    private static final int REQUEST_SEND_SMS = 1; // Request code for sending SMS permission
//    private TextToSpeech tts;
   Button editButton,profileButton;
   ImageView profile1,back1;

    private View appointment,dietchart,drugdetails,remainder, feedback,help, view1;

    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mAuth = FirebaseAuth.getInstance();

        appointment = findViewById(R.id.textView1);
        dietchart = findViewById(R.id.textView20);
        drugdetails = findViewById(R.id.textView21);
        remainder = findViewById(R.id.textView22);
        feedback = findViewById(R.id.textView23);
        help = findViewById(R.id.textView4);
//        tts = new TextToSpeech(this, this);
        profileButton = findViewById(R.id.todoB);
        profile1 = findViewById(R.id.profileB);
        back1 = findViewById(R.id.backB);
        view1 = findViewById(R.id.viewa);



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent VidAc =new Intent(dashboard.this, PatientLoginActivity.class);
                startActivity(VidAc);
                Toast.makeText(dashboard.this, "You have Signed Out", Toast.LENGTH_SHORT).show();

//                Toast.makeText(dashboard.this, "HELP IMAGE CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if the SEND_SMS permission is granted
                if (ContextCompat.checkSelfPermission(dashboard.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(dashboard.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            REQUEST_SEND_SMS);
                } else {
                    // Permission has already been granted, proceed to send SMS
                    sendSMS();
                }

            }
        });


        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, com.example.ulcerpatientapp.Activity.DoctorViewActivity.class);
                startActivity(intent);
            }
        });

        dietchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, dietmanager.class);
                startActivity(intent);
            }
        });
        drugdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, drugdetails.class);
                startActivity(intent);
            }
        });
        remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, feedback.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, MainActivity21.class);
                startActivity(intent);

            }
        });

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboard.this, ViewAppointmentActivity.class);
                startActivity(intent);
            }
        });


    }

    // Handle the permission request response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with SMS sending
                sendSMS();
            } else {
                // Permission denied, you may want to disable the functionality
                // or show a message indicating why the permission is necessary
            }
        }
    }

    // Method to send SMS
    private void sendSMS() {
        String phoneNumber = "0775218489"; // Replace with the recipient's phone number
        String message = "Hello! This is a test SMS."; // Replace with your message

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            // SMS sent successfully, you might want to show a success message here
        } catch (Exception e) {
            // Failed to send SMS, handle the error (e.g., show an error message)
            e.printStackTrace();
        }

}

}
