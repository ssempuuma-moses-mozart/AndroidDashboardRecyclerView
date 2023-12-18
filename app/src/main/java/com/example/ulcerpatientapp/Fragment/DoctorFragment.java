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

import com.example.ulcerpatientapp.Adapter.DoctorAdapter;
import com.example.ulcerpatientapp.ModelClass.DoctorModel;
import com.example.ulcerpatientapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorFragment extends Fragment {

    RecyclerView recyclerView;
    DoctorAdapter adapter;
    ProgressBar progressBar;
    List<DoctorModel> mModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DoctorFragment newInstance(String param1, String param2) {
        DoctorFragment fragment = new DoctorFragment();
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
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        //Assigning the Recyclerview to display notifications
        recyclerView = (RecyclerView) view.findViewById(R.id.DoctorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar= view.findViewById(R.id.progress);
//        search_input=view.findViewById(R.id.home_search_edit_text);
        progressBar.setVisibility(View.VISIBLE);

        mModel = new ArrayList<>();
        adapter = new DoctorAdapter(getContext(), mModel);

        loadDataFromDB();

        return view;
    }

    private void loadDataFromDB() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctor");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    mModel.add(new DoctorModel(

                            ds.child("firstName").getValue(String.class),
                            ds.child("lastName").getValue(String.class),
                            ds.child("userName").getValue(String.class),
                            ds.child("specialization").getValue(String.class),
                            ds.child("phoneNumber").getValue(String.class),
                            ds.child("timeSlot").getValue(String.class),
                            ds.child("email").getValue(String.class),
                            ds.child("password").getValue(String.class),
                            ds.child("usertype").getValue(String.class),
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