package com.example.gariliapllication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Liste_lot_parking extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton ajouter,right;
    BDHelper db;
    ArrayList<String> id_place,nom_place , disponibiliter ;

    Adapterrecycle_place adapterrecycle_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_lot_parking);

        recyclerView = findViewById(R.id.recycle_view_lot_parking);
        ajouter = findViewById(R.id.addparking_place);
        right = findViewById(R.id.parking_charge);


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Liste_lot_parking.this,Liste_place_charge.class);
                startActivity(intent);
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Liste_lot_parking.this,Add_place.class);
                startActivity(intent);
            }
        });

        db = new BDHelper(Liste_lot_parking.this);
        id_place = new ArrayList<>();
        nom_place = new ArrayList<>();
        disponibiliter = new ArrayList<>();
        db = new BDHelper(Liste_lot_parking.this);
        AjouterDatadansArray();

        adapterrecycle_place = new Adapterrecycle_place(Liste_lot_parking.this,this,id_place,nom_place,disponibiliter);
        recyclerView.setAdapter(adapterrecycle_place);
        recyclerView.setLayoutManager(new LinearLayoutManager(Liste_lot_parking.this));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //pour rafrecher les donnees
        if (requestCode == 1){
            recreate();
        }
    }

    void AjouterDatadansArray(){

        Cursor cursor = db.readallplace();
        if (cursor.getCount() == 0 ){
            Toast.makeText(Liste_lot_parking.this,"pas de donné", Toast.LENGTH_SHORT).show();
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