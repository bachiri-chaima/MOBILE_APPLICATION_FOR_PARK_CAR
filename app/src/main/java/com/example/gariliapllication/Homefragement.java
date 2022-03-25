package com.example.gariliapllication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Homefragement extends Fragment {


    Button reserver;
    String data,data1;

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.fragment_homefragement,container,false);

        reserver = root.findViewById(R.id.reserver);


        Bundle bundle = this.getArguments();


        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                data = bundle.getString("id");
                Intent intent = new Intent(getActivity(),Reservation.class);
                intent.putExtra("id_usr",data);
                startActivity(intent);
            }
        });


        return  root;
    }



}