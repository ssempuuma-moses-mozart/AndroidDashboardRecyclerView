package com.example.ulcerpatientapp.Activity;

// Your imports
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ulcerpatientapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText emailP, passwordP;
    private Button signupBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        initializeUI();
        signupBtn.findViewById(R.id.signUpBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount() {

        String email, password;
        email = emailP.getText().toString();
        password = passwordP.getText().toString();
        // Check if email and password are provided
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter both email and password!", Toast.LENGTH_LONG).show();
            return;
        }

        // Show progress dialog
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait, we are checking your account...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        // Firebase sign-in
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            firebaseDatabase.getReference().child("Doctor").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userType = snapshot.getValue(String.class);
                                    if ("Doctor".equals(userType)) { // Compare using equals
                                        Intent intent = new Intent(DoctorLoginActivity.this, DoctorHomeActivity.class);
                                        startActivity(intent);
                                        finish(); // Close this activity
                                    } else {
                                        Toast.makeText(DoctorLoginActivity.this, "You don't have Doctor privileges.", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut(); // Sign out if not a doctor
                                    }
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressDialog.dismiss();
                                    // Handle onCancelled event if needed
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void initializeUI() {
        // Initialize views and progressDialog
        emailP = findViewById(R.id.editEmailSignUp);
        passwordP = findViewById(R.id.editPassSignUp);

        signupBtn = findViewById(R.id.signUpBtn);

        progressDialog =new ProgressDialog(this);
    }
}
