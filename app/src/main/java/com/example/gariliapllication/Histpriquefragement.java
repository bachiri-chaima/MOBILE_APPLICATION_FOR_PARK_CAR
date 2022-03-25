package com.example.gariliapllication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Histpriquefragement extends Fragment {
    String data;

    RecyclerView recyclerView;
    ArrayList<String> id_res,date,heure_ent,heure_str,cnf,prix,id_usr,id_place,id_vehicule,nom_place,nom_vehicule,id_reservation;
    Adapter_reservation_client adapter_reservation_client;
    BDHelper db;

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.fragment_histpriquefragement,container,false);

        Bundle bundle = this.getArguments();
        data = bundle.getString("id2");
        Toast.makeText(getContext(),data,Toast.LENGTH_LONG).show();

        recyclerView = root.findViewById(R.id.recycle_view_reservation_client);
        recyclerView.setHasFixedSize(true);

        db = new BDHelper(getActivity());
        id_res = new ArrayList<>();
        date = new ArrayList<>();
        id_res = new ArrayList<>();
        heure_ent = new ArrayList<>();
        heure_str = new ArrayList<>();
        cnf = new ArrayList<>();
        prix = new ArrayList<>();
        id_usr = new ArrayList<>();
        id_place = new ArrayList<>();
        id_vehicule = new ArrayList<>();
        nom_place = new ArrayList<>();
        nom_vehicule = new ArrayList<>();
        id_reservation = new ArrayList<>();


        db = new BDHelper(getActivity());

        ajouterplace(data);
        ajoutermatricule(data);

        ajouterdata(data);
        Log.d("matricule",nom_vehicule.get(0).toString());
        adapter_reservation_client = new Adapter_reservation_client(getContext(),id_res,date,heure_ent,heure_str,cnf,prix,id_usr,nom_place,nom_vehicule);
        recyclerView.setAdapter(adapter_reservation_client);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                db = new BDHelper(getContext());
                ajouterdata(data);
                boolean update_place = db.Updateplace_client_reservation(id_place.get(viewHolder.getAdapterPosition()),false);
                boolean update_cnf = db.Updatrecnf(id_reservation.get(viewHolder.getAdapterPosition()),false);
                if (update_cnf == true){
                    Toast.makeText(getContext(),"Supprimer",Toast.LENGTH_LONG).show();

                }
                adapter_reservation_client.notifyDataSetChanged();

            }
        }).attachToRecyclerView(recyclerView);


        return root;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getActivity().recreate();
    }

    void ajouterdata(String user){
        Cursor cursor = db.readalres_client(user);
        if (cursor.getCount() == 0){
            Toast.makeText(getActivity(),"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                id_usr.add(cursor.getString(0));
                id_reservation.add(cursor.getString(0));
                date.add(cursor.getString(1));
                heure_ent.add(cursor.getString(2));
                heure_str.add(cursor.getString(3));
                prix.add(cursor.getString(4));
                cnf.add(cursor.getString(5));
                id_usr.add(cursor.getString(6));
                id_place.add(cursor.getString(7));
                id_vehicule.add(cursor.getString(8));
            }
        }
    }
    void ajouterplace( String user){
        Cursor cursor = db.read_place_name_client_join(user);
        if (cursor.getCount() == 0){
            Toast.makeText(getActivity(),"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                nom_place.add(cursor.getString(1));
            }
        }
    }
    void ajoutermatricule( String user){
        Cursor cursor = db.read_vehicule_name_client_join(user);
        if (cursor.getCount() == 0){
            Toast.makeText(getActivity(),"pas de donnes",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                nom_vehicule.add(cursor.getString(1));
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