package com.example.ulcerpatientapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulcerpatientapp.ModelClass.AppointmentModel;
import com.example.ulcerpatientapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayHomeRecyclerViewAdapter extends RecyclerView.Adapter<DisplayHomeRecyclerViewAdapter.AppointmentViewHolder> {

    private List<AppointmentModel> vData;
    private List<AppointmentModel> vDataFiltered;

    public DisplayHomeRecyclerViewAdapter(List<AppointmentModel> vData) {
        this.vData = vData;
        this.vDataFiltered = vData;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.display_home_recyclerview_data, parent, false);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppointmentViewHolder holder, final int position) {
        final AppointmentModel model = vDataFiltered.get(position);

        holder.nameTxt.setText(model.getDoctorName());
        holder.dateTxt.setText(model.getAppointmentDate());
        holder.timeTxt.setText(model.getAppointmentTime());
        holder.senderNameTxt.setText(model.getSenderName());
        holder.senderPhoneNumberTxt.setText(model.getSenderPhone());
        holder.appointmentStatusTxt.setText(model.getAppointmentStatus());

        // Handle button clicks based on appointment status
        // ... (your button click logic)

    }

    @Override
    public int getItemCount() {
        return vDataFiltered.size();
    }

//    @Override
//    public Filter getFilter() {
//        return myFilterData;
//    }

//    private Filter myFilterData = new Filter() {
//        // Your filter logic
//        // ...
//    };

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, dateTxt, timeTxt, senderNameTxt, senderPhoneNumberTxt, appointmentStatusTxt;
        Button approveButton, cancelButton;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.textViewdocTitle);
            dateTxt = itemView.findViewById(R.id.textViewDateDisplay);
            timeTxt = itemView.findViewById(R.id.textViewTimeDisplay);
            senderNameTxt = itemView.findViewById(R.id.senderName);
            senderPhoneNumberTxt = itemView.findViewById(R.id.senderPhoneNumber);
            appointmentStatusTxt = itemView.findViewById(R.id.appointmentStatus);
            approveButton = itemView.findViewById(R.id.btnApprove);
            cancelButton = itemView.findViewById(R.id.btnCancel);
        }
    }
}
