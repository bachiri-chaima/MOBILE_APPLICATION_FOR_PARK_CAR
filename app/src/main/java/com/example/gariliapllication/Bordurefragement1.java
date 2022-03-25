package com.example.gariliapllication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class Bordurefragement1 extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.page2login,container,false);

        return root;

    }
}
