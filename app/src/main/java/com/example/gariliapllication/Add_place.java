package com.example.gariliapllication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Add_place extends AppCompatActivity {

    Boolean place = null;
    CardView libre,charge;
    Button ajouter;
    String NOM ;
    BDHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        final TextView nom = (TextView) findViewById(R.id.add_place_nom);
        libre = findViewById(R.id.place_libre);
        charge = findViewById(R.id.place_charge);
        ajouter= findViewById(R.id.ajouter_place);

        libre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place = false;
            }
        });
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place = true;
            }
        });


        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NOM= nom.getText().toString();
                db = new BDHelper(Add_place.this);

                if (NOM.isEmpty()) {

                    Toast.makeText(Add_place.this, "Saisir le champs s'il vous plait", Toast.LENGTH_LONG).show();

                }else {
                    if (place == null){
                        Toast.makeText(Add_place.this, "Choisir le type de place s'il vous plait", Toast.LENGTH_LONG).show();
                    }else {
                        boolean name = db.checkplace(NOM);
                        if (name == false){

                            boolean insert = db.insertPlace(NOM,place);
                            if (insert == true ){
                                Toast.makeText(Add_place.this, "Place ajouter ", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(Add_place.this, "Erreur", Toast.LENGTH_LONG).show();
                            }
                        }else {

                            Toast.makeText(Add_place.this, "Place existe deja", Toast.LENGTH_LONG).show();
                        }
                    }
            }}
            }
        );
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