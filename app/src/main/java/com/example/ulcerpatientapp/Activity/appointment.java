package com.example.ulcerpatientapp.Activity;

// ... (imports remain unchanged) ...
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ulcerpatientapp.ModelClass.AppointmentModel;
import com.example.ulcerpatientapp.ModelClass.DoctorModel;
import com.example.ulcerpatientapp.R;
import com.example.ulcerpatientapp.dashboard;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;


public class appointment extends AppCompatActivity {

    private static final int REQUEST_SEND_SMS = 1; // Request code for sending SMS permission
    Calendar myCalendar;

    private TextView time;
    private int d = 0,m=0,y=0,min=0,h=0;
    DatePickerDialog DateSelector;

    TextView Displaydate;
    String DateD;
    Button DatePickbtn;
    Button BookAP;
    Button TimeSlotBtn;
    String username, specialization, phonenumber;

    EditText sendernameedit, senderphoneedit;

    String senderusernamefirebase;

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;

    GoogleSignInClient vGoogleSignInClient;
    GoogleSignInClient mGoogleSignInClient;
    String Database_Path = "Appointment";
    Button sendAppointmentBtn;
    TextView doctorname, appointmentdate, appointmenttime;
    //    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    private String selectedDate = "";
    private String selectedTime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("appointment");
        mAuth = FirebaseAuth.getInstance();

        doctorname = (TextView) findViewById(R.id.textViewdocTitle);


        time = findViewById(R.id.textViewTimeDisplay);
        Displaydate = findViewById(R.id.textViewDateDisplay);

        sendernameedit = findViewById(R.id.editSenderName);
        senderphoneedit = findViewById(R.id.editSenderPhoneNumber);

        Intent getlogindata = getIntent();
        username = getlogindata.getStringExtra("username");
        specialization = getlogindata.getStringExtra("specialization");
        phonenumber = getlogindata.getStringExtra("phonenumber");


        TextView PPName = findViewById(R.id.textViewdocTitle);
        TextView PPSpecial = findViewById(R.id.textViewdocspecialization);

        PPName.setText(username);
        PPSpecial.setText(specialization);
//        Toast.makeText(appointment.this, "Phone Number: " + phonenumber, Toast.LENGTH_SHORT).show();


//Geting user

//        Intent intent = new Intent(appointment.this, dashboard.class);
//        intent.putExtra("username", username);
//        intent.putExtra("specialization", specialization);
//        intent.putExtra("phonenumber", phonenumber);
//        startActivity(intent);

        DatePickbtn = findViewById(R.id.buttonPickDate);
        DatePickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                int Year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);

                DateSelector = new DatePickerDialog(appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mMonth, int mDay) {
                        Displaydate.setText(mDay + "/" + mMonth + "/" + myear);
                        DateD = Displaydate.getText().toString();
                    }
                }, day, month, Year);
                DateSelector.getDatePicker().setMinDate(System.currentTimeMillis());

                DateSelector.show();
                DatePickbtn.setText("Choose Another date");
            }
        });

        TimeSlotBtn = findViewById(R.id.buttonTimeSlot);
        TimeSlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookAP.setVisibility(View.VISIBLE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay == 0) {
                            time.setText("12" + ":" + minute + " AM");
                        } else if (hourOfDay < 12) {
                            time.setText(hourOfDay + ":" + minute + " AM");
                        } else if (hourOfDay == 12) {
                            time.setText(hourOfDay + ":" + minute + " PM");
                        } else {
                            time.setText((hourOfDay - 12) + ":" + minute + " PM");
                        }
                    }
                }, h, min, false);
                timePickerDialog.show();


            }
        });

        BookAP = findViewById(R.id.buttonBookA);
        BookAP.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                submitappointment();

                // Fetching username from Firebase
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(currentUser.getUid());
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String fetchedUsername = dataSnapshot.child("userName").getValue(String.class);
                                if (fetchedUsername != null) {
                                    senderusernamefirebase = fetchedUsername;
                                    // Update the TextView displaying the username
//                                    PPName.setText(username);
                                    Toast.makeText(appointment.this, "Hello " + senderusernamefirebase, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }




            }

        });


    }

    private void submitappointment() {

        String doname = doctorname.getText().toString();
        String appointmentdt = Displaydate.getText().toString();
        String appointmentd = time.getText().toString();
        String snder = sendernameedit.getText().toString();
        String phn = senderphoneedit.getText().toString();
        String status ="Pending";
        String key = databaseReference.push().getKey(); // Generate a unique key

        // Get user input for date and time
        String selectedDate = Displaydate.getText().toString().trim();
        String selectedTime = time.getText().toString().trim();



        AppointmentModel appointmentModel  = new AppointmentModel(doname, appointmentdt, appointmentd, snder, phn, status, key);
        databaseReference.push().setValue(appointmentModel);

        Toast.makeText(appointment.this," Appointment sent Successfully",Toast.LENGTH_SHORT).show();

        // Display user-selected date and time in toast message
//        String toastMessage = "Appointment sent for Date: " + selectedDate + "\nTime: " + selectedTime;
//        Toast.makeText(appointment.this, toastMessage, Toast.LENGTH_SHORT).show();


        doctorname.setText("");
        Displaydate.setText("");
        time.setText("");
        sendernameedit.setText("");
        senderphoneedit.setText("");


        // Check if the SEND_SMS permission is granted
        if (ContextCompat.checkSelfPermission(appointment.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(appointment.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS);
        } else {
            // Permission has already been granted, proceed to send SMS
            sendSMS();
        }

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

        // Replace with the recipient's phone number
        String phoneNumber = phonenumber;

//        String message = "Hello! " + username + "you have recieved an appointment request on" + Displaydate + "at" + time + "from" + sendernameedit;
        String message = "Hello! " + username + " you have just received an appointment request right now, for more info visit the Application. ";
//        String message = "Hello! " + username + ", you have an appointment on " + selectedDate + " at " + selectedTime + ". For more information, visit the application.";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            // SMS sent successfully, you might want to show a success message here
//            Toast.makeText(appointment.this," Appointment Message is sent Successfully to: " + username,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Failed to send SMS, handle the error (e.g., show an error message)
//            Toast.makeText(appointment.this,username + " Didn't receive the application message, Load SMS or Airtime and try again.",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
