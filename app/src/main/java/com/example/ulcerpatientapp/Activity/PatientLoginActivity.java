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

import com.example.ulcerpatientapp.R;
import com.example.ulcerpatientapp.dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientLoginActivity extends AppCompatActivity {

    private TextView signUpTextView;

    private EditText emailP, passwordP;
    private Button signupBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        signUpTextView = findViewById(R.id.signInText);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        signupBtn = findViewById(R.id.signUpBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent VidAc = new Intent(PatientLoginActivity.this, PatientRegisterActivity.class);
                startActivity(VidAc);
            }
        });
    }

    private void loginUserAccount() {
        String email, password;
        email = emailP.getText().toString();
        password = passwordP.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        else {
            progressDialog.setTitle("Logging In");
            progressDialog.setMessage("Please wait, we are checking for your Account...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = task.getResult().getUser().getUid();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                firebaseDatabase.getReference().child("User").child(uid).child("usertype")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Integer usertype = snapshot.getValue(Integer.class);
                                                if (usertype != null && usertype == 0) {
                                                    Intent intent = new Intent(PatientLoginActivity.this, dashboard.class);
                                                    startActivity(intent);
                                                    finish(); // Optional: Prevents going back to login on pressing back button
                                                } else {
                                                    Toast.makeText(PatientLoginActivity.this, "Don't Have An Account Here or Try Again", Toast.LENGTH_SHORT).show();
                                                }
                                                progressDialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                progressDialog.dismiss();
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }

    private void initializeUI() {
        emailP = findViewById(R.id.editEmailSignUp);
        passwordP = findViewById(R.id.editPassSignUp);
        signupBtn = findViewById(R.id.signUpBtn);
        progressDialog =new ProgressDialog(this);
    }
}
