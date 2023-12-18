package com.example.ulcerpatientapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ulcerpatientapp.Fragment.DoctorFragment;
import com.example.ulcerpatientapp.Fragment.DoctorViewDoctorFragment;
import com.example.ulcerpatientapp.R;

public class DoctorViewDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_doctor);

        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new DoctorViewDoctorFragment()).commit();
    }
}