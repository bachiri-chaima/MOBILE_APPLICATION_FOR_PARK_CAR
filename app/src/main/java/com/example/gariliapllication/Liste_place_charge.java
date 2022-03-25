package com.example.gariliapllication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Liste_place_charge extends AppCompatActivity {

    RecyclerView recyclerView;
    BDHelper db;
    ArrayList<String> id_place,nom_place , disponibiliter ;

    Adapterrecycle_place_charge adapterrecycle_place_charge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_place_charge);




        recyclerView = findViewById(R.id.recycle_view_lot_parking_charge);


        db = new BDHelper(Liste_place_charge.this);
        id_place = new ArrayList<>();
        nom_place = new ArrayList<>();
        disponibiliter = new ArrayList<>();
        db = new BDHelper(Liste_place_charge.this);
        AjouterDatadansArray();

        adapterrecycle_place_charge = new Adapterrecycle_place_charge( this, id_place, nom_place, disponibiliter);
        recyclerView.setAdapter(adapterrecycle_place_charge);
        recyclerView.setLayoutManager(new LinearLayoutManager(Liste_place_charge.this));

    }

    void AjouterDatadansArray(){

        Cursor cursor = db.readallplacecharge();
        if (cursor.getCount() == 0 ){
            Toast.makeText(Liste_place_charge.this,"pas de donné", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                id_place.add(cursor.getString(0));
                nom_place.add(cursor.getString(1));
                disponibiliter.add(cursor.getString(2));

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}