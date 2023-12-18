package com.example.ulcerpatientapp.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulcerpatientapp.Activity.DoctorHomeActivity;
import com.example.ulcerpatientapp.ModelClass.AppointmentModel;
import com.example.ulcerpatientapp.R;
import com.example.ulcerpatientapp.dashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentViewDoctorAdapter extends RecyclerView.Adapter<com.example.ulcerpatientapp.Adapter.AppointmentViewDoctorAdapter.AppointmentViewHolder> implements Filterable {


    private Context context;
    List<AppointmentModel> vData;
    List<AppointmentModel> vDataFiltered;

    public AppointmentViewDoctorAdapter(Context context, List<AppointmentModel> vData) {
        this.context = context;
        this.vData = vData;
        this.vDataFiltered = vData;
    }


    @Override
    public com.example.ulcerpatientapp.Adapter.AppointmentViewDoctorAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.appointment_view_doctor, parent,false);

        return new com.example.ulcerpatientapp.Adapter.AppointmentViewDoctorAdapter.AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final com.example.ulcerpatientapp.Adapter.AppointmentViewDoctorAdapter.AppointmentViewHolder holder, final int position) {
        final AppointmentModel model = vDataFiltered.get(position);
        //Adding data to xml file
        holder.nameTxt.setText(model.getDoctorName());
        holder.dateTxt.setText(model.getAppointmentDate());
        holder.timeTxt.setText(model.getAppointmentTime());
        holder.senderNameTxt.setText(model.getSenderName());
        holder.senderPhoneNumberTxt.setText(model.getSenderPhone());
        holder.appointmentStatusTxt.setText(model.getAppointmentStatus());


        // Check if the status is "Cancelled"
        if (model.getAppointmentStatus().equals("Cancelled")) {
            // Disable the "Approve" button if the status is "Cancelled"
            holder.appointmentStatusTxt.setTextColor(Color.RED);
            holder.approveButton.setEnabled(false);
        } else if (model.getAppointmentStatus().equals("Pending")){
            holder.appointmentStatusTxt.setTextColor(Color.BLUE);
            holder.approveButton.setEnabled(true);
            holder.cancelButton.setEnabled(true);
            holder.approveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Set senderPhone to "Approved"
                    Map<String, Object> map = new HashMap<>();
                    map.put("appointmentStatus", "Approved");
                    FirebaseDatabase.getInstance().getReference().child("appointment")
                            .child(model.key)
                            .updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(holder.appointmentStatusTxt.getContext(), "Status set to Approved", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(holder.appointmentStatusTxt.getContext(), DoctorHomeActivity.class);
                                    holder.appointmentStatusTxt.getContext().startActivity(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(holder.appointmentStatusTxt.getContext(), "Error setting phone to pending", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        } else if (model.getAppointmentStatus().equals("Reschedule")) {
            holder.appointmentStatusTxt.setTextColor(Color.parseColor("#9D0F9B"));
//            holder.approveButton.setEnabled(false);

        } else {
            // Enable the "Approve" button if the status is not "Cancelled"
            holder.approveButton.setEnabled(true);
            holder.appointmentStatusTxt.setTextColor(Color.parseColor("#FF018786"));
           }

        // Check if the status is "Cancelled"
        if (model.getAppointmentStatus().equals("Cancelled")) {
            // Disable the "Approve" button if the status is "Cancelled"
            holder.cancelButton.setEnabled(false);
        } else {
            // Enable the "Approve" button if the status is not "Cancelled"
            holder.cancelButton.setEnabled(true);
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.appointmentStatusTxt.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("You want to Cancel an Appointment, if YES Click CANCEL");

                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(holder.appointmentStatusTxt.getContext(), DoctorHomeActivity.class);
                        holder.appointmentStatusTxt.getContext().startActivity(intent);

                    }});
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Set senderPhone to "Reschedule"
                        Map<String, Object> map = new HashMap<>();
                        map.put("appointmentStatus", "Reschedule");
                        FirebaseDatabase.getInstance().getReference().child("appointment")
                                .child(model.key)
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(holder.appointmentStatusTxt.getContext(), "Status set to Reschedule", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(holder.appointmentStatusTxt.getContext(), DoctorHomeActivity.class);
//                                        holder.appointmentStatusTxt.getContext().startActivity(intent);


                                        // Send SMS when appointment is rescheduled
                                        String phoneNo = model.getSenderPhone();
                                        String message = "Your appointment has been rescheduled. Please contact us for further details.";

                                        // Use SMS Manager to send SMS
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(phoneNo, null, message, null, null);

                                        Toast.makeText(holder.appointmentStatusTxt.getContext(), "Status set to Reschedule", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(holder.appointmentStatusTxt.getContext(), DoctorHomeActivity.class);
                                        holder.appointmentStatusTxt.getContext().startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(holder.appointmentStatusTxt.getContext(), "Error setting phone to pending", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                builder.show();
            }
        });}

    }

    @Override
    public int getItemCount() {
        return vDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return myFilterData;
    }


    private Filter myFilterData = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String key = constraint.toString().trim();
            if (key.isEmpty()) {
                vDataFiltered = vData;
            } else {

                List<AppointmentModel> FilteredList = new ArrayList<>();
//                for (AppointmentModel row : vData) {
//                    if (row.getName().toString().contains(key) || String.valueOf(row.getDt()).contains(key) || String.valueOf(row.getTm()).contains(key) || String.valueOf(row.getAppmt()).contains(key) ) {
//                        FilteredList.add(row);
//                    }
//                }
                vDataFiltered = FilteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = vDataFiltered;
            return filterResults;


        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            vDataFiltered= (List<AppointmentModel>)filterResults.values;
            notifyDataSetChanged();
        }
    };
    class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, dateTxt, timeTxt, senderNameTxt, senderPhoneNumberTxt, appointmentStatusTxt;

        Button approveButton, cancelButton;
        public AppointmentViewHolder(View itemView) {
            super(itemView);
            nameTxt= (TextView) itemView.findViewById(R.id.textViewdocTitle);
            dateTxt = (TextView) itemView.findViewById(R.id.textViewDateDisplay);
            timeTxt = (TextView) itemView.findViewById(R.id.textViewTimeDisplay);
            senderNameTxt = (TextView) itemView.findViewById(R.id.senderName);
            senderPhoneNumberTxt = (TextView) itemView.findViewById(R.id.senderPhoneNumber);
            appointmentStatusTxt = (TextView) itemView.findViewById(R.id.appointmentStatus);
            approveButton = (Button) itemView.findViewById(R.id.btnApprove);
            cancelButton = (Button) itemView.findViewById(R.id.btnCancel);


        }

    }

}

