package com.example.gariliapllication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.layout.simple_spinner_dropdown_item;

public class Add_utilisateur extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Boolean admin = false;
    Button ajouter;
    String NOM , PRENOM , NUMERO , EMAIL , MDP ;
    BDHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utilisateur);


        final TextView nom = (TextView) findViewById(R.id.add_nom_utilisateur);
        final TextView prenom= (TextView) findViewById(R.id.add_prenom_utilisateur);
        final TextView numero= (TextView) findViewById(R.id.add_numero_utilisateur);
        final TextView email= (TextView) findViewById(R.id.add_email_utilisateur);
        final  TextView mdp= (TextView) findViewById(R.id.add_mot_passe_utilisateur);
        ajouter= findViewById(R.id.ajouter_utilisateur);



        spinner = findViewById(R.id.spinner_utilisateur);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Add_utilisateur.this,R.array.user_admin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOM= nom.getText().toString();
                PRENOM= prenom.getText().toString();
                NUMERO= numero.getText().toString();
                EMAIL= email.getText().toString();
                MDP= mdp.getText().toString();
                db = new BDHelper(Add_utilisateur.this);

                if (NOM.isEmpty() || PRENOM.equals("") || NUMERO.equals("") || EMAIL.equals("") || MDP.equals("") ) {

                    Toast.makeText(Add_utilisateur.this, "Saisir toutes les champs s'il vous plait", Toast.LENGTH_LONG).show();

                }else {
                    boolean validationemail = validationEmailadress(EMAIL);
                    if (validationemail == true){

                        Boolean resultat = db.checkUser(EMAIL);

                        if (resultat == false){

                            if (admin == true){

                                boolean inserer_admin = db.insertData(NOM,PRENOM,NUMERO,EMAIL,MDP,admin,null);
                                //interface admin
                                if (inserer_admin == true){
                                    Toast.makeText(Add_utilisateur.this,"inscreption succes",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(Add_utilisateur.this,"problem d'inscreption ",Toast.LENGTH_LONG).show();
                                }

                            }else {

                                boolean inserer_user = db.insertData(NOM,PRENOM,NUMERO,EMAIL,MDP,admin,null);
                                //interface user
                                if (inserer_user == true){

                                    Toast.makeText(Add_utilisateur.this,"inscreption succes ",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(Add_utilisateur.this,"problem d'inscreption ",Toast.LENGTH_LONG).show();
                                }

                            }
                        }else {
                            Toast.makeText(Add_utilisateur.this,"Email deja exister",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Add_utilisateur.this,"Email est non valide", Toast.LENGTH_SHORT).show();
                    }
                }
            }}
        );
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String choice = parent.getItemAtPosition(position).toString();
        if (choice.equals("administrateur")){
            admin = true;
        }else {
            admin = false;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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