package com.example.gariliapllication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Liste_admin extends AppCompatActivity {

    FloatingActionButton add_admin;
    RecyclerView recyclerView;
    BDHelper db;
    ArrayList<String> utilisateur_id,utilisateur_nom,utilisateur_prenom,utilisateur_email,utilisateur_numero;
    Adapterrecucle_admin adapterrecucle_admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_admin);

        recyclerView = findViewById(R.id.recycle_view_admin);
        add_admin = findViewById(R.id.addadmin);

        add_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Liste_admin.this,Add_admin.class);
                startActivity(intent);
            }
        });

        db = new BDHelper(Liste_admin.this);
        utilisateur_id = new ArrayList<>();
        utilisateur_nom = new ArrayList<>();
        utilisateur_prenom = new ArrayList<>();
        utilisateur_email = new ArrayList<>();
        utilisateur_numero = new ArrayList<>();

        AjouterDatadansArray();

        adapterrecucle_admin = new Adapterrecucle_admin(Liste_admin.this,this,utilisateur_id,utilisateur_nom,utilisateur_prenom,utilisateur_numero,utilisateur_email);

        recyclerView.setAdapter(adapterrecucle_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(Liste_admin.this));

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
        Cursor cursor = db.readalladmin();
        if (cursor.getCount() == 0 ){
            Toast.makeText(Liste_admin.this,"pas de donné", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                utilisateur_id.add(cursor.getString(0));
                utilisateur_nom.add(cursor.getString(1));
                utilisateur_prenom.add(cursor.getString(2));
                utilisateur_numero.add(cursor.getString(3));
                utilisateur_email.add(cursor.getString(4));
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