package com.example.gariliapllication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Forget_password extends AppCompatActivity {

    TextInputLayout email;

    Button verifier;
    BDHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.verifier_email);
        verifier = findViewById(R.id.verifier_email_bouton);

        verifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_txt2 = email.getEditText().getText().toString().trim();
                db = new BDHelper(Forget_password.this);

                Boolean resultat = db.checkUser(email_txt2);

                if (resultat == true){

                    showAlertDialog(email_txt2);

                }else {
                    Toast.makeText(Forget_password.this,"Email non exister",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showAlertDialog(String email_txt) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Forget_password.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Forget_password.this).inflate(R.layout.activity_alert_dialogue_password,(ConstraintLayout)findViewById(R.id.layout_dialoque_mot_de_passe));

        builder.setView(view);
        ((TextView)view.findViewById(R.id.titre_dialogue)).setText(getResources().getString(R.string.password_titre));
        ((ImageView) view.findViewById(R.id.icone_dialoque)).setImageResource(R.drawable.ic_baseline_check_24);
        final TextView mdp = ((TextView) view.findViewById(R.id.changer_mot_de_passe));



        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.confirmation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add = email_txt.toString().trim();
                String mdp_text= mdp.getText().toString();

                if (mdp_text.isEmpty()) {

                    Toast.makeText(Forget_password.this,"Ajouter le nouveau mot de passe",Toast.LENGTH_LONG).show();

                }else {

                boolean accepter = db.Updatemdp(add,mdp_text);

                if (accepter == true){

                    Toast.makeText(Forget_password.this,"Mot de passe changer ",Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }}
        });
        view.findViewById(R.id.annulation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }
}