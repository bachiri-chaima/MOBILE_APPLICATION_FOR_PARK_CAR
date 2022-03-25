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

public class Liste_utulisateur extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_utilisateur;
    BDHelper db;
    ArrayList<String> utilisateur_id,utilisateur_nom,utilisateur_prenom,utilisateur_email,utilisateur_numero;
    Adapterrecycle_utilisateur adapterrecycle_utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_utulisateur);

        recyclerView = findViewById(R.id.recycle_view_utilisateur);
        add_utilisateur = findViewById(R.id.addutilisateur);

        add_utilisateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Liste_utulisateur.this,Add_utilisateur.class);
                startActivity(intent);
            }
        });

        db = new BDHelper(Liste_utulisateur.this);
        utilisateur_id = new ArrayList<>();
        utilisateur_nom = new ArrayList<>();
        utilisateur_prenom = new ArrayList<>();
        utilisateur_email = new ArrayList<>();
        utilisateur_numero = new ArrayList<>();

        AjouterDatadansArray();

        adapterrecycle_utilisateur = new Adapterrecycle_utilisateur(Liste_utulisateur.this,this,utilisateur_id,utilisateur_nom,utilisateur_prenom,utilisateur_numero,utilisateur_email);

        recyclerView.setAdapter(adapterrecycle_utilisateur);
        recyclerView.setLayoutManager(new LinearLayoutManager(Liste_utulisateur.this));
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
        Cursor cursor = db.readallusers();
        if (cursor.getCount() == 0 ){
            Toast.makeText(Liste_utulisateur.this,"pas de donné", Toast.LENGTH_SHORT).show();
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