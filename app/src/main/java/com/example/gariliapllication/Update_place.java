package com.example.gariliapllication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Update_place extends AppCompatActivity {

    Button Update,delate;
    Boolean place = null;
    String NOM;
    String id1,nom1 ,dispo1;
    CardView libre,charge;


    BDHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_place);
        final TextView nom = (TextView) findViewById(R.id.update_place_nom);

        Update= findViewById(R.id.Update_place);
        delate= findViewById(R.id.supprimer_place);
        libre = findViewById(R.id.place_libre2);
        charge = findViewById(R.id.place_charge2);

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

        getIntentData();
        //setting data
        nom.setText(nom1);



        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOM= nom.getText().toString().trim();
                db = new BDHelper(Update_place.this);

                if (place == null){
                    Toast.makeText(Update_place.this, "Choisir le type de place s'il vous plait", Toast.LENGTH_LONG).show();
                }else {
                    boolean update_place = db.Updateplace(id1,NOM,place);

                    if (update_place == true){

                        Toast.makeText(Update_place.this,"Modification succes ",Toast.LENGTH_LONG).show();

                    }else {

                        Toast.makeText(Update_place.this,"problem de modification ",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NOM = nom.getText().toString().trim();
                AlertDelate();
            }
        });
    }



    void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nom") && getIntent().hasExtra("dispo")){
            //get Datta
            id1 = getIntent().getStringExtra("id");
            nom1 = getIntent().getStringExtra("nom");
            dispo1 = getIntent().getStringExtra("dispo");


        }else {
            Toast.makeText(this,"pas de donnés",Toast.LENGTH_SHORT).show();
        }
    }
    void AlertDelate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supression !");
        builder.setMessage("Vous voulez vraiment supprimer cette place : " + NOM + "?" );
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = new BDHelper(Update_place.this);


                boolean delate = db.delate_place(id1);


                if (delate == true) {
                    Toast.makeText(Update_place.this,"suppression valider",Toast.LENGTH_SHORT).show();
                    finish();

                }
                else {
                    Toast.makeText(Update_place.this,"Erreur de suppression",Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
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