package com.example.gariliapllication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
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

public class Add_reservation extends AppCompatActivity {
    CardView place2,heure_entrer2,heure_sortie2;
    TextView date_current;
    TextView heure_ent,heure_str;

    Float convert1,convert2;
    ArrayList<String> id;

    Float minute1,minute2,h1,h2;
    Float a;
    String mat,em,ID;

    String id_place,nom_place,dispo;

    final int prix_fix = 100;
    Button reserver2;

    Date current_date;
    int heure1,heure2,min1,min2;
    BDHelper db;

    ArrayList<String> id_reservation,id_vehicule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);


        final TextView matricule = (TextView) findViewById(R.id.matricule_reservation);
        final TextView email= (TextView) findViewById(R.id.Email_reservation);


        place2 = findViewById(R.id.card_view_place2);
        heure_entrer2 = findViewById(R.id.card_view_heure_1_2);
        heure_sortie2 = findViewById(R.id.card_view_heure_2_2);
        reserver2 = findViewById(R.id.ajouter_reservation_2);

        current_date = Calendar.getInstance().getTime();
        String date = java.text.DateFormat.getDateInstance().format(current_date);




        heure_ent = findViewById(R.id.heure_entrer2);
        heure_str = findViewById(R.id.heure_sotie2);

        date_current =findViewById(R.id.date_entrer2);
        date_current.setText(date);



        place2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_reservation.this,Liste_place_admin.class);
                startActivity(intent);

            }
        });


        heure_entrer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialiser l'heure
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_reservation.this, new TimePickerDialog.OnTimeSetListener() {
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


        heure_sortie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialiser l'heure
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_reservation.this, new TimePickerDialog.OnTimeSetListener() {
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


        reserver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mat= matricule.getText().toString().trim();
                em= email.getText().toString().trim();
                Log.d("name",em);
                String heure1 = heure_ent.getText().toString();
                String heure2 = heure_str.getText().toString();

                if (mat.isEmpty() || em.isEmpty() || heure1.isEmpty() || heure2.isEmpty()){
                    Toast.makeText(Add_reservation.this,"Remplir toutes les champs",Toast.LENGTH_LONG).show();
                }else {

                    boolean validationemail = validationEmailadress(em);
                    if (validationemail == true){

                        db = new BDHelper(Add_reservation.this);
                        boolean resultas = db.checkUser(em);

                        if (resultas == true){
                            //insert matricule
                            id = new ArrayList<>();
                            getid(em);
                            ID = id.get(0).toString();
                            db = new BDHelper(Add_reservation.this);
                            boolean insert_matricule = db.insertmatricule(mat);

                            if (insert_matricule == true){

                                //get Id vehicule
                                db = new BDHelper(Add_reservation.this);
                                id_vehicule = new ArrayList<>();
                                getid_vehicule(mat);

                                //add place
                                getextra_place();
                                if (h2 == null || h1 == null){
                                    Toast.makeText(Add_reservation.this,"ajouter heure ",Toast.LENGTH_LONG).show();
                                }else {
                                    a = h2 - h1;
                                }

                                String prix = "";
                                if ( a == null){
                                    Toast.makeText(Add_reservation.this,"Remplir tous les champs",Toast.LENGTH_LONG).show();
                                }else {
                                    prix = String.valueOf(a*prix_fix);
                                }

                                Log.d("name",ID);

                                int ID_int = Integer.parseInt(ID);

                                //intentplace
                                int place = Integer.parseInt(id_place);
                                int id_veh = Integer.parseInt(id_vehicule.get(0).toString());

                                if (heure1.isEmpty() || heure2.isEmpty()){

                                    Toast.makeText(Add_reservation.this,"ajouter heure ",Toast.LENGTH_LONG).show();

                                }else {
                                    boolean insert_reservation = db.insertreservation(current_date.toString(),heure1,heure2,prix,true,ID_int,place,id_veh);
                                    //place devra true ou charge
                                    db.Updateplacecharge(id_place,true);
                                    if (insert_reservation == true){

                                        Toast.makeText(Add_reservation.this,"Reservation avec succes",Toast.LENGTH_LONG).show();
                                        //shwo dialogue
                                        show(prix);
                                    }
                                }



                            }else {
                                Toast.makeText(Add_reservation.this,"Erreur! \n Resseyer s'il vous plait",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(Add_reservation.this,"Email non exister",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(Add_reservation.this,"Email invalide",Toast.LENGTH_LONG).show();
                    }
                }



            }
        });

    }

    private void show(String prix_cl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Add_reservation.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Add_reservation.this).inflate(R.layout.prix_admin,(ConstraintLayout)findViewById(R.id.prix_admin_dialoque));

        builder.setView(view);
        ((TextView)view.findViewById(R.id.titre_dialoguead)).setText(getResources().getString(R.string.reservation_titre));
        ((ImageView) view.findViewById(R.id.icone_dialoque)).setImageResource(R.drawable.ic_baseline_check_24);
        ((TextView)view.findViewById(R.id.text_meesage_prix)).setText(prix_cl.toString());


        AlertDialog alertDialog = builder.create();

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    void getextra_place (){

        Bundle bundle = getIntent().getExtras();
        id_place=bundle.getString("id_place1");
        nom_place=bundle.getString("nom_place2");
        dispo = bundle.getString("dispo_place3");
    }

    private  boolean validationEmailadress(String email){
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else {
            return  false;
        }
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