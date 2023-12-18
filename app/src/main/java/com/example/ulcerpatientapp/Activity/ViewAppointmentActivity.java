package com.example.ulcerpatientapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ulcerpatientapp.Fragment.AppoitmentFragment;
import com.example.ulcerpatientapp.R;

public class ViewAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new AppoitmentFragment()).commit();
    }
}