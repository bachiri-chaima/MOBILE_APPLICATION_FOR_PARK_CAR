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

public class Adapter_reservation_adr extends RecyclerView.Adapter<Adapter_reservation_adr.MyViewHolder> {
    Context context;
    ArrayList id_utls,date,h_ent,h_str,prix,cnf,Id_user,id_place,id_vehicule,email_utl;
    Animation anim;

    public Adapter_reservation_adr(Context context, ArrayList id_utls,ArrayList email_utl, ArrayList date, ArrayList h_ent, ArrayList h_str, ArrayList prix, ArrayList cnf, ArrayList id_user, ArrayList id_place, ArrayList id_vehicule) {
        this.context = context;
        this.id_utls = id_utls;
        this.email_utl = email_utl;
        this.date = date;
        this.h_ent = h_ent;
        this.h_str = h_str;
        this.prix = prix;
        this.cnf = cnf;
        this.Id_user = id_user;
        this.id_place = id_place;
        this.id_vehicule = id_vehicule;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_raw_reservation_adminn,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.matricule.setText(String.valueOf(id_vehicule.get(position)));
        holder.email.setText(String.valueOf(email_utl.get(position)));
        holder.date_client.setText(String.valueOf(date.get(position)));
        holder.h_ent_client.setText(String.valueOf(h_ent.get(position)));
        holder.h_str_client.setText(String.valueOf(h_str.get(position)));
        holder.prix_client.setText(String.valueOf(prix.get(position)));
        holder.place.setText(String.valueOf(id_place.get(position)));

    }

    @Override
    public int getItemCount() {
        return id_utls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView matricule,email,date_client,h_ent_client,h_str_client,prix_client,place;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            matricule = itemView.findViewById(R.id.matricule_admin);
            email = itemView.findViewById(R.id.res_email_admin);
            date_client = itemView.findViewById(R.id.date_admin);
            h_ent_client = itemView.findViewById(R.id.heure_admin_entrer);
            h_str_client = itemView.findViewById(R.id.heure_admin_sortie);
            prix_client = itemView.findViewById(R.id.prix_admin);
            place = itemView.findViewById(R.id.place_admin);
            linearLayout = itemView.findViewById(R.id.layout_update_reservation_admin);

            anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim_liste);
            linearLayout.setAnimation(anim);


        }
    }
}
