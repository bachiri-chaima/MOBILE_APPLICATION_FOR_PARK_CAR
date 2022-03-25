package com.example.gariliapllication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Parametrefragement extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.fragment_parametrefragement,container,false);

        return root;

    }
}