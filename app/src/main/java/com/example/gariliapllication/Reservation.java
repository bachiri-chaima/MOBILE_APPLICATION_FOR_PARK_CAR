package com.example.gariliapllication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reservation extends AppCompatActivity {

    CardView place,heure_entrer,heure_sortie;
    TextView date_current;
    TextView heure_ent,heure_str;
    TextInputLayout matricule;
    TextView prix_txt;

    Float convert1,convert2;

    Float minute1,minute2,h1,h2;
    Float a;

    final int prix_fix = 100;
    String data,id_place,nom_place,dispo,recycle_usr;
    Button reserver;

    Date current_date;
    int heure1,heure2,min1,min2;
    BDHelper db;

    ArrayList<String> id_reservation,id_vehicule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Bundle intent = getIntent().getExtras();
        data = intent.getString("id_usr");
       // Toast.makeText(Reservation.this,data,Toast.LENGTH_LONG).show();

        matricule = findViewById(R.id.add_matricule_layout);
        place = findViewById(R.id.card_view_place);
        heure_entrer = findViewById(R.id.card_view_heure_1);
        heure_sortie = findViewById(R.id.card_view_heure_2);
        reserver = findViewById(R.id.ajouter_reservation);




        current_date = Calendar.getInstance().getTime();
        String date = java.text.DateFormat.getDateInstance().format(current_date);




        heure_ent = findViewById(R.id.heure_entrer);
        heure_str = findViewById(R.id.heure_sotie);

        date_current =findViewById(R.id.date_entrer);
        date_current.setText(date);




        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation.this,Liste_place_client.class);
                intent.putExtra("user_id",data);
                startActivity(intent);

            }
        });


        heure_entrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialiser l'heure
                TimePickerDialog timePickerDialog = new TimePickerDialog(Reservation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //initialiser heure et minute
                        heure1 = hourOfDay;
                        min1 = minute;
                        convert1 = Float.valueOf(String.valueOf(min1));

                        minute1 = convert1/60;

                        h1 = minute1+heure1;
                        //Toast.makeText(Reservation.this,String.valueOf(h1).toString(),Toast.LENGTH_SHORT).show();

                        //initialer le calendrier
                        Calendar calendar = Calendar.getInstance();
                        //set heure et minute
                        calendar.set(0,0,0,heure1,min1);
                        //set temp selectionner en textview
                        heure_ent.setText(DateFormat.format("hh:mm aa",calendar));

                    }
                },12,0,true);

                timePickerDialog.updateTime(heure1,min1);
                timePickerDialog.show();
            }
        });



        heure_sortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialiser l'heure
                TimePickerDialog timePickerDialog = new TimePickerDialog(Reservation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //initialiser heure et minute
                        heure2 = hourOfDay;
                        min2 = minute;
                        convert2 = Float.valueOf(String.valueOf(min2));

                        minute2 = convert2/60;

                        h2 = minute2+heure2;




                        //initialer le calendrier
                        Calendar calendar = Calendar.getInstance();
                        //set heure et minute
                        calendar.set(0,0,0,heure2,min2);
                        //set temp selectionner en textview
                        heure_str.setText(DateFormat.format("hh:mm aa",calendar));

                    }
                },12,0,true);

                timePickerDialog.updateTime(heure2,min2);
                timePickerDialog.show();
            }
        });

        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //matricule
                 String matricule_string = matricule.getEditText().getText().toString().trim();
                if (matricule_string.isEmpty()){
                    Toast.makeText(Reservation.this,"Ajouter une matricule ",Toast.LENGTH_SHORT).show();

                }else {
                    db = new BDHelper(Reservation.this);
                    boolean insert_matricule = db.insertmatricule(matricule_string);

                    if (insert_matricule == true){

                        db = new BDHelper(Reservation.this);
                        id_vehicule = new ArrayList<>();
                        getid_vehicule(matricule_string);
                    }else {

                        Toast.makeText(Reservation.this,"Echek \n réessayer s'il vous plait ",Toast.LENGTH_SHORT).show();
                    }
                }
                //add place
                getextra_place();
                String heure1 = heure_ent.getText().toString();
                String heure2 = heure_str.getText().toString();
                if (h2 == null || h1 == null){
                }else {
                    a = h2 - h1;
                }
                String prix = "";
                if ( a == null){
                    Toast.makeText(Reservation.this,"Remplir tous les champs",Toast.LENGTH_LONG).show();
                }else {
                    prix = String.valueOf(a*prix_fix);
                }

                //intentplace
                int user = Integer.parseInt(recycle_usr);
                int place = Integer.parseInt(id_place);
                int mat = Integer.parseInt(id_vehicule.get(0).toString());

                if (heure1.isEmpty() || heure2.isEmpty()){

                    Toast.makeText(Reservation.this,"ajouter heure ",Toast.LENGTH_LONG).show();

                }else {
                boolean insert_reservation = db.insertreservation(current_date.toString(),heure1,heure2,prix,true,user,place,mat);
                db.Updateplacecharge(id_place,true);
                if (insert_reservation == true){
                    Toast.makeText(Reservation.this,"Reservation avec succes",Toast.LENGTH_LONG).show();
                    //shwo dialogue
                    show(prix);
               }
            }

            }
        });


    }

    private void show(String prix_cl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Reservation.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Reservation.this).inflate(R.layout.prix_client,(ConstraintLayout)findViewById(R.id.prix_client_dialoque));

        builder.setView(view);
        ((TextView)view.findViewById(R.id.titre_dialoguecl)).setText(getResources().getString(R.string.reservation_titre));
        ((ImageView) view.findViewById(R.id.icone_dialoque)).setImageResource(R.drawable.ic_baseline_check_24);
        ((TextView)view.findViewById(R.id.text_meesage2)).setText(prix_cl.toString());


        AlertDialog alertDialog = builder.create();

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    void getid_reservation(String id_usr,String date){
        Cursor cursor = db.getId_reservation(id_usr,date);
        if (cursor.getCount() == 0 ){
            //pas de donné
        }else {
            while (cursor.moveToNext()){
                id_reservation.add(cursor.getString(1));
            }
        }
    }


    void getextra_place (){

        Bundle bundle = getIntent().getExtras();
        id_place=bundle.getString("id_place");
        nom_place=bundle.getString("nom_place");
        dispo = bundle.getString("dispo_place");
        recycle_usr = bundle.getString("recycle_usr");
    }
    void getid_vehicule(String matricule){
        Cursor cursor = db.getId_vehicule(matricule);
        if (cursor.getCount() == 0 ){
            //pas de donné
        }else {
            while (cursor.moveToNext()){
                id_vehicule.add(cursor.getString(0));
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