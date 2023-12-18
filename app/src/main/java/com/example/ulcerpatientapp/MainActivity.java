package com.example.ulcerpatientapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ulcerpatientapp.Activity.AskDoctorPatientActivity;
import com.example.ulcerpatientapp.Activity.DoctorHomeActivity;
import com.example.ulcerpatientapp.Activity.DoctorLoginActivity;
import com.example.ulcerpatientapp.Activity.DoctorRegisterActivity;
import com.example.ulcerpatientapp.Activity.DoctorViewActivity;
import com.example.ulcerpatientapp.Activity.PatientRegisterActivity;
import com.example.ulcerpatientapp.Activity.ViewAppointmentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                AskDoctorPatientActivity
                Intent intent=new Intent(MainActivity.this, AskDoctorPatientActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);//Timer you can change time here example-1 sec=1000
    }
}