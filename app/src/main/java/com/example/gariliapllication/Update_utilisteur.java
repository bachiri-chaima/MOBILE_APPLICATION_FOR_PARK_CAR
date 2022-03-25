package com.example.gariliapllication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class Update_utilisteur extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button Update,delate;
    Spinner spinner;
    Boolean admin = false;
    String NOM , PRENOM , NUMERO , EMAIL , MDP ;
    String id1,nom1 , prenom1 , numero1 , email1 ,admistrateur;

    BDHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_utilisteur);
        final TextView nom = (TextView) findViewById(R.id.add_nom_utilisateur2);
        final TextView prenom= (TextView) findViewById(R.id.add_prenom_utilisateur2);
        final TextView numero= (TextView) findViewById(R.id.add_numero_utilisateur2);
        final TextView email= (TextView) findViewById(R.id.add_email_utilisateur2);
        final  TextView mdp= (TextView) findViewById(R.id.add_mot_passe_utilisateur2);
        Update= findViewById(R.id.update_utilisateur);
        delate= findViewById(R.id.suprimmer_utilisateur);


        spinner = findViewById(R.id.spinner_utilisateur2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Update_utilisteur.this,R.array.user_admin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getIntentData();
        //setting data
        nom.setText(nom1);
        prenom.setText(prenom1);
        numero.setText(numero1);
        email.setText(email1);


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOM= nom.getText().toString().trim();
                PRENOM= prenom.getText().toString().trim();
                NUMERO= numero.getText().toString().trim();
                EMAIL= email.getText().toString().trim();
                MDP= mdp.getText().toString().trim();


                boolean validationemail = validationEmailadress(EMAIL);
                if (validationemail == true){



                        if (admin == true){

                            db = new BDHelper(Update_utilisteur.this);
                            boolean update_admin = db.Updatedata(id1,NOM,PRENOM,NUMERO,EMAIL,MDP,admin);

                            //interface admin
                            if (update_admin == true){

                                Toast.makeText(Update_utilisteur.this,"Modification succes ",Toast.LENGTH_LONG).show();

                            }else {

                                Toast.makeText(Update_utilisteur.this,"problem de modification ",Toast.LENGTH_LONG).show();
                            }

                        }else {

                            boolean update_user = db.Updatedata(id1,NOM,PRENOM,NUMERO,EMAIL,MDP,admin);
                            //interface user
                            if (update_user == true){
                                Toast.makeText(Update_utilisteur.this,"Modification succes ",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(Update_utilisteur.this,"problem de modefication ",Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {

                    Toast.makeText(Update_utilisteur.this,"Email est non valide", Toast.LENGTH_SHORT).show();

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
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nom") && getIntent().hasExtra("prenom") && getIntent().hasExtra("numero") && getIntent().hasExtra("email")){
            //get Datta
            id1 = getIntent().getStringExtra("id");
            nom1 = getIntent().getStringExtra("nom");
            prenom1 = getIntent().getStringExtra("prenom");
            numero1 = getIntent().getStringExtra("numero");
            email1 = getIntent().getStringExtra("email");

        }else {
            Toast.makeText(this,"pas de donnés",Toast.LENGTH_SHORT).show();
        }
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
        }}
     void AlertDelate(){
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Supression !");
         builder.setMessage("Vous voulez vraiment supprimer l'utilisateur : " + NOM + "?" );
         builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 db = new BDHelper(Update_utilisteur.this);
                 boolean delate = db.delate_user(id1);
                 if (delate == true) {
                     Toast.makeText(Update_utilisteur.this,"suppression valider",Toast.LENGTH_SHORT).show();
                     finish();
                 }
                 else {
                     Toast.makeText(Update_utilisteur.this,"Erreur de suppression",Toast.LENGTH_SHORT).show();
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