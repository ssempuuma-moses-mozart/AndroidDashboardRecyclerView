package com.example.ulcerpatientapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.ulcerpatientapp.Adapter.AppointmentAdapter;
import com.example.ulcerpatientapp.Adapter.AppointmentViewDoctorAdapter;
import com.example.ulcerpatientapp.ModelClass.AppointmentModel;
import com.example.ulcerpatientapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ViewAppointmentFragment extends Fragment {

    RecyclerView recyclerView;
    AppointmentViewDoctorAdapter adapter;
    ProgressBar progressBar;
    List<AppointmentModel> mModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAppointmentFragment newInstance(String param1, String param2) {
        ViewAppointmentFragment fragment = new ViewAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_view_appointment, container, false);

        //Assigning the Recyclerview to display notifications
        recyclerView = (RecyclerView) view.findViewById(R.id.ViewAppointmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar= view.findViewById(R.id.progress);
//        search_input=view.findViewById(R.id.home_search_edit_text);
        progressBar.setVisibility(View.VISIBLE);

        mModel = new ArrayList<>();
        adapter = new  AppointmentViewDoctorAdapter(getContext(), mModel);

        loadDataFromDB();

        return view;
    }

    private void loadDataFromDB() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("appointment");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    mModel.add(new AppointmentModel(

                            ds.child("doctorName").getValue(String.class),
                            ds.child("appointmentDate").getValue(String.class),
                            ds.child("appointmentTime").getValue(String.class),
                            ds.child("senderName").getValue(String.class),
                            ds.child("senderPhone").getValue(String.class),
                            ds.child("appointmentStatus").getValue(String.class),
                            ds.getKey()
                    ));

                }
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}