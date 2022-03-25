package com.example.gariliapllication;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class login_tab_fragment extends Fragment {

    Button froget_mdp;
    Button login;
    BDHelper db;
    ArrayList<String> id;
    boolean connecter = false;

    String EMAIL,MDP;
    float valeur=0;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.login_tab_fragement,container,false);
        final TextView email= (TextView) root.findViewById(R.id.Email);
        final  TextView mdp= (TextView) root.findViewById(R.id.Mot_de_passe_login);
        froget_mdp= root.findViewById(R.id.forhet_mo_de_passe);
        login= root.findViewById(R.id.button_login);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMAIL= email.getText().toString();
                MDP= mdp.getText().toString();
                if (EMAIL.isEmpty() || MDP.isEmpty()){
                    Toast.makeText(getActivity(),"Saisir toutes les champs s'il vous plait",Toast.LENGTH_LONG).show();
                }else{
                    UserLogin(v);
                    if (connecter == true){
                    boolean validationemail = validationEmailadress(EMAIL);
                    if (validationemail == true){

                        db = new BDHelper(getActivity());
                        boolean resultas = db.check_user_password(EMAIL,MDP);

                        if (resultas == true){

                            boolean admin = false;
                            boolean if_admin = db.chekEmail_admin(admin,EMAIL);

                            if (if_admin == true) {

                                Toast.makeText(getActivity(),"Admin", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Home_admin.class);
                                startActivity(intent);
                            }
                            if (if_admin == false){

                                Intent intent = new Intent(getActivity(), home_garili.class);
                                 db = new BDHelper(getActivity());
                                 id = new ArrayList<>();
                                 getid(EMAIL);
                                 intent.putExtra("id",String.valueOf(id.get(0).toString()));
                                 startActivity(intent);

                            }

                        }else {
                            Toast.makeText(getActivity(),"Email et mot de passe invalide", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getActivity(),"Email est non valide", Toast.LENGTH_SHORT).show();
                    }

                }}

            }
        });

        froget_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Forget_password.class);
                startActivity(intent);

            }
        });


        email.setTranslationX(800);
        mdp.setTranslationX(800);
        froget_mdp.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(valeur);
        mdp.setAlpha(valeur);
        froget_mdp.setAlpha(valeur);
        login.setAlpha(valeur);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mdp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        froget_mdp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }

    private  boolean validationEmailadress(String email){
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else {
            return  false;
        }
    }






    //methode for cheking connection servise and showing with message
    public void UserLogin(View view){
        if(!isconnecter(this)){
            connecter = false;
            showDialogclient();
            Toast.makeText(getActivity(),"pas d'internet!",Toast.LENGTH_LONG);}
        else {
            connecter = true;
        }
    }

    private boolean isconnecter(login_tab_fragment login_tab_fragment) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login_tab_fragment.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificonnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileconnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if((wificonnection != null && wificonnection.isConnected()) || (mobileconnection != null && mobileconnection.isConnected())){
            return true;
        } else {
            return false;
        }
    }

    private void showDialogclient() {

        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setMessage("veuillez vous connecter à l'internet pour continuer")
                .setCancelable(false).setPositiveButton("Connecter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                connecter = true;
            }
        }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(),loginActivity.class));
                connecter = false;

            }
        });
        AlertDialog alert =builder.create();
        alert.show();
    }


    void getid(String EMI){
        Cursor cursor = db.getId(EMI);
        if (cursor.getCount() == 0 ){
            //pas de donné
        }else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
            }
        }
    }


}
