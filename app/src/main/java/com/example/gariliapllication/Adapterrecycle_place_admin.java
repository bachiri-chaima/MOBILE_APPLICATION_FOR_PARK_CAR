package com.example.gariliapllication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class Adapterrecycle_place_admin extends RecyclerView.Adapter<Adapterrecycle_place_admin.MyviewHolder>{

    private Context context;
    private ArrayList id_place;
    private ArrayList nom_place;
    private ArrayList disponibiliter;

    private Activity activity;

    Animation anim;
    Adapterrecycle_place_admin(Activity activity, Context context, ArrayList id_place, ArrayList nom_place, ArrayList disponibiliter) {

        //adaptateur pour l'affichage utulisateur
        this.activity = activity;
        this.context = context ;
        this.id_place = id_place;
        this.nom_place = nom_place;
        this.disponibiliter = disponibiliter;

    }
    @NonNull
    @Override
    public Adapterrecycle_place_admin.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_raw_place_admin,parent,false);
        return  new Adapterrecycle_place_admin.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.nom_place.setText(String.valueOf(nom_place.get(position)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Add_reservation.class);
                intent.putExtra("id_place1",String.valueOf(id_place.get(position)));
                intent.putExtra("nom_place2",String.valueOf(nom_place.get(position)));
                intent.putExtra("dispo_place3",String.valueOf(disponibiliter.get(position)));
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

            nom_place = itemView.findViewById(R.id.nom_place_admin);







            linearLayout = itemView.findViewById(R.id.layout_update_place_admin);
            //animation
            anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim_liste);
            linearLayout.setAnimation(anim);
        }
    }
}
