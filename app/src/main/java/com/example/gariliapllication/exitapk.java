package com.example.gariliapllication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class exitapk extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.fragment_exitapk,container,false);

        /*Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Exit",true);
        startActivity(intent);

        getActivity().finish();
        System.exit(0);
*/
        return root;
    }
}