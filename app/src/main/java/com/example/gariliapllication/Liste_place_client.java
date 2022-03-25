package com.example.gariliapllication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Liste_place_client extends AppCompatActivity {

    RecyclerView recyclerView;
    BDHelper db;
    ArrayList<String> id_place,nom_place , disponibiliter ;

    Adapterrecycle_place_client adapterrecycle_place_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_place_client);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user_id");

        recyclerView = findViewById(R.id.recycle_view_lot_parking_client);


        db = new BDHelper(Liste_place_client.this);
        id_place = new ArrayList<>();
        nom_place = new ArrayList<>();
        disponibiliter = new ArrayList<>();
        db = new BDHelper(Liste_place_client.this);
        AjouterDatadansArray();

        adapterrecycle_place_client = new Adapterrecycle_place_client( Liste_place_client.this,this, id_place, nom_place, disponibiliter,user);
        recyclerView.setAdapter(adapterrecycle_place_client);
        recyclerView.setLayoutManager(new LinearLayoutManager(Liste_place_client.this));
    }

    void AjouterDatadansArray(){

        Cursor cursor = db.readallplacelibre();
        if (cursor.getCount() == 0 ){
            Toast.makeText(Liste_place_client.this,"pas de donné", Toast.LENGTH_SHORT).show();
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