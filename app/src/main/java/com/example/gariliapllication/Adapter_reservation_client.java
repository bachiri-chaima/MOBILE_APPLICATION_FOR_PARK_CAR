package com.example.gariliapllication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_reservation_client extends RecyclerView.Adapter<Adapter_reservation_client.MyViewHolder> {

    Context context;
    ArrayList id_res,date,heure_ent,heure_str,cnf,prix,nom_user,nom_place, nom_vehicule ;

    Adapter_reservation_client(Context context, ArrayList id_res, ArrayList date, ArrayList heure_ent, ArrayList heure_str, ArrayList cnf, ArrayList prix, ArrayList nom_user, ArrayList nom_place, ArrayList name_vehicule) {
        this.context = context;
        this.id_res = id_res;
        this.date = date;
        this.heure_ent = heure_ent;
        this.heure_str = heure_str;
        this.cnf = cnf;
        this.prix = prix;
        this.nom_user = nom_user;
        this.nom_place = nom_place;
        this.nom_vehicule = name_vehicule;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_raw_reservation_client,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.date.setText(String.valueOf(date.get(position)));
        holder.heure_ent.setText(String.valueOf(heure_ent.get(position)));
        holder.heure_str.setText(String.valueOf(heure_str.get(position)));
        holder.prix.setText(String.valueOf(prix.get(position)));
        holder.place.setText(String.valueOf(nom_place.get(position)));
        holder.matricule.setText(String.valueOf(nom_vehicule.get(position)));


    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView matricule,date,heure_str,heure_ent,prix,place;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            matricule = itemView.findViewById(R.id.matricule_client);
            date = itemView.findViewById(R.id.date_client);
            heure_ent = itemView.findViewById(R.id.heure_client_entrer);
            heure_str = itemView.findViewById(R.id.heure_client_sortie);
            prix = itemView.findViewById(R.id.prix_client);
            place = itemView.findViewById(R.id.place_client);
            linearLayout = itemView.findViewById(R.id.layout_update_reservation_client);

            Animation anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim_liste);
            linearLayout.setAnimation(anim);
        }
    }
}
