package com.example.ulcerpatientapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ulcerpatientapp.Adapter.AppointmentViewDoctorAdapter;
import com.example.ulcerpatientapp.Adapter.DisplayHomeRecyclerViewAdapter;
import com.example.ulcerpatientapp.ModelClass.AppointmentModel;
import com.example.ulcerpatientapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DisplayHomeRecyclerViewAdapter adapter;
    private List<AppointmentModel> vData;

    FirebaseAuth mAuth;
    DatabaseReference appointmentsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        mAuth = FirebaseAuth.getInstance();

//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        // Initialize your data list and adapter
        vData = new ArrayList<>();
        adapter = new DisplayHomeRecyclerViewAdapter(vData);
        recyclerView.setAdapter(adapter);

        // Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        appointmentsRef = database.getReference("appointment");
        // Fetch data from Firebase and update RecyclerView
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        appointmentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vData.clear(); // Clear the existing list before adding new data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming your AppointmentModel has a constructor that accepts the necessary data
                    AppointmentModel appointment = snapshot.getValue(AppointmentModel.class);
                    if (appointment != null) {
                        vData.add(appointment);
                    }
                }

                adapter.notifyDataSetChanged(); // Notify the adapter after updating the dataList
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Toast.makeText(DoctorHomeActivity.this, "Failed to retrieve data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewDoctors(View view) {
        Intent intent = new Intent(DoctorHomeActivity.this, DoctorViewDoctorActivity.class);
        startActivity(intent);
    }

    public void viewAppointments(View view) {
        Intent intent = new Intent(DoctorHomeActivity.this, AppointmentDoctorViewActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        mAuth.signOut();
        Intent VidAc =new Intent(DoctorHomeActivity.this, DoctorLoginActivity.class);
        startActivity(VidAc);
        Toast.makeText(DoctorHomeActivity.this, "You have Signed Out", Toast.LENGTH_SHORT).show();
    }
}