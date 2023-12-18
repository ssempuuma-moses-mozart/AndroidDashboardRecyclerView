package com.example.ulcerpatientapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulcerpatientapp.ModelClass.DoctorModel;
import com.example.ulcerpatientapp.R;
import com.example.ulcerpatientapp.Activity.appointment;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> implements Filterable {

    private Context context;
    List<DoctorModel> vData;
    List<DoctorModel> vDataFiltered;

    public DoctorAdapter(Context context, List<DoctorModel> vData) {
        this.context = context;
        this.vData = vData;
        this.vDataFiltered = vData;
    }

    @Override
    public DoctorAdapter.DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_doctor_data, parent,false);

        return new DoctorAdapter.DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorAdapter.DoctorViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DoctorModel model = vDataFiltered.get(position);
        //Adding data to xml file
        holder.userNameTxt.setText(model.getLastName());
        holder.specializationTxt.setText(model.getSpecialization());
        holder.timeSlotTxt.setText(model.getUserName());
        holder.phoneNumberTxt.setText(model.getPhoneNumber());


        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context); // Assuming mContext is your activity context

                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to proceed with Appointment?");

                // Set up the buttons
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DoctorModel clickedDoctor = vDataFiltered.get(position);
                        String username = clickedDoctor.getLastName();
                        String specialization = clickedDoctor.getPhoneNumber();
                        String phonenumber = clickedDoctor.getUserName();
//                        String timeslot = clickedDoctor.getTimeSlot();

                        Intent intent = new Intent(context, appointment.class);
                        intent.putExtra("username", username);
                        intent.putExtra("specialization", specialization);
                        intent.putExtra("phonenumber", phonenumber);

//                        intent.putExtra("timeslot", timeslot);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the action when the user clicks "Cancel" or simply dismiss the dialog
                        dialog.dismiss();
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

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

                List<DoctorModel> FilteredList = new ArrayList<>();
                for (DoctorModel row : vData) {
                    if (row.getFirstName().toString().contains(key) || String.valueOf(row.getLastName()).contains(key) || String.valueOf(row.getUserName()).contains(key) || String.valueOf(row.getPhoneNumber()).contains(key) ) {
                        FilteredList.add(row);
                    }
                }
                vDataFiltered = FilteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = vDataFiltered;
            return filterResults;


        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            vDataFiltered= (List<DoctorModel>)filterResults.values;
            notifyDataSetChanged();
        }
    };
    class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTxt, specializationTxt, timeSlotTxt, phoneNumberTxt;

        ImageView viewButton;
        public DoctorViewHolder(View itemView) {
            super(itemView);
            userNameTxt = (TextView) itemView.findViewById(R.id.editUserName);
            specializationTxt = (TextView) itemView.findViewById(R.id.editSpecializationName);
            timeSlotTxt = (TextView) itemView.findViewById(R.id.editTimeSlot);
            phoneNumberTxt = (TextView)itemView.findViewById(R.id.editPhoneNumber);
            viewButton =(ImageView)  itemView.findViewById(R.id.imageViewbook);

        }



    }

}
