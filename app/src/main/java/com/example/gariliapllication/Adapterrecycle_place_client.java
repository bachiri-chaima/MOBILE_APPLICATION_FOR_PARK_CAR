package com.example.gariliapllication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterrecycle_place_client extends RecyclerView.Adapter<Adapterrecycle_place_client.MyviewHolder>{

    private Context context;
    private ArrayList id_place;
    private ArrayList nom_place;
    private ArrayList disponibiliter;
    private String usr;
    private Activity activity;

    Animation anim;
    Adapterrecycle_place_client(Activity activity,Context context, ArrayList id_place, ArrayList nom_place, ArrayList disponibiliter,String usr) {

        //adaptateur pour l'affichage utulisateur
        this.activity = activity;
        this.context = context ;
        this.id_place = id_place;
        this.nom_place = nom_place;
        this.disponibiliter = disponibiliter;
        this.usr = usr;

    }
    @NonNull
    @Override
    public Adapterrecycle_place_client.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_raw_place_client,parent,false);
        return  new Adapterrecycle_place_client.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.nom_place.setText(String.valueOf(nom_place.get(position)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Reservation.class);
                intent.putExtra("id_place",String.valueOf(id_place.get(position)));
                intent.putExtra("nom_place",String.valueOf(nom_place.get(position)));
                intent.putExtra("dispo_place",String.valueOf(disponibiliter.get(position)));
                intent.putExtra("recycle_usr",String.valueOf(usr));
                context.startActivity(intent);

                activity.startActivityForResult(intent,2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_place.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView nom_place;
        LinearLayout linearLayout;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            nom_place = itemView.findViewById(R.id.nom_place_client);







            linearLayout = itemView.findViewById(R.id.layout_update_place_client);
            //animation
            anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim_liste);
            linearLayout.setAnimation(anim);
        }
    }
}
