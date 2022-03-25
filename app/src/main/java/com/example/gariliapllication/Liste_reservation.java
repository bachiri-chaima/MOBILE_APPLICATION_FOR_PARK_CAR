package com.example.gariliapllication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Liste_reservation extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> id_utilisateur,date_utilisateur,heure_ent_utulisateur,heure_str_utulisateur,prix_utl,cnf_utl,id_user,id_place,id_veh,email_utilisateur,matricule,place_utl,id_reservation;
    Adapter_reservation_adr adapter_reservation_adr;

    BDHelper db;
    FloatingActionButton add_reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_reservation);

        recyclerView = findViewById(R.id.recycle_view_reservation);
        add_reservation = findViewById(R.id.add_reservtion);

        add_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Liste_reservation.this,Add_reservation.class);
                startActivity(intent);
            }
        });

        db = new BDHelper(Liste_reservation.this);

        id_utilisateur = new ArrayList<>();
        date_utilisateur = new ArrayList<>();
        heure_ent_utulisateur = new ArrayList<>();
        heure_str_utulisateur = new ArrayList<>();
        prix_utl = new ArrayList<>();
        cnf_utl = new ArrayList<>();
        id_user = new ArrayList<>();
        id_place = new ArrayList<>();
        id_veh = new ArrayList<>();
        email_utilisateur = new ArrayList<>();
        matricule = new ArrayList<>();
        place_utl = new ArrayList<>();
        id_reservation = new ArrayList<>();

        getData();
        getEMAIL();
        getmatricule();
        getplace();

        adapter_reservation_adr = new Adapter_reservation_adr(Liste_reservation.this,id_utilisateur,email_utilisateur,date_utilisateur,heure_ent_utulisateur,heure_str_utulisateur,prix_utl,cnf_utl,id_user,place_utl,matricule);
        recyclerView.setAdapter(adapter_reservation_adr);
        recyclerView.setLayoutManager(new LinearLayoutManager(Liste_reservation.this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            db = new BDHelper(Liste_reservation.this);
            getData();



            AlertDialog.Builder builder = new AlertDialog.Builder(Liste_reservation.this);
            builder.setTitle("Supression !");
            builder.setMessage("Vous voulez vraiment supprimer la reservation : " + id_reservation.get(position) + " ?" );
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db = new BDHelper(Liste_reservation.this);
                    boolean update_place = db.Updateplace_client_reservation(id_place.get(position),false);
                    boolean delate = db.delate_reservation(id_reservation.get(position));
                    if (delate == true) {
                        Toast.makeText(Liste_reservation.this,"suppression valider",Toast.LENGTH_SHORT).show();
                        recreate();

                    }
                    else {
                        Toast.makeText(Liste_reservation.this,"Erreur de suppression",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
            adapter_reservation_adr.notifyDataSetChanged();


        }

    };

    void getData(){
        Cursor cursor = db.readallreservation();
        if (cursor.getCount() == 0){
            Toast.makeText(Liste_reservation.this,"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                id_utilisateur.add(cursor.getString(0));
                id_reservation.add(cursor.getString(0));
                date_utilisateur.add(cursor.getString(1));
                heure_ent_utulisateur.add(cursor.getString(2));
                heure_str_utulisateur.add(cursor.getString(3));
                prix_utl.add(cursor.getString(4));
                cnf_utl.add(cursor.getString(5));
                id_user.add(cursor.getString(6));
                id_place.add(cursor.getString(7));
                id_veh.add(cursor.getString(8));
            }
        }
    }
    void getEMAIL(){
        Cursor cursor = db.read_all_email_admin_join();
        if (cursor.getCount() == 0){
            Toast.makeText(Liste_reservation.this,"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
               email_utilisateur.add(cursor.getString(0));
            }
        }
    }
    void getmatricule(){
        Cursor cursor = db.read_all_matricule_admin_join();
        if (cursor.getCount() == 0){
            Toast.makeText(Liste_reservation.this,"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                matricule.add(cursor.getString(0));
            }
        }
    }

    void getplace(){
        Cursor cursor = db.read_all_place_admin_join();
        if (cursor.getCount() == 0){
            Toast.makeText(Liste_reservation.this,"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                place_utl.add(cursor.getString(0));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}