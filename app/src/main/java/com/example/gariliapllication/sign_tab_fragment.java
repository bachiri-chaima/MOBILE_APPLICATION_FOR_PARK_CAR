package com.example.gariliapllication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.gariliapllication.base_de_donne.DATABASE_NAME;

public class sign_tab_fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Button sign_in;
    float valeur=0;
    Spinner spinner;

    Boolean admin = false;
    String NOM , PRENOM , NUMERO , EMAIL , MDP ;

    BDHelper db;
    client client;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.sign_in_tab_fragement,container,false);

        final TextView  nom = (TextView) root.findViewById(R.id.Nom);
       final TextView prenom= (TextView) root.findViewById(R.id.Prenom);
       final TextView numero= (TextView) root.findViewById(R.id.Numero);
       final TextView email= (TextView) root.findViewById(R.id.Email2);
       final  TextView mdp= (TextView) root.findViewById(R.id.Mot_de_paase2);
        sign_in= root.findViewById(R.id.button_sign);


        //client = new client(NOM,PRENOM,NUMERO,EMAIL,MDP, admin);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOM= nom.getText().toString();
                PRENOM= prenom.getText().toString();
                NUMERO= numero.getText().toString();
                EMAIL= email.getText().toString();
                MDP= mdp.getText().toString();
                if (NOM.isEmpty() || PRENOM.equals("") || NUMERO.equals("") || EMAIL.equals("") || MDP.equals("") ){

                    Toast.makeText(getActivity(),"Saisir toutes les champs s'il vous plait",Toast.LENGTH_LONG).show();
                }
               else{
                    boolean validationemail = validationEmailadress(EMAIL);
                    if (validationemail == true){


                        db = new BDHelper(getActivity());
                        Boolean resultat = db.checkUser(EMAIL);

                      if (resultat == false){

                        if (admin == true){

                           boolean inserer_admin = db.insertData(NOM,PRENOM,NUMERO,EMAIL,MDP,admin,null);
                            //interface admin
                            if (inserer_admin == true){
                                Toast.makeText(getActivity(),"inscreption succes \n Log in s'il vous plait",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(),"problem d'inscreption ",Toast.LENGTH_LONG).show();
                            }

                        }else {

                            boolean inserer_user = db.insertData(NOM,PRENOM,NUMERO,EMAIL,MDP,admin,null);
                            //interface user
                            if (inserer_user == true){
                                Toast.makeText(getActivity(),"inscreption succes \n Log in s'il vous plait",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(),"problem d'inscreption ",Toast.LENGTH_LONG).show();
                            }

                        }
                      }else {
                       Toast.makeText(getActivity(),"Email deja exister \n s'il vous plait Log in",Toast.LENGTH_LONG).show();
                          }
                    }else {
                        Toast.makeText(getActivity(),"Email est non valide", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        spinner = root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.user_admin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        nom.setTranslationX(800);
        prenom.setTranslationX(800);
        numero.setTranslationX(800);
        email.setTranslationX(800);
        mdp.setTranslationX(800);
        sign_in.setTranslationX(800);


        nom.setAlpha(valeur);
        prenom.setAlpha(valeur);
        email.setAlpha(valeur);
        mdp.setAlpha(valeur);
        sign_in.setAlpha(valeur);
        numero.setAlpha(valeur);

        nom.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        prenom.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        numero.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        mdp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        sign_in.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        return root;
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
}
