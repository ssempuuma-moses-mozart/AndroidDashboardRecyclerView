package com.example.ulcerpatientapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

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

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> implements Filterable {

    private Context context;
    List<AppointmentModel> vData;
    List<AppointmentModel> vDataFiltered;

    public AppointmentAdapter(Context context, List<AppointmentModel> vData) {
        this.context = context;
        this.vData = vData;
        this.vDataFiltered = vData;
    }

    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.appointment_data, parent,false);

        return new AppointmentAdapter.AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppointmentAdapter.AppointmentViewHolder holder, final int position) {
        final AppointmentModel model = vDataFiltered.get(position);
        //Adding data to xml file
        holder.nameTxt.setText(model.getDoctorName());
        holder.dateTxt.setText(model.getAppointmentDate());
        holder.timeTxt.setText(model.getAppointmentTime());
        holder.senderNameTxt.setText(model.getSenderName());
        holder.senderPhoneNumberTxt.setText(model.senderPhone);
        holder.appointmentStatusTxt.setText(model.getAppointmentStatus());




        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.nameTxt.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();
//                dialogPlus.show();
                View view1 = dialogPlus.getHolderView();
                EditText NameTxt = view1.findViewById(R.id.textViewdocTitle);
                EditText  appointmentDateTxt = view1.findViewById(R.id.textViewDateDisplay);
                EditText  appointmentTimeTxt = view1.findViewById(R.id.textViewTimeDisplay);
                EditText senderNameTxt = view1.findViewById(R.id.senderName);
                EditText phoneNumbereTxt = view1.findViewById(R.id.senderPhoneNumber);



                Button btnUpdate = view1.findViewById(R.id.updateBtn);
                NameTxt.setText(model.getDoctorName());
                appointmentDateTxt.setText(model.getAppointmentDate());
                appointmentTimeTxt.setText(model.getAppointmentTime());
                senderNameTxt.setText(model.getSenderName());
                phoneNumbereTxt.setText(model.senderPhone);

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("doctorName",NameTxt.getText().toString());
                        map.put("appointmentDate",appointmentDateTxt.getText().toString());
                        map.put("appointmentTime",appointmentTimeTxt.getText().toString());
                        map.put("senderName", senderNameTxt.getText().toString());
                        map.put("senderPhone", phoneNumbereTxt.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("appointment")
                                .child(model.key).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.nameTxt.getContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show();

                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(holder.nameTxt.getContext(),"Error while Updating", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        });
        if (model.getAppointmentStatus().equals("Pending")) {
            holder.appointmentStatusTxt.setTextColor(Color.BLUE);
            holder.editButton.setEnabled(true);
            holder.cancelButton.setEnabled(true);
            holder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.appointmentStatusTxt.getContext());
                    builder.setTitle("Are you sure?");
                    builder.setMessage("You want to exit");

                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(context, AppointmentAdapter.class);

//                        Intent intent = new Intent(holder.nameTxt.getContext(), dashboard.class);
//                        holder.nameTxt.getContext().startActivity(intent);

                            // Set senderPhone to "pending"
                            Map<String, Object> map = new HashMap<>();
                            map.put("appointmentStatus", "Cancelled");

                            FirebaseDatabase.getInstance().getReference().child("appointment")
                                    .child(model.key)
                                    .updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.appointmentStatusTxt.getContext(), "Appointment set to Cancelled", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(holder.appointmentStatusTxt.getContext(), dashboard.class);
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
                    builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(holder.nameTxt.getContext(), dashboard.class);
                            holder.nameTxt.getContext().startActivity(intent);
                        }
                    });
                    builder.show();

                }
            });
        }
        else if (model.getAppointmentStatus().equals("Cancelled")) {
            // Disable the "Approve" button if the status is "Cancelled"
            holder.appointmentStatusTxt.setTextColor(Color.RED);

            // Show a popup/notification for cancelled appointments
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Appointment Cancelled");
            builder.setMessage("Hello, " + model.getSenderName() + " You Cancelled your Appointment with " + model.getDoctorName() + " Hope You Recall This.");

            // Add a button to dismiss the dialog
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Dismiss the dialog
                    dialogInterface.dismiss();
                }
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
else {
            // Enable the "Approve" button if the status is not "Cancelled"
            holder.cancelButton.setEnabled(true);
            holder.editButton.setEnabled(true);
            holder.appointmentStatusTxt.setTextColor(Color.parseColor("#FF018786"));
        }




        holder.setIsRecyclable(false);
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

        Button editButton, cancelButton;
        public AppointmentViewHolder(View itemView) {
            super(itemView);
            nameTxt= (TextView) itemView.findViewById(R.id.textViewdocTitle);
            dateTxt = (TextView) itemView.findViewById(R.id.textViewDateDisplay);
            timeTxt = (TextView) itemView.findViewById(R.id.textViewTimeDisplay);
            senderNameTxt = (TextView) itemView.findViewById(R.id.senderName);
            senderPhoneNumberTxt = (TextView) itemView.findViewById(R.id.senderPhoneNumber);
            appointmentStatusTxt = (TextView) itemView.findViewById(R.id.appointmentStatus);
            editButton =(Button)  itemView.findViewById(R.id.editBtn);
            cancelButton = (Button) itemView.findViewById(R.id.cancel);

        }
    }

}

