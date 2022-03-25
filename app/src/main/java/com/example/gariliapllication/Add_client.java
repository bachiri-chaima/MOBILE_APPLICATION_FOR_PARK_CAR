package com.example.gariliapllication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Add_client extends AppCompatActivity {
    Boolean admin = false;
    Button ajouter;
    String NOM , PRENOM , NUMERO , EMAIL , MDP ;
    BDHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        final TextView nom = (TextView) findViewById(R.id.add_nom_client);
        final TextView prenom= (TextView) findViewById(R.id.add_prenom_client);
        final TextView numero= (TextView) findViewById(R.id.add_numero_client);
        final TextView email= (TextView) findViewById(R.id.add_email_client);
        final  TextView mdp= (TextView) findViewById(R.id.add_mot_passe_client);
        ajouter= findViewById(R.id.ajouter_client);




        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NOM= nom.getText().toString();
                PRENOM= prenom.getText().toString();
                NUMERO= numero.getText().toString();
                EMAIL= email.getText().toString();
                MDP= mdp.getText().toString();

                db = new BDHelper(Add_client.this);

                if (NOM.isEmpty() || PRENOM.equals("") || NUMERO.equals("") || EMAIL.equals("") || MDP.equals("") ) {

                    Toast.makeText(Add_client.this, "Saisir toutes les champs s'il vous plait", Toast.LENGTH_LONG).show();

                }else {
                    boolean validationemail = validationEmailadress(EMAIL);
                    if (validationemail == true){

                        Boolean resultat = db.checkUser(EMAIL);

                        if (resultat == false){

                            if (admin == false){

                                boolean inserer_client = db.insertData(NOM,PRENOM,NUMERO,EMAIL,MDP,admin,null);
                                //interface admin
                                if (inserer_client == true){
                                    Toast.makeText(Add_client.this,"inscreption succes",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(Add_client.this,"problem d'inscreption ",Toast.LENGTH_LONG).show();
                                }

                            }
                        }else {
                            Toast.makeText(Add_client.this,"Email deja exister",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Add_client.this,"Email est non valide", Toast.LENGTH_SHORT).show();
                    }
                }
            }}
        );

    }

    private  boolean validationEmailadress(String email){

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;

        }else {
            return  false;
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