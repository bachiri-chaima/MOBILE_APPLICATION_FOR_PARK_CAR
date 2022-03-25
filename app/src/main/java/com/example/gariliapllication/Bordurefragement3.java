package com.example.gariliapllication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Bordurefragement3 extends Fragment {
    FloatingActionButton next_icone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.page4log,container,false);

        next_icone = root.findViewById(R.id.next_icone);
        next_icone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),loginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
