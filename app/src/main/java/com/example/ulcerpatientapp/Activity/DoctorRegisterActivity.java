package com.example.ulcerpatientapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulcerpatientapp.ModelClass.DoctorModel;
import com.example.ulcerpatientapp.ModelClass.UserType;
import com.example.ulcerpatientapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorRegisterActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private EditText firstName, lastName, specialization, userName, phoneNumber, timeSlot, emailAddress, passwordP,confirmpasswordP;
    private Button signupBtn;
    // private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        signInTextView = findViewById(R.id.signInText);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        initializeUI();
        signupBtn.findViewById(R.id.signUpBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent VidAc =new Intent(DoctorRegisterActivity.this, DoctorLoginActivity.class);
                startActivity(VidAc);
            }
        });
    }

    private void registerNewUser() {
        //  progressBar.setVisibility(View.VISIBLE);

        String firstname, lastname, specializationz, username, phone, timeslot, email, password, confirmpassword;
        firstname = firstName.getText().toString();
        lastname = lastName.getText().toString();
        specializationz = specialization.getText().toString();
        username = userName.getText().toString();
        phone = phoneNumber.getText().toString();
        timeslot = timeSlot.getText().toString();
        email = emailAddress.getText().toString();
        password = passwordP.getText().toString();
        confirmpassword = confirmpasswordP.getText().toString();

        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(getApplicationContext(), "Please enter first Name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(getApplicationContext(), "Please enter last Name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(specializationz)) {
            Toast.makeText(getApplicationContext(), "Please enter specialization...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please enter username...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Please enter phoneNumber...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(timeslot)) {
            Toast.makeText(getApplicationContext(), "Please enter available time...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(getApplicationContext(), "Please enter and confirm your password!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(confirmpassword)){
            Toast.makeText(DoctorRegisterActivity.this,"Password Don't match",Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            progressDialog.setTitle("Creating Account");
            progressDialog.setMessage("Please wait,we are creating your Account...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String uid = task.getResult().getUser().getUid();

                                DoctorModel user = new DoctorModel(uid, firstname, lastname, specializationz, username, phone, timeslot, email, password, "Doctor");
                                firebaseDatabase.getReference().child("Doctor").child(uid).setValue(user);


                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();

                                Intent intent = new Intent(DoctorRegisterActivity.this, DoctorLoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                // progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }

    private void initializeUI() {
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        specialization = findViewById(R.id.editSpecializationName);
        userName = findViewById(R.id.editUserName);
        phoneNumber = findViewById(R.id.editPhoneNumber);
        timeSlot = findViewById(R.id.editTimeSlot);
        emailAddress = findViewById(R.id.editEmailSignUp);
        passwordP = findViewById(R.id.editPassSignUp);
        signupBtn = findViewById(R.id.signUpBtn);
        confirmpasswordP=findViewById(R.id.editConfirmPassSignUp);
        // progressBar = findViewById(R.id.progressBar);
        progressDialog =new ProgressDialog(this);
    }



}