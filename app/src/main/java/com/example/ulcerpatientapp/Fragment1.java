package com.example.ulcerpatientapp;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment implements View.OnClickListener {

    Button b1,b2,b3,b4,b5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        b1 = (Button) view.findViewById(R.id.button6);
        b2 = (Button) view.findViewById(R.id.button7);
        b3 = (Button) view.findViewById(R.id.button8);
        b4 = (Button) view.findViewById(R.id.button9);
        b5 = (Button) view.findViewById(R.id.button10);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);

        return view;
    }


//    @Override
//    public void onClick(View view) {
//
//        switch (view.getId()){
//            case (R.id.button6):
//
//                break;
//            case (R.id.button7):
//
//                break;
//            case (R.id.button8):
//
//                break;
//            case (R.id.button9):
//
//                break;
//            case (R.id.button10):
//
//                break;
//        }
//
//
//    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select an option")
                .setMessage("Close or Select?")
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Perform actions for 'Select'
                        switch (view.getId()) {
                            case (R.id.button6):
                                // Actions for button 6 when 'Select' is chosen
                                Toast.makeText(getContext(), "Button 6 selected", Toast.LENGTH_SHORT).show();
                                break;
                            case (R.id.button7):
                                // Actions for button 7 when 'Select' is chosen
                                Toast.makeText(getContext(), "Button 7 selected", Toast.LENGTH_SHORT).show();
                                break;
                            case (R.id.button8):
                                // Actions for button 8 when 'Select' is chosen
                                Toast.makeText(getContext(), "Button 8 selected", Toast.LENGTH_SHORT).show();
                                break;
                            case (R.id.button9):
                                // Actions for button 9 when 'Select' is chosen
                                Toast.makeText(getContext(), "Button 9 selected", Toast.LENGTH_SHORT).show();
                                break;
                            case (R.id.button10):
                                // Actions for button 10 when 'Select' is chosen
                                Toast.makeText(getContext(), "Button 10 selected", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss the dialog when 'Close' is chosen
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
